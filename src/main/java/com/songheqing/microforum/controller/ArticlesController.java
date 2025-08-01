package com.songheqing.microforum.controller;

import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleListVO;
import com.songheqing.microforum.vo.Result;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;

@Tag(name = "文章", description = "处理文章的新增、查询等操作")
@Slf4j
@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Authorization")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    // 查询文章列表
    @GetMapping
    public Result<List<ArticleListVO>> list(@RequestParam Integer pageNumber) {
        List<ArticleListVO> articles = articlesService.list(pageNumber);
        log.info("文章列表：{}", articles);
        return Result.success(articles);
    }

    // 添加文章
    @PostMapping
    // 现在使用 @ModelAttribute 来绑定表单字段到一个 DTO，并配合 @Valid
    // 注意：@ModelAttribute 默认也会绑定 @RequestParam 的字段
    public Result<Void> publish(@Valid @ModelAttribute ArticlePublishRequest publishArticleRequest,
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

    // 获取文章详情
    @GetMapping("/{id}")
    public Result<ArticleDetailVO> detail(@PathVariable Long id) {
        ArticleDetailVO detail = articlesService.getDetail(id);
        if (detail == null) {
            return Result.error("文章不存在");
        }
        return Result.success(detail);
    }
}
