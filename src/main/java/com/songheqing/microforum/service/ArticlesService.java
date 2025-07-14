package com.songheqing.microforum.service;

import java.util.List;

import com.songheqing.microforum.entity.Article;
import com.songheqing.microforum.request.PublishArticleRequest;

public interface ArticlesService {
    List<Article> list(Integer pageNumber);

    // 发布文章
    void publish(PublishArticleRequest publishArticleRequest);
}
