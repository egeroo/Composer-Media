package com.egeroo.composer.core.base;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class BaseMapperImpl extends BaseDAO implements BaseMapper {
	protected SqlSession sqlSession = null;
	protected String tenantIdentifier = "";
	protected static final Logger LOG = Logger.getLogger(BaseMapperImpl.class);
	
	public SqlSession getSqlSession() {
		return this.sqlSession;
	}
	
	public void setSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void commit() {
		if(this.sqlSession != null) {
			this.sqlSession.commit();
		}
	}
	
	public void closeConnection() {
		if(this.sqlSession != null) {
			this.sqlSession.close();
		}
	}
	
	public void rollback() {
		if(this.sqlSession != null) {
			this.sqlSession.rollback(true);
		}
	}
}
