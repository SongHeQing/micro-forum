package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.songheqing.microforum.entity.Channel;

import java.util.List;

@Mapper
public interface ChannelMapper {
    @Select("select id, channelname, image, user_conut, create_time, update_time from channel")
    List<Channel> selectAll();
}