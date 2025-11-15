package com.songheqing.microforum.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    /**
     * 通用：保存图片到本地
     * 
     * @param images     图片列表
     * @param entityType 业务类型（如 "article"、"user" 等）
     * @return 图片URL列表
     */
    List<String> saveImages(List<MultipartFile> images, String entityType);

    /**
     * 批量删除指定路径的本地文件
     * 
     * @param filePaths 文件路径列表
     * @return 删除结果，成功删除的文件数量
     */
    int deleteFiles(List<String> filePaths);

}