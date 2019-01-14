package com.egeroo.composer.media;

import java.util.List;

import com.egeroo.composer.core.base.BaseMapperImpl;

public class MediaMapperImpl extends BaseMapperImpl implements MediaMapper {
	
	public MediaMapperImpl(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
		this.sqlSession = super.getInstance(this.tenantIdentifier).openSession();
	}

	@Override
	public List<Media> getAllMedias(String url) {
		List<Media> result = null;
		try {
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			result = mapper.getAllMedias(url);
		} catch(Exception e) {
			LOG.debug(e);
		}
		return result;
	}

	@Override
	public Integer getNextId() {
		Integer result = null;
		try {
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			result = mapper.getNextId();
			if (result == null) {
				result = 1;
			}
		} catch(Exception e) {
			LOG.debug(e);
		}
		return result;
	}

	@Override
	public Media getMedia(int id, String url) {
		Media result = null;
		try {
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			result = mapper.getMedia(id, url);
		} catch(Exception e) {
			LOG.debug(e);
		}
		return result;
	}

	@Override
	public int insertMedia(Media media, int userId) {
		int result = 0;
		try {
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			result = mapper.insertMedia(media, userId);
		} catch(Exception e) {
			LOG.debug(e);
		}
		return result;
	}

	@Override
	public boolean deleteMedia(int id) {
		try {
			MediaMapper mapper = sqlSession.getMapper(MediaMapper.class);
			mapper.deleteMedia(id);
		} catch(Exception e) {
			LOG.debug(e);
		}
		return true;
	}

}
