package com.songheqing.microforum.controller;

import com.songheqing.microforum.dto.ArticleListDTO;
import com.songheqing.microforum.request.PublishArticleRequest;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.vo.Result;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Authorization")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    // 查询文章列表
    @GetMapping
    public Result<List<ArticleListDTO>> list(@RequestParam Integer pageNumber) {
        List<ArticleListDTO> articles = articlesService.list(pageNumber);
        return Result.success(articles);
    }

    // 添加文章
    @PostMapping
    // 现在使用 @ModelAttribute 来绑定表单字段到一个 DTO，并配合 @Valid
    // 注意：@ModelAttribute 默认也会绑定 @RequestParam 的字段
    public Result<Void> publish(@Valid @ModelAttribute PublishArticleRequest publishArticleRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        log.info("发布文章：{}，图片：{}", publishArticleRequest, (images != null ? images.size() : 0));
        try {
            articlesService.publish(publishArticleRequest, images);
        } catch (IOException e) {
            log.error("发布文章失败，文件上传失败：{}", e.getMessage());
            return Result.error("发布文章失败，文件上传失败");
        }
        return Result.success();
    }
}
