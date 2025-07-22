package com.songheqing.microforum.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ArticleDetailDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer coverType;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;
    private Integer viewCount;
    private List<ImageDTO> coverImageUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private UserSimpleDTO user;
    private ChannelSimpleDTO channel;
}