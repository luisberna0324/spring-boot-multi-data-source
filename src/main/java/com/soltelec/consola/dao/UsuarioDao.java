package com.soltelec.consola.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.InputStream;

public interface UsuarioDao {
	public String validUserPasswDominio(String user, String passw, EntityManager em) throws NoResultException;
	public String servRegistroUsuario(InputStream firma, String strAtributos, EntityManager em) throws Exception ;
	public String servExistenciaNickUsuario(String strNickUsuario, EntityManager em) throws Exception ;
	public String servExistenciaUsuario(String cedula, EntityManager em) throws Exception ;
	public String servActualizacionUsuario(InputStream firma, String strAtributos, EntityManager em) throws Exception ;
	public byte[] servFindFirma(String cedula, EntityManager em) throws Exception;
}
