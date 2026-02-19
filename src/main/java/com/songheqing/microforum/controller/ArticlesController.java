package com.songheqing.microforum.controller;

import com.songheqing.microforum.request.ArticlePublishRequest;
import com.songheqing.microforum.service.ArticlesService;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleCardVO;
import com.songheqing.microforum.vo.ArticleUserCardVO;
import com.songheqing.microforum.vo.Result;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    /**
     * 获取文章列表
     * 
     * @param pageNumber 页码
     * @return 文章列表
     */
    @GetMapping
    public Result<List<ArticleCardVO>> list(@RequestParam Integer pageNumber) {
        List<ArticleCardVO> articles = articlesService.list(pageNumber);
        log.debug("文章列表查询成功，共{}篇文章", articles.size());
        return Result.success(articles);
    }

    /**
     * 根据频道ID获取文章列表
     * 
     * @param channelId  频道ID
     * @param pageNumber 页码
     * @param sort       排序方式 (create_time 或 last_reply_time)
     * @return 频道文章列表
     */
    @GetMapping("/channel/{channelId}")
    public Result<List<ArticleUserCardVO>> listByChannelId(@PathVariable Integer channelId,
            @RequestParam Integer pageNumber,
            @RequestParam(required = false) String sort) {
        List<ArticleUserCardVO> articles = articlesService.listByChannelId(channelId, pageNumber, sort);
        log.debug("频道{}文章列表查询成功，共{}篇文章", channelId, articles.size());
        return Result.success(articles);
    }

    /**
     * 根据用户ID获取文章列表
     * 
     * @param userId     用户ID
     * @param pageNumber 页码
     * @return 用户文章列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<ArticleUserCardVO>> listByUserId(@PathVariable Long userId, @RequestParam Integer pageNumber) {
        List<ArticleUserCardVO> articles = articlesService.listByUserId(userId, pageNumber);
        log.debug("用户{}文章列表查询成功，共{}篇文章", articles, articles.size());
        return Result.success(articles);
    }

    // 添加文章
    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "发布文章")
    // 现在使用 @ModelAttribute 来绑定表单字段到一个 DTO，并配合 @Valid
    // 注意：@ModelAttribute 默认也会绑定 @RequestParam 的字段
    // 对于 MultipartFile 类型的文件参数，使用 @RequestParam 是 Spring MVC 的标准做法
    public Result<Void> publish(
            @Parameter(description = "文章信息") @Valid @ModelAttribute ArticlePublishRequest publishArticleRequest,
            @Parameter(description = "文章图片") @RequestParam(required = false) List<MultipartFile> images) {
        log.info("发布文章：{}，图片：{}", publishArticleRequest, (images != null ? images.size() : 0));
        try {
            articlesService.publish(publishArticleRequest, images);
            log.info("文章发布成功. UserID: {}, Title: {}, Images: {}",
                    CurrentHolder.getCurrentId(), publishArticleRequest.getTitle(),
                    (images != null ? images.size() : 0));
        } catch (IOException e) {
            log.error("发布文章失败，{}", e.getMessage());
            return Result.error("发布文章失败");
        }
        return Result.success();
    }

    /**
     * 获取文章详情
     * 
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/{id}")
    public Result<ArticleDetailVO> detail(@PathVariable Long id) {
        ArticleDetailVO articleDetail = articlesService.getDetail(id);
        return Result.success(articleDetail);
    }
}