package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.mapper.ChannelMapper;
import com.songheqing.microforum.pojo.Channel;
import com.songheqing.microforum.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> list() {
        return channelMapper.selectAll();
    }
}