package com.songheqing.microforum.controller;

import com.songheqing.microforum.pojo.Article;
import com.songheqing.microforum.pojo.Result;
import com.songheqing.microforum.service.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    // 查询文章列表
    @GetMapping
    public Result<List<Article>> list() {
        List<Article> articles = articlesService.list();
        return Result.success(articles);
    }

    // 添加文章
    @PostMapping
    public Result<Void> add(@RequestBody Article article) {
        log.info("添加文章：{}", article);
        articlesService.add(article);
        return Result.success();
    }
}
