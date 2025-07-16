package com.songheqing.microforum.service;

import java.io.IOException;
import java.util.List;

import com.songheqing.microforum.dto.ArticleListDTO;
import com.songheqing.microforum.request.PublishArticleRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ArticlesService {
    List<ArticleListDTO> list(Integer pageNumber);

    // 发布文章
    void publish(PublishArticleRequest publishArticleRequest, List<MultipartFile> images) throws IOException;
}
