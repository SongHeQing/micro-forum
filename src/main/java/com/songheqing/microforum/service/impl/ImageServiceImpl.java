package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.ImageEntity;
import com.songheqing.microforum.mapper.ImageMapper;
import com.songheqing.microforum.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;

    @Value("${app.upload-dir}")
    private String UPLOAD_DIR;

    @Override
    public void saveImage(ImageEntity image) {
        imageMapper.insertImage(image);
    }

    /**
     * 保存图片
     * 
     * @param images     图片列表
     * @param entityType 业务类型（如 "ARTICLE"、"USER" 等）
     * @param entityId   业务ID
     * @return 图片URL列表
     */
    @Override
    public List<String> saveImages(List<MultipartFile> images, String entityType, Long entityId) throws IOException {
        // 如果图片列表为空，则返回空列表
        List<String> imgUrls = new ArrayList<>();
        if (images == null || images.isEmpty()) {
            return imgUrls;
        }
        // 获取上传文件的目录
        String subDir = entityType.toLowerCase();
        // 生成文件路径
        Path uploadPath = Paths.get(UPLOAD_DIR, subDir);
        // 如果上传文件的目录不存在，则创建目录
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
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
            image.transferTo(filePath.toFile());
            // 生成图片URL
            String imgUrl = "/uploads/" + subDir + "/" + uniqueFileName;
            imgUrls.add(imgUrl);

            // 插入图片表
            ImageEntity img = new ImageEntity();
            img.setImageUrl(imgUrl);
            img.setEntityType(entityType);
            img.setEntityId(entityId);
            img.setOrderNum((short) i);
            imageMapper.insertImage(img);
        }
        return imgUrls;
    }
}