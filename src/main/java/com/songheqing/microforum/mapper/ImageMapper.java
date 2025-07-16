package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    void insertImage(Image image);
}