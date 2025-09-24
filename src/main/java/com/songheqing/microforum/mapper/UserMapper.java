package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.UserEntity;
import com.songheqing.microforum.vo.UserHomeVO;
import com.songheqing.microforum.vo.UserProfileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    UserEntity login(UserEntity user);

    Integer findByNickname(String nickname);

    Integer findByEmail(String email);

    void insert(UserEntity user);

    // 增加用户的文章发送量
    @Update("UPDATE user SET article_send_count = article_send_count + 1 WHERE id = #{userId}")
    void incrementUserArticleCount(@Param("userId") Long userId);

    // 增加用户的评论发送量
    @Update("UPDATE user SET comment_send_count = comment_send_count + 1 WHERE id = #{userId}")
    void incrementUserCommentCount(@Param("userId") Long userId);

    // 根据ID查询用户统计数据
    @Select("SELECT article_send_count, comment_send_count, channel_follow_count, follow_count, fans_count, image, nickname FROM user WHERE id = #{userId}")
    UserProfileVO selectProfileById(@Param("userId") Long userId);

    // 根据ID查询用户主页信息
    @Select("SELECT article_send_count, comment_send_count, channel_follow_count, follow_count, fans_count, image, nickname, like_count, introduction, create_time FROM user WHERE id = #{userId}")
    UserHomeVO selectHomeById(@Param("userId") Long userId);
}