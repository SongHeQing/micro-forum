package com.songheqing.microforum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.songheqing.microforum.entity.CommentEntity;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface CommentMapper {
    int insertComment(CommentEntity comment);

    @Update("UPDATE comment SET reply_count = reply_count + 1 WHERE id = #{parentId}")
    void incrementReplyCount(@Param("parentId") Long parentId);

    List<CommentVO> selectTopLevelCommentVOs(@Param("articleId") Long articleId, @Param("limit") int limit,
            @Param("offset") int offset);

    List<CommentReplyVO> selectCommentReplyVOs(@Param("parentId") Long parentId, @Param("limit") int limit,
            @Param("offset") int offset);
}