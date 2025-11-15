package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.ArticleEntity;
import com.songheqing.microforum.vo.ArticleDetailVO;
import com.songheqing.microforum.vo.ArticleCardVO;
import com.songheqing.microforum.vo.ArticleUserCardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticlesMapper {

    // 查询所有文章，按更新时间降序排序，由新到旧
    List<ArticleCardVO> selectAll(
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("userId") Long userId);

    // 根据用户ID查询文章列表
    List<ArticleUserCardVO> selectByUserId(
            @Param("userId") Long userId,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("currentUserId") Long currentUserId);

    // 根据频道ID查询文章列表
    List<ArticleUserCardVO> selectByChannelId(
            @Param("channelId") Integer channelId,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("userId") Long userId,
            @Param("sort") String sort);

    // 插入文章
    void insert(ArticleEntity article);

    // 查询文章详情
    ArticleDetailVO selectDetailById(@Param("id") Long id, @Param("userId") Long userId);

    // 查询当前楼层并加排他锁
    @Select("SELECT floor_count FROM article WHERE id = #{articleId} FOR UPDATE")
    Integer selectFloorCountForUpdate(@Param("articleId") Long articleId);

    // 增加楼层数
    @Update("UPDATE article SET floor_count = floor_count + 1 WHERE id = #{articleId}")
    void incrementFloorCount(@Param("articleId") Long articleId);

    // 增加评论数
    @Update("UPDATE article SET comment_count = comment_count + 1 WHERE id = #{articleId}")
    void incrementCommentCount(@Param("articleId") Long articleId);

    // 更新文章最后回复时间
    @Update("UPDATE article SET last_reply_time = NOW() WHERE id = #{articleId}")
    void updateLastReplyTime(@Param("articleId") Long articleId);

    // 点赞文章
    @Update("UPDATE article SET like_count = like_count + 1 WHERE id = #{id}")
    void incrementLikeCount(@Param("id") Long id);

    // 取消点赞文章
    @Update("UPDATE article SET like_count = like_count - 1 WHERE id = #{id}")
    void decrementLikeCount(@Param("id") Long id);

    // 更新文章的媒体
    @Update("UPDATE article SET media = #{mediaStr} WHERE id = #{articleId}")
    void updateMedia(Long articleId, String mediaStr);

}
