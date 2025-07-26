package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.service.ChannelService;
import com.songheqing.microforum.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/channels")
@SecurityRequirement(name = "Authorization")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping
    public Result<List<ChannelEntity>> list() {
        List<ChannelEntity> channels = channelService.list();
        return Result.success(channels);
    }
}