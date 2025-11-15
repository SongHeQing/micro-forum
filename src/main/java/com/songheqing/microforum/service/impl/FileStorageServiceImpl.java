package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.exception.BusinessException;
import com.songheqing.microforum.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload-dir}")
    private String UPLOAD_DIR;

    /**
     * 保存图片到本地文件系统
     * 
     * @param images     图片列表
     * @param entityType 业务类型（如 "article"、"user" 等）
     * @return 图片URL列表
     */
    @Override
    public List<String> saveImages(List<MultipartFile> images, String entityType) {
        // 如果图片列表为空，则返回空列表
        List<String> imgUrls = new ArrayList<>();
        if (images == null || images.isEmpty()) {
            return imgUrls;
        }
        // 获取上传文件的目录，确保路径格式正确
        String subDir = entityType;
        if (subDir.endsWith("/")) {
            subDir = subDir.substring(0, subDir.length() - 1);
        }
        // 生成文件路径
        Path uploadPath = Paths.get(UPLOAD_DIR, subDir);
        // 如果上传文件的目录不存在，则创建目录
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error("创建目录失败: {}", e.getMessage(), e);
                throw new BusinessException("创建目录失败: " + e.getMessage());
            }
        }
        // 遍历图片列表
        for (int i = 0; i < images.size(); i++) {
            // 获取图片
            MultipartFile image = images.get(i);
            // 获取图片的原始文件名
            String originalFilename = image.getOriginalFilename();
            // 获取图片的扩展名
            String extName = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            // 生成唯一文件名
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
            // 生成文件路径
            Path filePath = uploadPath.resolve(uniqueFileName);
            // 将图片保存到本地
            try {
                image.transferTo(filePath.toFile());
            } catch (IOException e) {
                log.error("保存文件失败: {}", e.getMessage(), e);
                throw new BusinessException("保存文件失败: " + e.getMessage());
            }
            // 生成图片URL
            String imgUrl = "/uploads/" + subDir + "/" + uniqueFileName;
            imgUrls.add(imgUrl);
        }
        log.debug("保存图片成功，图片URL：{}", imgUrls);
        return imgUrls;
    }

    /**
     * 批量删除指定路径的本地文件
     * 
     * @param filePaths 文件路径列表
     * @return 删除结果，成功删除的文件数量
     */
    @Override
    public int deleteFiles(List<String> filePaths) {
        // 记录成功删除的文件数量
        int deletedCount = 0;

        // 如果文件路径列表为空，直接返回0
        if (filePaths == null || filePaths.isEmpty()) {
            return deletedCount;
        }

        // 遍历文件路径列表，逐个删除文件
        for (String filePath : filePaths) {
            try {
                // 构建完整的文件路径
                Path fullFilePath = Paths.get(UPLOAD_DIR, filePath);

                // 检查文件是否存在
                if (Files.exists(fullFilePath)) {
                    // 删除文件
                    Files.delete(fullFilePath);
                    deletedCount++;
                    log.debug("文件删除成功：{}", filePath);
                } else {
                    log.warn("文件不存在：{}", filePath);
                }
            } catch (IOException e) {
                log.error("删除文件时发生错误：{}", filePath, e);
                throw new BusinessException("删除文件时发生错误：" + filePath);
            }
        }

        log.debug("批量删除文件完成，总共尝试删除 {} 个文件，成功删除 {} 个文件", filePaths.size(), deletedCount);
        return deletedCount;
    }
}