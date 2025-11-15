package com.songheqing.microforum.constant;

/**
 * MinIO相关常量定义
 * 统一管理MinIO存储中的路径和实体类型，确保符合MinIO小写规范
 */
public class MinioConstants {
    
    /**
     * 实体类型常量（用作MinIO中对象的路径前缀）
     * 使用小写以符合MinIO规范
     */
    public static class EntityType {
        /** 文章相关文件 */
        public static final String ARTICLE = "article";
        
        /** 用户相关文件 */
        public static final String USER = "user";
        
        /** 频道相关文件 */
        public static final String CHANNEL = "channel";
        
        /** 评论相关文件 */
        public static final String COMMENT = "comment";
        
        /** 默认文件类型 */
        public static final String DEFAULT = "default";
    }

    /**
     * 子路径常量（用于指定特定类型的文件存储子目录）
     */
    public static class SubPath {
        /** 头像文件 */
        public static final String AVATAR = "avatar";
        
        /** 背景文件 */
        public static final String BACKGROUND = "background";
    }
}