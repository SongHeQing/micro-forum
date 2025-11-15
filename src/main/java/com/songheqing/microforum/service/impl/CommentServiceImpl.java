package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.CommentEntity;
import com.songheqing.microforum.entity.CommentLikeEntity;
import com.songheqing.microforum.exception.BusinessException;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.mapper.CommentMapper;
import com.songheqing.microforum.mapper.CommentLikesMapper;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.request.CommentAddRequest;
import com.songheqing.microforum.request.CommentReplyAddRequest;
import com.songheqing.microforum.service.CommentService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticlesMapper articlesMapper;
    @Autowired
    private CommentLikesMapper commentLikesMapper;

    @Autowired
    private UserMapper userMapper;

    /*
     * 添加评论
     */
    @Transactional
    public Long addComment(CommentAddRequest request) {
        // 查询并锁定当前楼层
        Integer floor = articlesMapper.selectFloorCountForUpdate(request.getArticleId());

        // 构造 Comment 实体
        CommentEntity comment = new CommentEntity();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(CurrentHolder.getCurrentId());
        comment.setFloor(floor + 1);
        comment.setParentId(null);
        // 设置 userId、createdAt 等其他字段

        // 插入评论
        commentMapper.insertComment(comment);
        // 文章评论数+1
        articlesMapper.incrementCommentCount(request.getArticleId());
        // 更新文章楼层计数
        articlesMapper.incrementFloorCount(request.getArticleId());
        // 更新文章最后回复时间
        articlesMapper.updateLastReplyTime(request.getArticleId());
        // 更新用户的评论发送量
        userMapper.incrementUserCommentCount(CurrentHolder.getCurrentId());

        // 返回新创建评论的ID
        return comment.getId();
    }

    /*
     * 回复评论
     */
    @Transactional
    public Long replyToComment(CommentReplyAddRequest request) {
        // 构造回复评论实体
        CommentEntity comment = new CommentEntity();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(CurrentHolder.getCurrentId());
        // 设置 userId、createdAt 等其他字段

        // 插入评论（无需楼层）,返回id
        commentMapper.insertComment(comment);

        // 更新父评论的子评论数reply_count
        commentMapper.incrementReplyCount(request.getParentId());

        // 文章评论数+1
        articlesMapper.incrementCommentCount(request.getArticleId());
        // 更新文章最后回复时间
        articlesMapper.updateLastReplyTime(request.getArticleId());
        // 更新用户的评论发送量
        userMapper.incrementUserCommentCount(CurrentHolder.getCurrentId());

        // 返回新创建评论的ID
        return comment.getId();
    }

    /*
     * 查询一级评论
     */
    private static final Integer COMMENT_LIMIT = 15;
    private static final Integer REPLIES_PREVIEW_LIMIT = 5;

    @Transactional(readOnly = true)
    public List<CommentVO> queryTopLevelComments(Long articleId, Integer pageNum) {
        Integer offset = (pageNum - 1) * COMMENT_LIMIT;
        Long currentUserId = CurrentHolder.getCurrentId(); // 获取当前用户ID，可能为null
        log.debug("查询一级评论 currentUserId: {}", currentUserId);
        // 1. 查询一级评论（带 user 信息）
        List<CommentVO> topComments = commentMapper.selectTopLevelCommentVOs(articleId, COMMENT_LIMIT, offset,
                currentUserId);
        if (topComments.isEmpty())
            return Collections.emptyList();

        // 2. 提取有子评论的 ID
        List<Long> parentIds = topComments.stream()
                .filter(c -> c.getReplyCount() > 0)
                .map(CommentVO::getId)
                .toList();

        // 3. 查询预览子评论（带 user + replyToUser）
        Map<Long, List<CommentReplyVO>> replyMap = new HashMap<>();
        for (Long parentId : parentIds) {
            List<CommentReplyVO> replies = commentMapper.selectCommentReplyVOs(parentId, REPLIES_PREVIEW_LIMIT, 0,
                    currentUserId);
            replyMap.put(parentId, replies);
        }

        // 4. 注入预览
        topComments.forEach(c -> c.setPreviewReplies(replyMap.getOrDefault(c.getId(), Collections.emptyList())));
        return topComments;
    }

    /*
     * 查询子评论
     */
    @Transactional(readOnly = true)
    public List<CommentReplyVO> queryReplies(Long parentId, Integer pageNum) {
        Integer offset = (pageNum - 1) * COMMENT_LIMIT;
        Long currentUserId = CurrentHolder.getCurrentId(); // 获取当前用户ID，可能为null
        return commentMapper.selectCommentReplyVOs(parentId, COMMENT_LIMIT, offset, currentUserId);
    }

    /**
     * 切换评论点赞状态：如果已点赞则取消，如果未点赞则点赞。
     * 整个操作在一个事务中，保证原子性。
     *
     * @param commentId 评论ID
     * @param articleId 文章ID
     * @return true表示点赞成功，false表示取消点赞成功
     * @throws BusinessException 当用户未登录时抛出异常
     */
    @Transactional
    public boolean toggleCommentLike(Long commentId, Long articleId) {
        // 从 CurrentHolder 获取当前用户ID
        Long userId = CurrentHolder.getCurrentId();

        // 检查用户是否已登录
        if (userId == null) {
            throw new BusinessException("请先登录后再进行点赞操作");
        }

        // 1. 查询是否已点赞
        boolean exists = commentLikesMapper.existsByCommentIdAndUserId(commentId, userId);

        if (exists) {
            // 已点赞，执行取消点赞
            int deletedRows = commentLikesMapper.deleteByCommentIdAndUserId(commentId, userId);
            if (deletedRows > 0) {
                commentMapper.decrementLikeCount(commentId); // 评论点赞量-1
                return false; // 返回false表示已取消点赞
            }
            return true; // 如果删除失败（理论上不应发生），保持原状态
        } else {
            // 未点赞，执行点赞
            CommentLikeEntity commentLike = new CommentLikeEntity();
            commentLike.setUserId(userId);
            commentLike.setCommentId(commentId);
            commentLike.setArticleId(articleId);

            int insertedRows = commentLikesMapper.insert(commentLike);
            if (insertedRows > 0) {
                commentMapper.incrementLikeCount(commentId); // 评论点赞量+1
                return true; // 返回true表示已点赞
            }
            return false; // 如果插入失败（理论上不应发生），保持原状态
        }
    }

}