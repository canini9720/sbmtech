package com.sbmtech.repository;

import javax.activation.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JdbcCommonDao {
	
	private DataSource dataSource;
	
	
	@PersistenceContext()
	private EntityManager em;
	/*
	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	
	public DataSource getDataSource() {
		return dataSource;
	}
	

*/
	public EntityManager getEm() {
		return em;
	}
}

