package com.songheqing.microforum.service;

import java.io.IOException;
import java.util.List;

import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleListVO;

import org.springframework.web.multipart.MultipartFile;

public interface ArticlesService {
    List<ArticleListVO> list(Integer pageNumber);

    // 发布文章
    void publish(ArticlePublishRequest publishArticleRequest, List<MultipartFile> images) throws IOException;

    // 获取文章详情
    ArticleDetailVO getDetail(Long id);
}
