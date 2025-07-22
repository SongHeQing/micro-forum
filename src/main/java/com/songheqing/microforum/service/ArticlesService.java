package com.songheqing.microforum.service;

import java.io.IOException;
import java.util.List;

import com.songheqing.microforum.dto.ArticleListDTO;
import com.songheqing.microforum.request.ArticlePublishRequest;
import org.springframework.web.multipart.MultipartFile;
import com.songheqing.microforum.dto.ArticleDetailDTO;

public interface ArticlesService {
    List<ArticleListDTO> list(Integer pageNumber);

    // 发布文章
    void publish(ArticlePublishRequest publishArticleRequest, List<MultipartFile> images) throws IOException;

    // 获取文章详情
    ArticleDetailDTO getDetail(Integer id);
}
