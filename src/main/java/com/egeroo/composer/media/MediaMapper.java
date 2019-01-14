package com.egeroo.composer.media;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.egeroo.composer.core.base.BaseMapper;

@Mapper
public interface MediaMapper extends BaseMapper {
	public List<Media> getAllMedias(@Param("url") String url);
	public Integer getNextId();
	public Media getMedia(@Param("id") int id, @Param("url") String url);
	public int insertMedia(@Param("media") Media media, @Param("userId") int userId);
	public boolean deleteMedia(@Param("id") int id);
}
