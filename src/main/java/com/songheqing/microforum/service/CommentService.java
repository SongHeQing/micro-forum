package com.songheqing.microforum.service;

import com.songheqing.microforum.request.CommentAddRequest;
import com.songheqing.microforum.request.CommentReplyAddRequest;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;

import java.util.List;

public interface CommentService {
    Long addComment(CommentAddRequest request);

    Long replyToComment(CommentReplyAddRequest request);

    List<CommentVO> queryTopLevelComments(Long articleId, Integer page);

    List<CommentReplyVO> queryReplies(Long parentId, Integer page);

    /**
     * 切换评论点赞状态：如果已点赞则取消，如果未点赞则点赞
     *
     * @param commentId 评论ID
     * @param articleId 文章ID
     * @return true表示点赞成功，false表示取消点赞成功
     */
    boolean toggleCommentLike(Long commentId, Long articleId);
}