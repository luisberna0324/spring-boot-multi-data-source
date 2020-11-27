package com.soltelec.consola.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FactoryDaoImpl implements FactoryDao {


    @PersistenceContext(unitName = "persistence.postgres")
    private EntityManager postgresDataSource;
    @PersistenceContext(unitName = "persistence.mysql")
    private EntityManager mysqlDatasource;


    @Override
    public UsuarioDao createUsuarioDao() {
        UsuarioDao usrDao = new UsuarioDaoImpl();
        return usrDao;
    }

    @Override
    public EntityManager getEntityManager() {
        return postgresDataSource;
    }

    @Override
    public ProcStreamingRecepcionDao createProcStreamingRecepcionDao() {
        ProcStreamingRecepcionDao procStreamingRecepcionDao = new ProcStreamingRecepcionDaoImpl();
        return procStreamingRecepcionDao;
    }

    @Override
    public EntityManager getEntityManagerMysql() {
        return mysqlDatasource;
    }

    @Override
    public CompBussinnesMysql createCompBussinnesMysqlDao() {
        CompBussinnesMysql compBussinnesMysql = new CompBussinnesMysqlDao();
        return compBussinnesMysql;
    }

}
