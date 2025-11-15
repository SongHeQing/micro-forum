package com.songheqing.microforum.service;

import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleCardVO;
import com.songheqing.microforum.vo.ArticleUserCardVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticlesService {
    List<ArticleCardVO> list(Integer pageNumber);

    // 根据用户ID获取文章列表
    List<ArticleUserCardVO> listByUserId(Long userId, Integer pageNumber);

    // 根据频道ID获取文章列表
    List<ArticleUserCardVO> listByChannelId(Integer channelId, Integer pageNumber, String sort);

    // 发布文章
    void publish(ArticlePublishRequest publishArticleRequest, List<MultipartFile> images) throws IOException;

    // 获取文章详情
    ArticleDetailVO getDetail(Long id);
}