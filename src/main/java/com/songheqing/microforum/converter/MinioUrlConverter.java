package com.songheqing.microforum.converter;

import com.songheqing.microforum.service.MinioService;
import com.songheqing.microforum.vo.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * MinIO对象名称到公共URL的转换器
 * 用于将存储在数据库中的MinIO对象名称转换为可直接访问的完整URL
 */
@Slf4j
@Mapper(componentModel = "spring")
public abstract class MinioUrlConverter {

    @Autowired
    protected MinioService minioService;

    // ==================== Article相关VO ====================

    /* ---------- 1. 处理 ArticleCardVO List ---------- */
    @IterableMapping(elementTargetType = ArticleCardVO.class)
    public abstract List<ArticleCardVO> updateArticleCardVOs(List<ArticleCardVO> sources);

    /* ---------- 2. 处理单个 ArticleCardVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "mediaUrls", source = "mediaUrls", qualifiedByName = "asUrls")
    @Mapping(target = "channelCard", source = "channelCard")
    public abstract ArticleCardVO updateArticleCardVO(ArticleCardVO source);

    /* ---------- 1. 处理 ArticleUserCardVO List ---------- */
    @IterableMapping(elementTargetType = ArticleUserCardVO.class)
    public abstract List<ArticleUserCardVO> updateArticleUserCardVOs(List<ArticleUserCardVO> sources);

    /* ---------- 2. 处理单个 ArticleUserCardVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "mediaUrls", source = "mediaUrls", qualifiedByName = "asUrls")
    @Mapping(target = "channel", source = "channel")
    @Mapping(target = "user", source = "user")
    public abstract ArticleUserCardVO updateArticleUserCardVO(ArticleUserCardVO source);

    /* ---------- 2. 处理单个 ArticleDetailVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "mediaUrls", source = "mediaUrls", qualifiedByName = "asUrls")
    @Mapping(target = "channel", source = "channel")
    @Mapping(target = "user", source = "user")
    public abstract ArticleDetailVO updateArticleDetailVO(ArticleDetailVO source);

    // ==================== Channel相关VO ====================

    /* ---------- 1. 处理 ChannelCardVO List ---------- */
    @IterableMapping(elementTargetType = ChannelCardVO.class)
    public abstract List<ChannelCardVO> updateChannelCardVOs(List<ChannelCardVO> sources);

    /* ---------- 2. 处理单个 ChannelCardVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "image", source = "image", qualifiedByName = "asUrl")
    public abstract ChannelCardVO updateChannelCardVO(ChannelCardVO source);

    /* ---------- 1. 处理 ChannelSimpleVO List ---------- */
    @IterableMapping(elementTargetType = ChannelSimpleVO.class)
    public abstract List<ChannelSimpleVO> updateChannelSimpleVOs(List<ChannelSimpleVO> sources);

    /* ---------- 2. 处理单个 ChannelSimpleVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "asUrl")
    public abstract ChannelSimpleVO updateChannelSimpleVO(ChannelSimpleVO source);

    /* ---------- 1. 处理单个 ChannelVO 对象 ---------- */
    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "asUrl")
    @Mapping(source = "backgroundUrl", target = "backgroundUrl", qualifiedByName = "asUrl")
    public abstract ChannelVO convertToChannelVO(ChannelVO source);

    // ==================== User相关VO ====================

    /* ---------- 1. 处理 UserHomeVO List ---------- */
    @IterableMapping(elementTargetType = UserHomeVO.class)
    public abstract List<UserHomeVO> updateUserHomeVOs(List<UserHomeVO> sources);

    /* ---------- 2. 处理单个 UserHomeVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "image", source = "image", qualifiedByName = "asUrl")
    public abstract UserHomeVO updateUserHomeVO(UserHomeVO source);

    /* ---------- 1. 处理 UserProfileVO List ---------- */
    @IterableMapping(elementTargetType = UserProfileVO.class)
    public abstract List<UserProfileVO> updateUserProfileVOs(List<UserProfileVO> sources);

    /* ---------- 2. 处理单个 UserProfileVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "image", source = "image", qualifiedByName = "asUrl")
    public abstract UserProfileVO updateUserProfileVO(UserProfileVO source);

    /* ---------- 1. 处理 UserSimpleVO List ---------- */
    @IterableMapping(elementTargetType = UserSimpleVO.class)
    public abstract List<UserSimpleVO> updateUserSimpleVOs(List<UserSimpleVO> sources);

    /* ---------- 2. 处理单个 UserSimpleVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "image", source = "image", qualifiedByName = "asUrl")
    public abstract UserSimpleVO updateUserSimpleVO(UserSimpleVO source);

    // ==================== Comment相关VO ====================

    /* ---------- 1. 处理 CommentVO List ---------- */
    @IterableMapping(elementTargetType = CommentVO.class)
    public abstract List<CommentVO> updateCommentVOs(List<CommentVO> sources);

    /* ---------- 2. 处理单个 CommentVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "user", source = "user")
    @Mapping(target = "previewReplies", source = "previewReplies")
    public abstract CommentVO updateCommentVO(CommentVO source);

    /* ---------- 1. 处理 CommentReplyVO List ---------- */
    @IterableMapping(elementTargetType = CommentReplyVO.class)
    public abstract List<CommentReplyVO> updateCommentReplyVOs(List<CommentReplyVO> sources);

    /* ---------- 2. 处理单个 CommentReplyVO 对象（List 中每个元素都会调用） ---------- */
    @Mapping(target = "user", source = "user")
    @Mapping(target = "replyToUser", source = "replyToUser")
    public abstract CommentReplyVO updateCommentReplyVO(CommentReplyVO source);

    // ==================== 通用转换方法 ====================

    /**
     * 将对象名称列表转换为公共URL列表
     *
     * @param objectNames 对象名称列表
     * @return 公共访问URL列表
     */
    @Named("asUrls")
    @IterableMapping(qualifiedByName = "asUrl")
    public abstract List<String> asUrls(List<String> objectNames);

    /**
     * 将单个对象名称转换为公共URL
     * 处理空值情况，确保即使某些文章没有封面图片也能正常返回
     *
     * @param objectName 对象名称
     * @return 公共访问URL
     */
    @Named("asUrl")
    protected String asUrl(String objectName) {
        // 处理空值情况，确保不会对空值进行处理
        if (objectName == null || objectName.isEmpty()) {
            return null;
        }

        try {
            // 直接处理MinIO对象名称格式
            return minioService.getPublicFileUrlToPublicBucket(objectName);
        } catch (Exception e) {
            // 如果转换过程中出现异常，返回原始对象名称确保不影响其他数据
            log.warn("转换对象名称为URL时出错: {}, 返回原始值", objectName, e);
            return objectName;
        }
    }

}