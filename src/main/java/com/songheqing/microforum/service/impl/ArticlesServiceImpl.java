package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.pojo.Article;
import com.songheqing.microforum.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Override
    public List<Article> list() {
        return articlesMapper.selectAll();
    }

    @Override
    public void add(Article article) {
        // 设置创建时间等默认值
        article.setCreateTime(java.time.LocalDateTime.now());
        article.setUpdateTime(java.time.LocalDateTime.now());
        // 调用 Mapper 插入数据
        articlesMapper.insert(article);
    }
}
