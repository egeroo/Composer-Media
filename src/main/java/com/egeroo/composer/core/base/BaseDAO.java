package com.egeroo.composer.core.base;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

import com.egeroo.composer.core.tenant.TenantConnection;
import com.egeroo.composer.core.tenant.TenantMapper;
import com.egeroo.composer.core.tenant.TenantMapperImpl;

@Component
public class BaseDAO {
	private static final Logger LOG = Logger.getLogger(BaseDAO.class);
	private static SqlSessionFactory sqlSession = null;	
	private static Map<String, SqlSessionFactory> sqlSessionTenant = new HashMap<>();	
	
	static {
		try{
			String aResource = "mybatis-config.xml";
	        Reader reader = Resources.getResourceAsReader(aResource); 
	        LOG.debug("reader = " + reader);
	        if(sqlSession==null){
	        	sqlSession = new SqlSessionFactoryBuilder().build(reader);
	        }
	        reader.close();
	        
	        TenantMapper tn = new TenantMapperImpl();	
	        List<TenantConnection> lst = new ArrayList<>();
	        lst = tn.getTenantList();
	        sqlSessionTenant = new HashMap<>();	        	
			
	        for (TenantConnection tenantConnection : lst) {
				addNewTenant(tenantConnection);
			}
		}catch(Exception e){
			LOG.error(e);
		}
	}

	public static SqlSessionFactory getInstance(){
		return sqlSession;
	}
	public static SqlSessionFactory getInstance(String tenantId){		
		return sqlSessionTenant.get(tenantId);
	}
	
	public static void addNewTenant(TenantConnection tenantConnection){		
		if(!sqlSessionTenant.containsKey(tenantConnection.getSchema_name())){
			DataSource ds = getDataSourceTenant(tenantConnection);
			TransactionFactory trxFactory = new JdbcTransactionFactory();
			Environment env = new Environment("dev", trxFactory, ds);			
			Configuration config = new Configuration(env);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
//			BaseDAO dsd = new BaseDAO();
//			config.addMapper(IntentMapper.class);
//			config.addMapper(AnswerMapper.class);
//			config.addMapper(ContentMapper.class);
//			config.addMapper(VariableMapper.class);
//			config.addMapper(LineMapper.class);
			registerMappers(config);
			sqlSessionTenant.put(tenantConnection.getIdentifier(), factory);			
		}
	}
	
	public static DataSource getDataSourceTenant(TenantConnection tenant){
		BasicDataSource ds = new BasicDataSource();
		try{
			ds.setDriverClassName("org.postgresql.Driver");
			ds.setUrl("jdbc:postgresql://"+tenant.getSchema_server()+":"+tenant.getSchema_server_port()+"/"+tenant.getSchema_name());
			ds.setUsername(tenant.getSchema_username());
			ds.setPassword(tenant.getSchema_password());					
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e);
		}
		return ds;
	}	
	
	/**
	 * ini untuk daftarin mappers yang akan dipanggil untuk SQL nya
	 * penambahan mappers dengan cara scan package
	 * @param config
	 */
	protected static void registerMappers(Configuration config){
		config.addMappers("com.egeroo.composer");
	}
}
