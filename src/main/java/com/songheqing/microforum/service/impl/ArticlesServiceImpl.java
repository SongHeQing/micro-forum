package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.pojo.Article;
import com.songheqing.microforum.pojo.User;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.service.UserService;
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
}
