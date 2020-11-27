package com.soltelec.consola.dao;

import com.soltelec.consola.model.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class UsuarioDaoImpl implements UsuarioDao {
	 
	 String respImplDao;
	 private Query queryDao;
	@Override
	public String validUserPasswDominio(String user, String passw, EntityManager em)  throws NoResultException {
		respImplDao="no";
		queryDao  = em.createNamedQuery("Usuarios.validUserPassw");		
		queryDao.setParameter("nickusuario",user);
		queryDao.setParameter("contrasenia",passw);		
		List<Object[]>  usuario = queryDao.getResultList();
		for (Object[] tuple : usuario) {			
			respImplDao=tuple[0]+";"+tuple[1]+";"+tuple[2]+";"+tuple[3]+";"+tuple[4]+";"+tuple[5];
		}
		//respImplDao=usuario.getCedula();
		return respImplDao;
	}
	@Override	
	public String servRegistroUsuario(InputStream firma,String strAtributos, EntityManager em) throws Exception {
		StringTokenizer tokAtributos = new StringTokenizer(strAtributos, ";");
		Usuario usuario = new Usuario();
		usuario.setRolUsuario(tokAtributos.nextToken());
		usuario.setCedula(tokAtributos.nextToken());
		usuario.setContrasena(tokAtributos.nextToken());
		usuario.setNick(tokAtributos.nextToken());
		usuario.setNombre(tokAtributos.nextToken());
		try {
	    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		    byte[] bytesFirma =  new byte[firma.available()];
		    int nRead;
		    while ((nRead = firma.read(bytesFirma, 0, bytesFirma.length)) != -1) {
		    	  buffer.write(bytesFirma, 0, nRead);
		    }
	    	buffer.flush();    
	    	usuario.setFirmaUsuario(buffer.toByteArray());
		em.persist(usuario);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return usuario.getCedula().toString();
	}
	@Override
	public String servExistenciaNickUsuario(String strNickUsuario, EntityManager em) throws Exception {
		queryDao  = em.createNamedQuery("Usuarios.findByNickusuario");
		queryDao.setParameter("nickusuario",strNickUsuario);
		List<Usuario>  lstUsuario = queryDao.getResultList();
		if(lstUsuario.size()> 0) {
			Usuario user =lstUsuario.iterator().next();
			respImplDao = user.getRolUsuario().concat(";").concat(user.getCedula()).concat(";").concat(user.getContrasena()).concat(";").concat(user.getNick()).concat(";").concat(user.getNombre()); 
		}
		return respImplDao;
	}
	@Override
	public String servExistenciaUsuario(String cedula, EntityManager em) throws Exception {
		respImplDao="no";
		queryDao  = em.createNamedQuery("Usuarios.findUsuario");
		queryDao.setParameter("cedula",cedula);
		List<Usuario>  lstUsuario = queryDao.getResultList();
		if(lstUsuario.size()> 0) {
			Usuario user =lstUsuario.iterator().next();
			respImplDao = user.getRolUsuario().concat(";").concat(user.getCedula()).concat(";").concat(user.getContrasena()).concat(";").concat(user.getNick()).concat(";").concat(user.getNombre()).concat(";").concat(user.getUsuario().toString()); 
		}
		return respImplDao;
	
	} 
	
	
	public byte[]  servFindFirma(String cedula, EntityManager em) throws Exception {
		byte[] firmaUser = null;
		queryDao  = em.createNamedQuery("Usuarios.findUsuario");
		queryDao.setParameter("cedula",cedula);
		List<Usuario>  lstUsuario = queryDao.getResultList();
		if(lstUsuario.size()> 0) {
			Usuario user =lstUsuario.iterator().next();
			firmaUser = user.getFirmaUsuario(); 
		}
	    return firmaUser;
	}
	
	
	@Override
	public String servActualizacionUsuario(InputStream firma, String strAtributos, EntityManager em) throws Exception {
		StringTokenizer tokAtributos = new StringTokenizer(strAtributos, ";");
		Usuario usuario = em.find(Usuario.class, Integer.parseInt(tokAtributos.nextToken()));
		usuario.setRolUsuario(tokAtributos.nextToken());
		usuario.setCedula(tokAtributos.nextToken());
		usuario.setContrasena(tokAtributos.nextToken());
		usuario.setNick(tokAtributos.nextToken());
		usuario.setNombre(tokAtributos.nextToken());
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] bytesFirma = new byte[firma.available()];
			int nRead;
			while ((nRead = firma.read(bytesFirma, 0, bytesFirma.length)) != -1) {
				buffer.write(bytesFirma, 0, nRead);
			}
			buffer.flush();
			usuario.setFirmaUsuario(buffer.toByteArray());
			em.merge(usuario);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuario.getCedula().toString();
	}
}
