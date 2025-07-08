package com.songheqing.microforum.service;

import com.songheqing.microforum.pojo.Article;
import java.util.List;

public interface ArticlesService {
    List<Article> list();

    // 添加文章
    void add(Article article);
}
