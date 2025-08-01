package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.mapper.ChannelMapper;
import com.songheqing.microforum.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ChannelEntity> list() {
        return channelMapper.selectAll();
    }
}