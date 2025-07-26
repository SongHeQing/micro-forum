package com.songheqing.microforum.vo;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ArticleDetailVO {
    private Integer id;
    private String title;
    private String content;
    private Integer coverType;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;
    private Integer viewCount;
    private List<ImageVO> coverImageUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private UserSimpleVO user;
    private ChannelSimpleVO channel;
}