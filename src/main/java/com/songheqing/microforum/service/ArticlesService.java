package com.songheqing.microforum.service;

import com.songheqing.microforum.mapper.ArticlesMapper;
import com.songheqing.microforum.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


public interface ArticlesService {
    List<Article> list();
}
