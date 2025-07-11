package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.Channel;
import com.songheqing.microforum.service.ChannelService;
import com.songheqing.microforum.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/channels")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping
    public Result<List<Channel>> list() {
        List<Channel> channels = channelService.list();
        return Result.success(channels);
    }
}