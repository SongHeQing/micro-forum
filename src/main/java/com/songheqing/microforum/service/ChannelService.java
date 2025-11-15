package com.songheqing.microforum.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.request.ChannelRequest;
import com.songheqing.microforum.vo.ChannelSimpleVO;
import com.songheqing.microforum.vo.ChannelVO;

public interface ChannelService {
    List<ChannelSimpleVO> list();

    ChannelVO getById(Long id);

    Long createChannel(ChannelRequest channelRequest, MultipartFile avatar, MultipartFile background);

    ChannelEntity updateChannel(Long id, ChannelRequest channelRequest);

    String uploadChannelAvatar(Long id, MultipartFile file);

    String uploadChannelBackground(Long id, MultipartFile file);
}
