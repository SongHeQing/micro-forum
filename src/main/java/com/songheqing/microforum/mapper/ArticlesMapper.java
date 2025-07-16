package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.songheqing.microforum.dto.ArticleListDTO;
import com.songheqing.microforum.entity.Article;

import java.util.List;

@Mapper
public interface ArticlesMapper {

    // 查询所有文章，按更新时间降序排序，由新到旧
    List<ArticleListDTO> selectAll(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    // 插入文章
    void insert(Article article);
}
