package com.songheqing.microforum.service;

import com.songheqing.microforum.request.CommentAddRequest;
import com.songheqing.microforum.request.CommentReplyAddRequest;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;

import java.util.List;

public interface CommentService {
    void addComment(CommentAddRequest request);

    void replyToComment(CommentReplyAddRequest request);

    List<CommentVO> queryTopLevelComments(Long articleId, Integer page);

    List<CommentReplyVO> queryReplies(Long parentId, Integer page);
}