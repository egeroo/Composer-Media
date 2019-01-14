package com.egeroo.composer.core.base;

import org.apache.log4j.Logger;

import com.egeroo.composer.core.error.CoreException;

public class BaseService {
	private static final Logger LOG = Logger.getLogger(BaseService.class);
	
	public void closeMapper(BaseMapper mapper) {
		if (mapper != null) {
			mapper.closeConnection();
		}
	}
	
	public void rollback(BaseMapper mapper) {
		if (mapper != null) {
			mapper.rollback();
		}
	}
	
	public void throwException(CoreException e) {
		LOG.error("ERROR");
		e.printStackTrace();
		throw e;
	}
}
