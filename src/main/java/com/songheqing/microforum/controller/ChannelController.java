package com.songheqing.microforum.controller;

import com.songheqing.microforum.converter.MinioUrlConverter;
import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.request.ChannelRequest;
import com.songheqing.microforum.service.ChannelService;
import com.songheqing.microforum.vo.ChannelSimpleVO;
import com.songheqing.microforum.vo.ChannelVO;
import com.songheqing.microforum.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/channels")
@SecurityRequirement(name = "Authorization")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private MinioUrlConverter minioUrlConverter;

    @GetMapping
    @Operation(summary = "获取频道列表")
    public Result<List<ChannelSimpleVO>> list() {
        List<ChannelSimpleVO> channels = channelService.list();
        return Result.success(channels);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取频道详情")
    public Result<ChannelVO> getById(
            @Parameter(description = "频道ID") @PathVariable Long id) {
        ChannelVO channel = channelService.getById(id);
        channel = minioUrlConverter.convertToChannelVO(channel);
        return Result.success(channel);
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "创建新频道")
    /*
     * 使用 @ModelAttribute 而不是 @RequestPart 的原因：
     * 1. 前端将每个 ChannelRequest 字段作为单独的表单字段发送
     * 2. @ModelAttribute 适用于将表单字段绑定到对象属性
     * 3. @RequestPart 适用于处理 multipart 请求中的 JSON 部分
     * 4. 当前端发送的是 application/x-www-form-urlencoded 或 multipart/form-data
     * 格式且每个字段单独发送时，应该使用 @ModelAttribute
     * 
     * 对于 MultipartFile 类型的文件参数，使用 @RequestParam 是 Spring MVC 的标准做法
     */
    public Result<Long> createChannel(
            @Parameter(description = "频道信息") @Valid @ModelAttribute ChannelRequest channelRequest,
            @Parameter(description = "频道头像文件") @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @Parameter(description = "频道背景文件") @RequestParam(value = "background", required = false) MultipartFile background) {
        Long channelId = channelService.createChannel(channelRequest, avatar, background);
        return Result.success(channelId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新频道信息")
    public Result<ChannelEntity> updateChannel(
            @Parameter(description = "频道ID") @PathVariable Long id,
            @Parameter(description = "频道信息") @Valid @RequestBody ChannelRequest channelRequest) {
        ChannelEntity channel = channelService.updateChannel(id, channelRequest);
        return Result.success(channel);
    }

    @PostMapping(value = "/{id}/avatar", consumes = "multipart/form-data")
    @Operation(summary = "更新频道头像")
    public Result<String> uploadChannelAvatar(
            @Parameter(description = "频道ID") @PathVariable Long id,
            @Parameter(description = "频道头像文件") @RequestParam("file") MultipartFile file) {
        String imageUrl = channelService.uploadChannelAvatar(id, file);
        return Result.success(imageUrl);
    }

    @PostMapping(value = "/{id}/background", consumes = "multipart/form-data")
    @Operation(summary = "更新频道背景")
    public Result<String> uploadChannelBackground(
            @Parameter(description = "频道ID") @PathVariable Long id,
            @Parameter(description = "频道背景文件") @RequestParam("file") MultipartFile file) {
        String imageUrl = channelService.uploadChannelBackground(id, file);
        return Result.success(imageUrl);
    }
}