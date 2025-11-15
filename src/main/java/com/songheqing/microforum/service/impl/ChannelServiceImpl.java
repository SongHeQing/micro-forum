package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.constant.MinioConstants;
import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.entity.ChannelOwnerEntity;
import com.songheqing.microforum.mapper.ChannelMapper;
import com.songheqing.microforum.mapper.ChannelOwnerMapper;
import com.songheqing.microforum.request.ChannelRequest;
import com.songheqing.microforum.service.ChannelService;
import com.songheqing.microforum.service.MinioService;
import com.songheqing.microforum.exception.BusinessException;
import com.songheqing.microforum.utils.CurrentHolder;
import com.songheqing.microforum.vo.ChannelSimpleVO;
import com.songheqing.microforum.vo.ChannelVO;
import com.songheqing.microforum.converter.MinioUrlConverter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelServiceImpl implements ChannelService {
    // 限制每个用户每月最多创建5个频道
    private static final int MAX_CHANNELS_PER_MONTH = 1;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ChannelOwnerMapper channelOwnerMapper;

    @Autowired
    private MinioService minioService;

    @Autowired
    private MinioUrlConverter minioUrlConverter;

    @Override
    public List<ChannelSimpleVO> list() {
        return minioUrlConverter.updateChannelSimpleVOs(channelMapper.selectAll());
    }

    @Override
    public ChannelVO getById(Long id) {
        return channelMapper.selectById(id);
    }

    @Transactional
    @Override
    public Long createChannel(ChannelRequest channelRequest, MultipartFile avatar, MultipartFile background) {
        // 获取当前用户ID
        Long currentUserId = CurrentHolder.getCurrentId();
        if (currentUserId == null) {
            throw new BusinessException("用户未登录");
        }

        // 检查用户在最近一个月内创建的频道数量
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<ChannelEntity> recentChannels = channelMapper.selectByCreatorIdAndTime(currentUserId, oneMonthAgo);

        if (recentChannels.size() >= MAX_CHANNELS_PER_MONTH) {
            throw new BusinessException("您已达到本月创建频道的最大数量限制（" + MAX_CHANNELS_PER_MONTH + "个）");
        }

        // 创建频道
        ChannelEntity channel = new ChannelEntity();
        channel.setCreatorId(currentUserId); // 设置创建人ID
        BeanUtils.copyProperties(channelRequest, channel);

        // 如果提供了头像文件，则上传头像
        if (avatar != null && !avatar.isEmpty()) {
            // 使用MinioService上传文件到公共存储桶
            List<String> imageUrls = minioService.uploadFilesToPublicBucket(List.of(avatar),
                    MinioConstants.EntityType.CHANNEL + "/" + MinioConstants.SubPath.AVATAR);
            String imageUrl = imageUrls.get(0);
            channel.setImage(imageUrl);
        }

        // 如果提供了背景文件，则上传背景
        if (background != null && !background.isEmpty()) {
            // 使用MinioService上传文件到公共存储桶
            List<String> imageUrls = minioService.uploadFilesToPublicBucket(List.of(background),
                    MinioConstants.EntityType.CHANNEL + "/" + MinioConstants.SubPath.BACKGROUND);
            String imageUrl = imageUrls.get(0);
            channel.setBackground(imageUrl);
        }
        channelMapper.insert(channel);

        // 将创建者设置为频道主
        ChannelOwnerEntity channelOwner = new ChannelOwnerEntity();
        channelOwner.setChannelId(channel.getId());
        channelOwner.setUserId(currentUserId);
        channelOwner.setType(1); // 1:频道主
        channelOwnerMapper.insert(channelOwner);

        // 返回频道ID
        return channel.getId();
    }

    @Override
    public ChannelEntity updateChannel(Long id, ChannelRequest channelRequest) {
        ChannelEntity channel = new ChannelEntity();
        BeanUtils.copyProperties(channelRequest, channel);
        channelMapper.update(channel);
        return channel;
    }

    @Override
    public String uploadChannelAvatar(Long id, MultipartFile file) {
        ChannelEntity channel = new ChannelEntity();
        // 使用MinioService上传文件到公共存储桶
        List<String> imageUrls = minioService.uploadFilesToPublicBucket(List.of(file),
                MinioConstants.EntityType.CHANNEL + "/" + MinioConstants.SubPath.AVATAR);
        String imageUrl = imageUrls.get(0);

        // 更新频道头像
        channel.setImage(imageUrl);
        channelMapper.update(channel);
        return imageUrl;
    }

    @Override
    public String uploadChannelBackground(Long id, MultipartFile file) {
        ChannelEntity channel = new ChannelEntity();
        // 使用MinioService上传文件到公共存储桶
        List<String> imageUrls = minioService.uploadFilesToPublicBucket(List.of(file),
                MinioConstants.EntityType.CHANNEL + "/" + MinioConstants.SubPath.BACKGROUND);
        String imageUrl = imageUrls.get(0);

        // 更新频道背景
        channel.setBackground(imageUrl);
        channelMapper.update(channel);
        return imageUrl;
    }
}