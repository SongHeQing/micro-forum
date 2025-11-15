package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.ArticleCollectionPreviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCollectionPreviewMapper {

    // 插入文章收藏预览记录
    void insert(ArticleCollectionPreviewEntity articleCollectionPreview);

    // 根据ID查询文章收藏预览记录
    ArticleCollectionPreviewEntity selectById(@Param("id") Long id);

    // 根据作者用户ID查询文章收藏预览列表
    List<ArticleCollectionPreviewEntity> selectByAuthorUserId(
            @Param("authorUserId") Long authorUserId,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    // 更新收藏数量
    void updateCollectedCount(@Param("id") Long id, @Param("collectedCount") Integer collectedCount);

    // 删除文章收藏预览记录
    void deleteById(@Param("id") Long id);

    // 根据文章ID查询
    ArticleCollectionPreviewEntity selectByArticleId(@Param("articleId") Long articleId);
}
