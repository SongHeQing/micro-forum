package com.songheqing.microforum.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * MinIO服务接口
 * 定义MinIO文件存储相关操作接口
 */
public interface MinioService {

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 存储桶是否存在
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    void createBucket(String bucketName);

    /**
     * 批量上传图片到公共存储桶
     *
     * @param files      图片列表
     * @param entityType 实体类型（如 "article"、"user" 等）
     * @return 图片URL列表
     */
    List<String> uploadFilesToPublicBucket(List<MultipartFile> files, String entityType);

    /**
     * 批量上传图片到私有存储桶
     *
     * @param files      文件列表
     * @param entityType 实体类型（如 "article"、"user" 等）
     * @return 图片URL列表
     */
    List<String> uploadFilesToPrivateBucket(List<MultipartFile> files, String entityType);

    /**
     * 批量上传图片
     *
     * @param bucketName 存储桶名称
     * @param files      文件列表
     * @param entityType 实体类型（如 "article"、"user" 等）
     * @return 图片URL列表
     */
    List<String> uploadFiles(String bucketName, List<MultipartFile> files, String entityType);

    /**
     * 批量删除公共存储桶中的文件
     *
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    int deleteFilesFromPublicBucket(List<String> objectNames);

    /**
     * 批量删除私有存储桶中的文件
     *
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    int deleteFilesFromPrivateBucket(List<String> objectNames);

    /**
     * 批量删除文件
     *
     * @param bucketName  存储桶名称
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    int deleteFiles(String bucketName, List<String> objectNames);

    /**
     * 获取文件访问URL
     * 根据存储桶类型和过期时间获取文件访问URL
     *
     * @param bucketName    存储桶名称
     * @param objectName    对象名称
     * @param expirySeconds 过期时间（秒），0表示永久有效（需要存储桶策略支持）
     * @return 文件访问URL
     */
    String getPrivateFileUrlToPrivateBucket(String objectName, int expirySeconds);

    /**
     * 获取公共文件访问URL（临时有效）
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    public String getPublicFileUrlToPublicBucket(String objectName);
    
    /**
     * 获取公共文件访问URL（永久有效）
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    String getPublicFileUrl(String bucketName, String objectName);
}