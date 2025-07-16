package com.songheqing.microforum.service;

import com.songheqing.microforum.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    void saveImage(Image image);

    /**
     * 通用：保存图片到本地并插入图片表
     * 
     * @param images     图片列表
     * @param entityType 业务类型（如 "ARTICLE"、"USER" 等）
     * @param entityId   业务ID
     * @return 图片URL列表
     */
    List<String> saveImages(List<MultipartFile> images, String entityType, Integer entityId) throws IOException;
}