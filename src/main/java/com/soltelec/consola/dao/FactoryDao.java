package com.soltelec.consola.dao;


import javax.persistence.EntityManager;


public interface FactoryDao {
	
	public UsuarioDao createUsuarioDao();
	public ProcStreamingRecepcionDao createProcStreamingRecepcionDao();
	public CompBussinnesMysql createCompBussinnesMysqlDao();
	public EntityManager getEntityManager();
	public EntityManager getEntityManagerMysql();

}
