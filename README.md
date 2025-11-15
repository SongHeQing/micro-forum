# MICRO-FORUM
## 开发常用命令
启动项目：
```bash
# maven命令启动
mvn spring-boot:run
# windows bat启动
.\start-utf8.bat
```

构建项目：
```bash
# maven命令构建
mvn clean package -Dmaven.test.skip=true
# windows bat构建
.\build.bat
```
清理编译文件：
```bash
# maven命令清理
mvn clean
```
## 常用web控制台
本地开发
- [MinIO控制台](http://localhost:9001)  
- [Swagger](http://localhost:8080/swagger-ui/index.html)

## Git相关
#### 常用Git命令
```bash
# 添加到暂存区
git add .  

# 提交
git commit -m "commit message"

# 推送到远程仓库
git pull origin master
```

#### Git commit message 实践约定
Git 本身对 commit message 的长度几乎没有硬性限制：
- 第一行（subject）：理论上可以很长，但建议保持在 50-72 个字符以内
- 正文部分：几乎没有限制，可以写很长的内容

**1. 第一行（subject行）**
```
git commit -m "这是一个简短的提交信息"
```
- 建议长度：50个字符以内
- 应该简洁明了地描述变更内容
- 以大写字母开头，不需要句号结尾

**2. 多行提交信息**
```bash
git commit -m "这是一个简短的标题
>
>这里是详细的描述信息，可以详细说明变更的原因、
>影响范围、注意事项等内容。
>
>可以包含多个段落，每一行建议不超过72个字符。"
```

**3. 使用文件作为提交信息**
```bash
git commit -F commit-message.txt
```

**各平台的限制**

虽然 Git 本身限制很少，但一些平台可能有自己的限制：

1. **GitHub**：单行建议不超过 72 个字符
2. **GitLab**：类似 GitHub 的建议
3. **Gerrit**：Change-Id 行有特定格式要求

**推荐格式**

```bash
git commit -m "简短描述（50字符以内）

详细描述（可选）：
- 更多细节信息
- 变更原因
- 影响说明

可以使用多行，每行不超过72字符"
```
在 VSCode 的 Git 提交界面中，保持换行是可以的，且这是推荐的做法。

**✅ 推荐的格式：**
```
编写了md操作文档，完成了使用MinIO管理文件的重构

- 优化了MinIO配置文件的结构
- 添加了详细的注释说明
- 更新了相关文档
```

**❌ 不推荐的格式：**
```
编写了md操作文档，完成了使用MinIO管理文件的重构。优化了MinIO配置文件的结构，添加了详细的注释说明，更新了相关文档。
```
**总结**

虽然技术上可以写很长的 commit message，但建议遵循以下原则：
1. 第一行保持简短（50字符以内）
2. 如需详细描述，使用多行格式
3. 遵循团队或项目的 commit message 规范
4. 保持清晰、简洁和有意义

这样既符合最佳实践，也便于在各种 Git 工具和平台上正确显示。


## 部署文件目录
### 生产环境
- 内网设备：  `docker-local-prod`  
已从Git版本控制移除的环境变量：`docker-local-prod\.env`
- 公网设备：  `linux-cloud-server-prod`  
已从Git版本控制移除的Docker环境变量 ：`linux-cloud-server-prod\profile`

## Java项目配置文件
- 开发：
`src\main\resources\application-dev.yml`  
- 生产： 
`src\main\resources\application-docker-local-prod.yml`

