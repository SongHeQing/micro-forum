package com.songheqing.microforum.mapper;

import com.songheqing.microforum.entity.ImageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    void insertImage(ImageEntity image);
}