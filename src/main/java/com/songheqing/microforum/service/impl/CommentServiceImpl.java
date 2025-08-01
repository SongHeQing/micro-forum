package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.CommentEntity;
import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.mapper.CommentMapper;
import com.songheqing.microforum.request.CommentAddRequest;
import com.songheqing.microforum.request.CommentReplyAddRequest;
import com.songheqing.microforum.service.CommentService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;

import io.jsonwebtoken.lang.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticlesMapper articlesMapper;

    /*
     * 添加评论
     */
    @Transactional
    public void addComment(CommentAddRequest request) {
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
    }

    /*
     * 回复评论
     */
    @Transactional
    public void replyToComment(CommentReplyAddRequest request) {
        // 构造回复评论实体
        CommentEntity comment = new CommentEntity();
        BeanUtils.copyProperties(request, comment);
        comment.setUserId(CurrentHolder.getCurrentId());
        // 设置 userId、createdAt 等其他字段

        // 插入评论（无需楼层）
        commentMapper.insertComment(comment);

        // 更新父评论的子评论数reply_count
        commentMapper.incrementReplyCount(request.getParentId());

        // 文章评论数+1
        articlesMapper.incrementCommentCount(request.getArticleId());
    }

    /*
     * 查询一级评论
     */
    private static final Integer COMMENT_LIMIT = 15;
    private static final Integer REPLIES_PREVIEW_LIMIT = 4;

    @Transactional(readOnly = true)
    public List<CommentVO> queryTopLevelComments(Long articleId, Integer pageNum) {
        Integer offset = (pageNum - 1) * COMMENT_LIMIT;

        // 1. 查询一级评论（带 user 信息）
        List<CommentVO> topComments = commentMapper.selectTopLevelCommentVOs(articleId, COMMENT_LIMIT, offset);
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
            List<CommentReplyVO> replies = commentMapper.selectCommentReplyVOs(parentId, REPLIES_PREVIEW_LIMIT, 0);
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
        return commentMapper.selectCommentReplyVOs(parentId, COMMENT_LIMIT, offset);
    }

}