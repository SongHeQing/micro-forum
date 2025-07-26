package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.songheqing.microforum.entity.ChannelEntity;

import java.util.List;

@Mapper
public interface ChannelMapper {
    @Select("select id, channelname, image, user_count, create_time, update_time from channel")
    List<ChannelEntity> selectAll();
}