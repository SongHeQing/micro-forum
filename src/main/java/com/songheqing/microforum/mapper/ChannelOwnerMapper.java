package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.ChannelOwnerEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChannelOwnerMapper {
    @Select("SELECT id, channel_id, user_id, type, create_time, update_time FROM channel_owner")
    List<ChannelOwnerEntity> selectAll();

    @Select("SELECT id, channel_id, user_id, type, create_time, update_time FROM channel_owner WHERE id = #{id}")
    ChannelOwnerEntity selectById(@Param("id") Long id);

    @Select("SELECT id, channel_id, user_id, type, create_time, update_time FROM channel_owner WHERE channel_id = #{channelId}")
    List<ChannelOwnerEntity> selectByChannelId(@Param("channelId") Long channelId);

    @Select("SELECT id, channel_id, user_id, type, create_time, update_time FROM channel_owner WHERE user_id = #{userId}")
    List<ChannelOwnerEntity> selectByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO channel_owner(channel_id, user_id, type) VALUES(#{channelId}, #{userId}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChannelOwnerEntity channelOwner);

    @Update("UPDATE channel_owner SET channel_id=#{channelId}, user_id=#{userId}, type=#{type} WHERE id=#{id}")
    int update(ChannelOwnerEntity channelOwner);

    @Delete("DELETE FROM channel_owner WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
}