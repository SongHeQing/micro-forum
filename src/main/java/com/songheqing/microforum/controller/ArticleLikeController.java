
package com.songheqing.microforum.controller;

import com.songheqing.microforum.service.impl.ArticleLikeService;
import com.songheqing.microforum.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "文章点赞", description = "处理文章点赞的新增、查询等操作")
@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Authorization")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    /**
     * 切换文章点赞状态的API接口。
     * POST /articles/{articleId}/toggleLike
     * 
     * @param articleId 文章ID
     * @return 响应实体，包含点赞/取消点赞状态
     */
    @Operation(summary = "切换文章点赞状态")
    @PostMapping("/{articleId}/toggleLike")
    public Result<String> toggleArticleLike(@Parameter(description = "文章ID") @PathVariable Long articleId) {
        boolean isLiked = articleLikeService.toggleLike(articleId);
        if (isLiked) {
            return Result.success("点赞成功");
        } else {
            return Result.success("取消点赞成功");
        }
    }

    /**
     * 查询当前用户对某篇文章的点赞状态。
     * GET /articles/{articleId}/likeStatus
     * 
     * @param articleId 文章ID
     * @return 响应实体，包含点赞状态（true/false）
     */
    @Operation(summary = "查询当前用户对某篇文章的点赞状态")
    @GetMapping("/{articleId}/likeStatus")
    public Result<Boolean> getArticleLikeStatus(@Parameter(description = "文章ID") @PathVariable Long articleId) {
        boolean isLiked = articleLikeService.isArticleLikedByUser(articleId);
        return Result.success(isLiked);
    }
}