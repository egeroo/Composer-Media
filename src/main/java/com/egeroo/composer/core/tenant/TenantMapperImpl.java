package com.egeroo.composer.core.tenant;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.composer.core.base.BaseDAO;
import com.egeroo.composer.core.tenant.TenantMapperImpl;

public class TenantMapperImpl extends BaseDAO implements TenantMapper{
	private static final Logger LOG = Logger.getLogger(TenantMapperImpl.class);
	
	public List<TenantConnection> getTenantList() {
		SqlSession sqlSession = BaseDAO.getInstance().openSession();
		List<TenantConnection> tenantList = null;
		try{
			TenantMapper tenantMapper = sqlSession.getMapper(TenantMapper.class);
			tenantList = tenantMapper.getTenantList();
		}catch(Exception e){	
			LOG.debug(e);
		}finally {
			sqlSession.close();
		}
		return tenantList;			
	}
	
	public Tenant getTenant(String identifier) {
		SqlSession sqlSession = BaseDAO.getInstance().openSession();
		Tenant tenant = null;
		try{
			TenantMapper tenantMapper = sqlSession.getMapper(TenantMapper.class);
			tenant = tenantMapper.getTenant(identifier);
		}catch(Exception e){	
			LOG.debug(e);
		}finally {
			sqlSession.close();
		}
		return tenant;			
	}
}
