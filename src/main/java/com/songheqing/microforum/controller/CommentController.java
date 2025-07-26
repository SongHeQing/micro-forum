package com.songheqing.microforum.controller;

import com.songheqing.microforum.request.CommentAddRequest;
import com.songheqing.microforum.request.CommentReplyAddRequest;
import com.songheqing.microforum.service.CommentService;
import com.songheqing.microforum.vo.CommentReplyVO;
import com.songheqing.microforum.vo.CommentVO;
import com.songheqing.microforum.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论", description = "处理评论的新增、查询等操作")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "提交成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败")
})
@RestController
@RequestMapping("/comment")
@SecurityRequirement(name = "Authorization")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "提交一级评论", description = "向指定文章提交一级评论，支持富文本或纯文本内容，内容限制为2000字以内")
    @PostMapping("/add")
    public Result<Void> addComment(@Valid @RequestBody CommentAddRequest request) {
        commentService.addComment(request);
        return Result.success();
    }

    @Operation(summary = "回复评论", description = "对某一级评论进行回复，支持@目标用户展示，内容限制为500字以内")
    @PostMapping("/reply")
    public Result<Void> replyToComment(@Valid @RequestBody CommentReplyAddRequest request) {
        commentService.replyToComment(request);
        return Result.success();
    }

    @Operation(summary = "分页查询一级评论（附带前5条回复预览）")
    @GetMapping("/list")
    @Validated
    public Result<List<CommentVO>> listComments(
            @Parameter(description = "文章ID", example = "1") @NotNull(message = "文章ID不能为空") Long articleId,
            @Parameter(description = "页码", example = "1") @Min(1) Integer page) {
        List<CommentVO> comments = commentService.queryTopLevelComments(articleId, page);
        return Result.success(comments);
    }

    @Operation(summary = "分页查询某条评论的所有回复（二级评论）")
    @GetMapping("/replies")
    @Validated
    public Result<List<CommentReplyVO>> listReplies(
            @Parameter(description = "父评论ID", example = "1") @NotNull(message = "父评论ID不能为空") Long parentId,
            @Parameter(description = "页码", example = "1") @Min(1) Integer page) {
        List<CommentReplyVO> replies = commentService.queryReplies(parentId, page);
        return Result.success(replies);
    }
}
