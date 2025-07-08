package com.songheqing.microforum.mapper;

import com.songheqing.microforum.pojo.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ChannelMapper {
    @Select("select id, channelname, image, user_conut, create_time, update_time from channel")
    List<Channel> selectAll();
}