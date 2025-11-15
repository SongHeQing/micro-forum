package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import com.songheqing.microforum.entity.ChannelEntity;
import com.songheqing.microforum.vo.ChannelSimpleVO;
import com.songheqing.microforum.vo.ChannelVO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChannelMapper {
    /*
     * 查询所有频道的简单信息
     * 使用 @Results 注解显式映射字段，将数据库中的 image 字段映射到 ChannelSimpleVO 的 imageUrl 属性
     */
    @Select("SELECT id, channelname, image FROM channel")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "channelname", column = "channelname"),
            @Result(property = "imageUrl", column = "image")
    })
    List<ChannelSimpleVO> selectAll();

    @Select("SELECT id, channelname, image as imageUrl, background as backgroundUrl, dominant_color, description, detail, user_count, article_count, create_time FROM channel WHERE id = #{id}")
    ChannelVO selectById(@Param("id") Long id);

    @Select("SELECT id, creator_id, channelname, image, background, dominant_color, description, detail, user_count, article_count, create_time FROM channel WHERE creator_id = #{creatorId} AND create_time >= #{sinceTime}")
    List<ChannelEntity> selectByCreatorIdAndTime(@Param("creatorId") Long creatorId,
            @Param("sinceTime") LocalDateTime sinceTime);

    @Insert("INSERT INTO channel(creator_id, channelname, description, detail, image, background, dominant_color) VALUES(#{creatorId}, #{channelname}, #{description}, #{detail}, #{image}, #{background}, #{dominantColor})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChannelEntity channel);

    /*
     * 动态更新频道信息，只更新非空字段
     */
    int update(ChannelEntity channel);
}