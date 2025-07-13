package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.songheqing.microforum.entity.Article;

import java.util.List;

@Mapper
public interface ArticlesMapper {

    // 查询所有文章，按更新时间降序排序
    @Select("select id, user_id, channel_id, title, content_preview, cover_type, create_time, update_time from article order by update_time desc")
    List<Article> selectAll();

    // 插入文章
    void insert(Article article);
}
