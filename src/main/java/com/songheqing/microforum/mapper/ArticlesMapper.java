package com.songheqing.microforum.mapper;

import com.songheqing.microforum.pojo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticlesMapper {

    @Select("select id, user_id, channel_id, title, content_preview, cover_type, create_time, update_time from article")
    List<Article> selectAll();

    void insert(Article article);
}
