package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.CommentLikeEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentLikesMapper {
    /**
     * 插入一条评论点赞记录
     * 
     * @param commentLike 点赞实体
     * @return 影响的行数
     */
    @Insert("INSERT INTO comment_likes (user_id, article_id, comment_id) VALUES (#{userId}, #{articleId}, #{commentId})")
    int insert(CommentLikeEntity commentLike);

    /**
     * 根据用户ID和评论ID删除点赞记录（取消点赞）
     * 
     * @param commentId 评论ID
     * @param userId    用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM comment_likes WHERE comment_id = #{commentId} AND user_id = #{userId}")
    int deleteByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    /**
     * 检查用户是否已对某评论点赞
     * 
     * @param commentId 评论ID
     * @param userId    用户ID
     * @return true表示已点赞，false表示未点赞
     */
    @Select("SELECT EXISTS(SELECT 1 FROM comment_likes WHERE comment_id = #{commentId} AND user_id = #{userId})")
    boolean existsByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
