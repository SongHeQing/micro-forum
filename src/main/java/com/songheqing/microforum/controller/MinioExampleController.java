package com.songheqing.microforum.controller;

import com.songheqing.microforum.constant.MinioConstants;
import com.songheqing.microforum.service.MinioService;
import com.songheqing.microforum.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * MinIO 使用示例控制器
 * 用于演示如何使用 MinIO 存储文件
 */
@RestController
@RequestMapping("/api/minio")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class MinioExampleController {

    @Autowired
    private MinioService minioService;

    /**
     * 上传图片到 MinIO 公共存储桶
     *
     * @param images     图片文件列表
     * @param entityType 实体类型（如 "article"、"user" 等）
     * @return 上传结果
     */
    @PostMapping(value = "/upload-public-images", consumes = "multipart/form-data")
    @Operation(summary = "上传图片到 MinIO 公共存储桶", description = "上传一个或多个图片文件到 MinIO 公共存储桶")
    public Result<List<String>> uploadPublicImages(
            @Parameter(description = "图片文件列表") @RequestParam List<MultipartFile> images,
            @Parameter(description = "实体类型（如 \"ARTICLE\"、\"USER\" 等）") @RequestParam String entityType) {
        List<String> imageUrls = minioService.uploadFilesToPublicBucket(images, entityType);
        return Result.success(imageUrls);
    }

    /**
     * 上传图片到 MinIO 私有存储桶
     *
     * @param images     图片文件列表
     * @param entityType 实体类型（如 "article"、"user" 等）
     * @return 上传结果
     */
    @PostMapping(value = "/upload-private-images", consumes = "multipart/form-data")
    @Operation(summary = "上传图片到 MinIO 私有存储桶", description = "上传一个或多个图片文件到 MinIO 私有存储桶")
    public Result<List<String>> uploadPrivateImages(
            @Parameter(description = "图片文件列表") @RequestParam List<MultipartFile> images,
            @Parameter(description = "实体类型（如 \"ARTICLE\"、\"USER\" 等）") @RequestParam String entityType) {
        List<String> imageUrls = minioService.uploadFilesToPrivateBucket(images, entityType);
        return Result.success(imageUrls);
    }

    /**
     * 从 MinIO 公共存储桶删除文件
     *
     * @param filePaths 文件路径列表
     * @return 删除结果
     */
    // 修复：将 @RequestBody 改为 @RequestParam，并使用 DELETE 查询参数方式传递列表
    @DeleteMapping("/delete-public-files")
    public Result<String> deletePublicFiles(@Parameter(description = "文件路径列表") @RequestParam List<String> filePaths) {
        int deletedCount = minioService.deleteFilesFromPublicBucket(filePaths);
        return Result.success("文件删除成功，共删除 " + deletedCount + " 个文件");
    }

    /**
     * 从 MinIO 私有存储桶删除文件
     *
     * @param filePaths 文件路径列表
     * @return 删除结果
     */
    // 修复：将 @RequestBody 改为 @RequestParam，并使用 DELETE 查询参数方式传递列表
    @DeleteMapping("/delete-private-files")
    public Result<String> deletePrivateFiles(@Parameter(description = "文件路径列表") @RequestParam List<String> filePaths) {
        int deletedCount = minioService.deleteFilesFromPrivateBucket(filePaths);
        return Result.success("文件删除成功，共删除 " + deletedCount + " 个文件");
    }

    /**
     * 获取私有文件访问URL
     *
     * @param objectName    对象名称
     * @param expirySeconds 过期时间（秒）
     * @return 文件访问URL
     */
    @GetMapping("/private-url")
    @Operation(summary = "获取私有文件访问URL", description = "获取私有存储桶中文件的临时访问URL")
    public Result<String> getPrivateFileUrl(
            @Parameter(description = "对象名称") @RequestParam String objectName,
            @Parameter(description = "过期时间（秒）") @RequestParam int expirySeconds) {
        String url = minioService.getPrivateFileUrlToPrivateBucket(objectName, expirySeconds);
        return Result.success(url);
    }
}