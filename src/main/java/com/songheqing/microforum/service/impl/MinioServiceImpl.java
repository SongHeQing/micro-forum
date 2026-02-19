package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.config.MinioConfig;
import com.songheqing.microforum.exception.BusinessException;
import com.songheqing.microforum.service.MinioService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.SetBucketPolicyArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO服务实现类
 * 实现MinIO文件存储相关操作
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 存储桶是否存在
     */
    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("检查存储桶是否存在失败: {}", e.getMessage(), e);
            throw new BusinessException("检查存储桶是否存在失败: " + e.getMessage());
        }
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    @Override
    public void createBucket(String bucketName) {
        try {
            // 检查存储桶是否存在
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.debug("MinIO 存储桶 {} 创建成功", bucketName);

                // 如果是公共存储桶，则设置为公共读取
                if (bucketName.equals(minioConfig.getPublicBucket())) {
                    setBucketPublic(bucketName);
                }
            }
        } catch (Exception e) {
            log.error("创建存储桶失败: {}", e.getMessage(), e);
            throw new BusinessException("创建存储桶失败: " + e.getMessage());
        }
    }

    /**
     * 设置存储桶为公共读取
     *
     * @param bucketName 存储桶名称
     */
    public void setBucketPublic(String bucketName) {
        try {
            // 创建仅允许获取对象的公共读取策略
            String policy = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": {\n" +
                    "                \"AWS\": [\n" +
                    "                    \"*\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetObject\"\n" +
                    "            ],\n" +
                    "            \"Resource\": [\n" +
                    "                \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            // 设置存储桶策略
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build());

            log.debug("存储桶 {} 已设置为公共读取", bucketName);
        } catch (Exception e) {
            log.error("设置存储桶公共读取失败: {}", e.getMessage(), e);
            throw new BusinessException("设置存储桶公共读取失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param file       要上传的文件
     * @param entityType 实体类型（如 "ARTICLE"、"USER" 等）
     * @return 文件URL
     */
    private String uploadFile(String bucketName, MultipartFile file, String entityType) {
        try {
            // 确保存储桶存在
            createBucket(bucketName);

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extName = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;

            // 构建对象名称（路径）
            String objectName = entityType.toLowerCase() + "/" + uniqueFileName;

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build());
            }

            log.debug("文件上传成功: {}", objectName);
            // 按照最佳实践，只返回对象键（objectName），而不是完整 URL
            // 这样可以提高灵活性，便于在不同环境间迁移
            return objectName;
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage(), e);
            throw new BusinessException("上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量上传图片
     *
     * @param bucketName 存储桶名称
     * @param files      图片列表
     * @param entityType 实体类型（如 "ARTICLE"、"USER" 等）
     * @return 图片URL列表
     */
    @Override
    public List<String> uploadFiles(String bucketName, List<MultipartFile> files, String entityType) {
        try {
            List<String> objectName = new ArrayList<>();
            if (files == null || files.isEmpty()) {
                return objectName;
            }

            for (MultipartFile file : files) {
                String imgUrl = uploadFile(bucketName, file, entityType);
                objectName.add(imgUrl);
            }

            log.debug("批量上传图片成功，图片对象名称：{}", objectName);
            return objectName;
        } catch (Exception e) {
            log.error("批量上传图片失败: {}", e.getMessage(), e);
            throw new BusinessException("批量上传图片失败: " + e.getMessage());
        }
    }

    /**
     * 批量上传到公共存储桶
     *
     * @param files      图片列表
     * @param entityType 实体类型（如 "ARTICLE"、"USER" 等）
     * @return 图片URL列表
     */
    @Override
    public List<String> uploadFilesToPublicBucket(List<MultipartFile> files, String entityType) {
        return uploadFiles(minioConfig.getPublicBucket(), files, entityType);
    }

    /**
     * 批量上传图片到私有存储桶
     *
     * @param files      图片列表
     * @param entityType 实体类型（如 "ARTICLE"、"USER" 等）
     * @return 图片URL列表
     */
    @Override
    public List<String> uploadFilesToPrivateBucket(List<MultipartFile> files, String entityType) {
        return uploadFiles(minioConfig.getPrivateBucket(), files, entityType);
    }

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称（即文件在MinIO中的路径+文件名）
     */
    private void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());

            log.debug("文件删除成功: {}", objectName);
        } catch (Exception e) {
            log.error("删除文件失败: {}", e.getMessage(), e);
            throw new BusinessException("删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除文件
     *
     * @param bucketName  存储桶名称
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    @Override
    public int deleteFiles(String bucketName, List<String> objectNames) {
        try {
            int deletedCount = 0;
            if (objectNames == null || objectNames.isEmpty()) {
                return deletedCount;
            }

            for (String objectName : objectNames) {
                deleteFile(bucketName, objectName);
                deletedCount++;
            }

            log.debug("批量删除文件完成，总共尝试删除 {} 个文件，成功删除 {} 个文件", objectNames.size(), deletedCount);
            return deletedCount;
        } catch (Exception e) {
            log.error("批量删除文件失败: {}", e.getMessage(), e);
            throw new BusinessException("批量删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除公共存储桶中的文件
     *
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    @Override
    public int deleteFilesFromPublicBucket(List<String> objectNames) {
        return deleteFiles(minioConfig.getPublicBucket(), objectNames);
    }

    /**
     * 批量删除私有存储桶中的文件
     *
     * @param objectNames 对象名称列表（即文件在MinIO中的路径+文件名）
     * @return 成功删除的文件数量
     */
    @Override
    public int deleteFilesFromPrivateBucket(List<String> objectNames) {
        return deleteFiles(minioConfig.getPrivateBucket(), objectNames);
    }

    /**
     * 获取公共文件访问URL（临时有效）
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    @Override
    public String getPublicFileUrlToPublicBucket(String objectName) {
        return getPublicFileUrl(minioConfig.getPublicBucket(), objectName);
    }

    /**
     * 获取私有文件访问URL（临时有效）
     *
     * @param objectName    对象名称
     * @param expirySeconds 过期时间（秒）
     * @return 文件访问objectName
     */
    @Override
    public String getPrivateFileUrlToPrivateBucket(String objectName, int expirySeconds) {
        return getPrivateFileUrl(minioConfig.getPrivateBucket(), objectName, expirySeconds);
    }

    /**
     * 获取公共文件访问URL（永久有效）
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 文件访问URL
     */
    @Override
    public String getPublicFileUrl(String bucketName, String objectName) {
        try {
            // 使用外部访问地址而不是内部端点地址
            String publicUrl = minioConfig.getPublicUrl();

            // 确保publicUrl不以斜杠结尾
            if (publicUrl.endsWith("/")) {
                publicUrl = publicUrl.substring(0, publicUrl.length() - 1);
            }

            // 确保objectName不以斜杠开头
            if (objectName.startsWith("/")) {
                objectName = objectName.substring(1);
            }

            return publicUrl + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            log.error("获取公共文件访问URL失败: {}", e.getMessage(), e);
            throw new BusinessException("获取公共文件访问URL失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件访问URL
     *
     * @param bucketName    存储桶名称
     * @param objectName    对象名称
     * @param expirySeconds 过期时间（秒），0表示永久有效（需要存储桶策略支持）
     * @return 文件访问objectName
     */
    private String getPrivateFileUrl(String bucketName, String objectName, int expirySeconds) {
        try {
            // 临时有效URL
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expirySeconds, TimeUnit.SECONDS)
                            .build());
        } catch (Exception e) {
            log.error("获取文件访问URL失败: {}", e.getMessage(), e);
            throw new BusinessException("获取文件访问URL失败: " + e.getMessage());
        }
    }
}