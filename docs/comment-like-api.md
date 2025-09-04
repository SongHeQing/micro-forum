# 评论点赞功能 API 文档

## 概述

评论点赞功能允许用户对评论进行点赞和取消点赞操作。该功能支持一级评论和二级评论（回复）的点赞。

## 数据库变更

### 1. comment 表新增字段
```sql
ALTER TABLE comment ADD COLUMN like_count int unsigned DEFAULT '0' COMMENT '点赞数';
```

### 2. comment_likes 表（已存在）
用于记录用户的点赞行为，防止重复点赞。

## API 接口

### 切换评论点赞状态

**接口地址：** `POST /comment/{commentId}/like`

**请求参数：**
- `commentId` (路径参数): 评论ID

**请求头：**
- `Authorization`: Bearer token（用户登录凭证）

**响应格式：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": true  // true表示已点赞，false表示已取消点赞
}
```

**使用示例：**

1. 点赞评论ID为123的评论：
```bash
curl -X POST "http://localhost:8080/comment/123/like" \
  -H "Authorization: Bearer your-jwt-token"
```

2. 再次调用相同接口会取消点赞：
```bash
curl -X POST "http://localhost:8080/comment/123/like" \
  -H "Authorization: Bearer your-jwt-token"
```

## 数据结构变更

### CommentVO 新增字段
```java
@Schema(description = "点赞数")
private Integer likeCount;

@Schema(description = "当前用户是否已点赞")
private Boolean isLiked;
```

### CommentReplyVO 新增字段
```java
@Schema(description = "点赞数")
private Integer likeCount;

@Schema(description = "当前用户是否已点赞")
private Boolean isLiked;
```

## 前端集成示例

### JavaScript 示例
```javascript
// 点赞/取消点赞评论
async function toggleCommentLike(commentId) {
  try {
    const response = await fetch(`/comment/${commentId}/like`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      }
    });
    
    const result = await response.json();
    if (result.code === 200) {
      // 更新UI显示
      const isLiked = result.data;
      updateLikeButton(commentId, isLiked);
    }
  } catch (error) {
    console.error('点赞操作失败:', error);
  }
}

// 更新点赞按钮状态
function updateLikeButton(commentId, isLiked) {
  const button = document.querySelector(`[data-comment-id="${commentId}"] .like-button`);
  if (isLiked) {
    button.classList.add('liked');
    button.textContent = '已点赞';
  } else {
    button.classList.remove('liked');
    button.textContent = '点赞';
  }
}
```

## 注意事项

1. **用户认证**：所有点赞操作都需要用户登录
2. **防重复点赞**：通过数据库唯一约束防止用户重复点赞同一评论
3. **事务保证**：点赞记录和评论点赞数的更新在同一事务中，保证数据一致性
4. **性能优化**：查询评论列表时会一次性获取当前用户的点赞状态，避免N+1查询问题

## 错误处理

- **401 Unauthorized**：用户未登录
- **404 Not Found**：评论不存在
- **500 Internal Server Error**：服务器内部错误

## 测试

运行单元测试验证功能：
```bash
mvn test -Dtest=CommentLikeTest
```
