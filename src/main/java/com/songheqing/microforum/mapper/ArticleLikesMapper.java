package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.songheqing.microforum.entity.ArticleLikeEntity;

@Mapper
public interface ArticleLikesMapper {
    /**
     * 插入一条点赞记录
     * 
     * @param articleLike 点赞实体
     * @return 影响的行数
     */
    int insert(ArticleLikeEntity articleLike);

    /**
     * 根据用户ID和文章ID删除点赞记录（取消点赞）
     * 
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM article_likes WHERE article_id=#{articleId} AND user_id = #{userId}")
    int deleteByArticleIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    /**
     * 查询某个用户是否点赞了某篇文章
     * 
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 记录数，大于0表示已点赞
     */
    @Select("SELECT EXISTS(SELECT 1 FROM article_likes WHERE article_id = #{articleId} AND user_id = #{userId})")
    boolean existsByArticleIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

}
