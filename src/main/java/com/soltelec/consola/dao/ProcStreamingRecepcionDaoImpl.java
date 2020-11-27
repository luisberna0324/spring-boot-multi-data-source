package com.soltelec.consola.dao;

import com.soltelec.consola.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class ProcStreamingRecepcionDaoImpl implements ProcStreamingRecepcionDao {
	@PersistenceContext(unitName = "SARTPU")
	private EntityManager em;
	String respImplDao;
	private Query queryDao;

	@Override
	public String procStreamingDocPropiedad(String streaming, EntityManager em) {
		return "1";	
	}

	@Override
	public String servPersistPaso1Recepcion(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		Recepcion recepcion = new Recepcion();
		recepcion.setPlaca(flujo.nextToken());
		recepcion.setNroLicencia(flujo.nextToken());
		recepcion.setMarca(flujo.nextToken());
		recepcion.setNroSerie(flujo.nextToken());
		recepcion.setNroChasis(flujo.nextToken());
		recepcion.setLinea(flujo.nextToken());
		recepcion.setEsMismo(true);
		em.persist(recepcion);
		return recepcion.getIdRecepcion().toString();
	}
	@Override
	public String servFindDatosUbicConductor(String streaming, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(streaming));
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
		return  recepcion.getCedulaConduc().concat(";").concat(recepcion.getApellidosConduc()).concat(";").concat(recepcion.getNombresConduc()).concat(";").concat(recepcion.getDireccionConduc()).concat(";").concat(recepcion.getCorreo()).concat(";").concat(recepcion.getTelefono());				
	}
	
	@Override
	public String servFindDatosConductor(String streaming, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(streaming));		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
		return recepcion.getPlaca().concat(";").concat(recepcion.getCiudadaniaConduc()).concat(";").concat(recepcion.getCedulaConduc()).concat(";").concat(recepcion.getApellidosConduc()).concat(";").concat(recepcion.getNombresConduc()).concat(";").concat(recepcion.getServicio()).concat(";").concat(preRevision.getNacionalidad());		
	}
	
	
	@Override
	public String servFindDatosPropietario(String streaming, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(streaming));		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
		return recepcion.getPlaca().concat(";").concat(recepcion.getCiudadania()).concat(";").concat(recepcion.getApellidos()).concat(";").concat(recepcion.getNombres()).concat(";").concat(recepcion.getCedula());		
	}
	@Override
	public String servActualizarDatosConductor(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(flujo.nextToken()));
		Recepcion recepcion = em.find(Recepcion.class, preRevision.getFkRecepcion() );		
		recepcion.setDireccionConduc(flujo.nextToken());
		recepcion.setTelefono(flujo.nextToken());
		recepcion.setCorreo(flujo.nextToken());
		recepcion.setCiudadaniaConduc(flujo.nextToken().trim());
		recepcion.setCedulaConduc(flujo.nextToken());
		recepcion.setApellidosConduc(flujo.nextToken().toUpperCase());
		recepcion.setNombresConduc(flujo.nextToken().toUpperCase());
		recepcion.setIdDepartamento(Integer.parseInt(flujo.nextToken()));
		recepcion.setIdCiudad(Integer.parseInt(flujo.nextToken()));
		recepcion.setUsuario(Integer.parseInt(flujo.nextToken()));
		em.merge(recepcion);
		return "1";		
	}
	

	@Override
	public String servPersistRegistroCedula(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		Recepcion recepcion = em.find(Recepcion.class, flujo.nextToken());
		recepcion.setCedulaConduc(flujo.nextToken());
		recepcion.setApellidosConduc(flujo.nextToken());
		recepcion.setNombresConduc(flujo.nextToken());
		recepcion.setDireccionConduc(flujo.nextToken());
		em.merge(recepcion);
		return "1";
	}
	
	@Override
	public String setFechaInicioRecepcion(Integer fkRevision,Integer usuario, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,fkRevision);		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
		Calendar fecha = Calendar.getInstance();
		recepcion.setFechaRegistro(fecha.getTime());
		recepcion.setUsuario(usuario);
		em.merge(recepcion);
		return recepcion.getNroFactura();
	}

	@Override
	public String procStreamingDocCedula(String streaming, EntityManager em) {
		try {
			Recepcion recepcion = new Recepcion();
			recepcion.setCedulaConduc(streaming.substring(25, 36).trim());
			recepcion.setNombresConduc(streaming.substring(2, 19).trim());
			recepcion.setApellidosConduc(streaming.substring(20, 40).trim());
			String car;
			int pos = 0;
			for (int i = 183;; i++) {
				car = streaming.substring(i, (i + 1));
				if (car.trim().length() > 0) {
					pos = i;
					break;
				}
			}
			recepcion.setPlaca(streaming.substring(pos, pos + 6).trim());
			pos = pos + 6;
			respImplDao = recepcion.getNroLicencia().concat(";").concat(recepcion.getPlaca()).concat(";");
		} catch (Exception ex) {
			respImplDao = "noLect";
		}
		return respImplDao;
	}
	@Override
	public String servPersistRegistroRechazo( String placa,String descRechazo, Integer typeHandler,String observacion,Integer usuario, EntityManager em) {
		RegistroRechazo regRechazo =new RegistroRechazo();		
		regRechazo.setUsuario(0); 
		regRechazo.setDescripcionRechazo(descRechazo.trim()); 
		regRechazo.setPlaca(placa.trim());		
		regRechazo.setTypeHandler(typeHandler); 
		regRechazo.setObservacion(observacion);
		regRechazo.setUsuario(usuario);
		Calendar calF = Calendar.getInstance();
		regRechazo.setFechaRegistro(calF.getTime());		
		em.persist(regRechazo);
		return regRechazo.getIdRechazo().toString();
	}

	@Override
	public String servRegManualRunt(String streaming, EntityManager em) {
		Calendar fecha = Calendar.getInstance();
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		PreRevision preRevision =new PreRevision();
		Recepcion recepcion =new Recepcion();
		PreRevision preRevisionExist=null ;
		Recepcion recepcionExist=null ;
		
		Integer usuario =Integer.parseInt(flujo.nextToken());		
		
		String ctxRevision =flujo.nextToken();
		Integer fkRevision = Integer.parseInt(flujo.nextToken());
		if(ctxRevision.equalsIgnoreCase("2")) {
			preRevisionExist = em.find(PreRevision.class, fkRevision);
			recepcionExist = em.find(Recepcion.class, preRevisionExist.getFkRecepcion());
			preRevision.setNoLLanta(preRevisionExist.getNoLLanta());			
			preRevision.setKilometraje(preRevisionExist.getKilometraje());			
			preRevision.setNoExosto(preRevisionExist.getNoExosto());			
			preRevision.setTipoVehiculo(preRevisionExist.getTipoVehiculo());	
			preRevision.setNroCertificado(preRevisionExist.getNroCertificado());
			preRevision.setEmpresaCertificado(preRevisionExist.getEmpresaCertificado());			
			preRevision.setFechaVencCertificado(preRevisionExist.getFechaVencCertificado());			
			preRevision.setVidriosPolarizados(preRevisionExist.getVidriosPolarizados());			
			preRevision.setBlindado(preRevisionExist.getBlindado());			
			preRevision.setEsEnsenanza(preRevisionExist.getEsEnsenanza());			
			preRevision.setEsAmbulancia(preRevisionExist.getEsAmbulancia());
			preRevision.setEsBombero(preRevisionExist.getEsBombero());			
			recepcion.setNroEjes(recepcionExist.getNroEjes());			
			recepcion.setPoseePacha(recepcionExist.getPoseePacha());
			recepcion.setEsScooter(recepcionExist.getEsScooter());
			recepcion.setPesoBruto(recepcionExist.getPesoBruto());
			recepcion.setCombustible(recepcionExist.getCombustible());
			recepcion.setPlaca(recepcionExist.getPlaca());
			recepcion.setModelo(recepcionExist.getModelo());
			recepcion.setNroSillas(recepcionExist.getNroSillas());
			recepcion.setColor(recepcionExist.getColor());
			recepcion.setNroMotor(recepcionExist.getNroMotor());
			preRevision.setTiemposMotor(preRevisionExist.getTiemposMotor());
			flujo.nextToken();
			flujo.nextToken();
			flujo.nextToken();
			recepcion.setCiudadania(flujo.nextToken());
			recepcion.setCedula(flujo.nextToken());
			recepcion.setClaseVehiculo(recepcionExist.getClaseVehiculo());
		}else {
			recepcion.setPlaca(flujo.nextToken().toUpperCase());
			recepcion.setCombustible(flujo.nextToken());
			recepcion.setModelo(flujo.nextToken());
			recepcion.setCiudadania(flujo.nextToken());
			recepcion.setCedula(flujo.nextToken());
			recepcion.setClaseVehiculo(flujo.nextToken());			
		}
		preRevision.setUsuario(usuario);		
		
		recepcion.setCiudadaniaConduc(recepcion.getCiudadania());
					
		preRevision.setCategoria(10);
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
			preRevision.setCategoria(4); 
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
			preRevision.setCategoria(5); 
		}
		if(ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setServicio(recepcionExist.getServicio());
			recepcion.setNroEjes(recepcionExist.getNroEjes());			
			flujo.nextToken();
			flujo.nextToken();
			flujo.nextToken();
			flujo.nextToken();	
		}else {
			recepcion.setServicio(flujo.nextToken());
			recepcion.setNroEjes(flujo.nextToken());			
			recepcion.setPesoBruto("0");
			recepcion.setNroSillas("0");		
			recepcion.setColor(flujo.nextToken().trim());	
		}		
		recepcion.setMarca("SIN MARCA");
		recepcion.setLinea("SIN LINEAS");
		recepcion.setEntidadSoat("No Asignado");
		recepcion.setCarroceria("0");
		recepcion.setApellidos(" ");
		recepcion.setNombres(" ");
		recepcion.setCilindrada("0");
		recepcion.setEdoVehiculo("ACTIVO");
		recepcion.setEsEnsenanza("NO");
		recepcion.setNroFactura(" ");
		recepcion.setFechaExpedicionSoat(null);
		recepcion.setFechaFinExpedicionSoat(null);
		recepcion.setFechaMatricula(null);
		recepcion.setFechaInicExpedicionSoat(null);
		recepcion.setFechaRegistro(fecha.getTime());
		recepcion.setNroPuertas("0");		
		recepcion.setNroPolizaSoat("0");
		recepcion.setNroLicencia("0");
		recepcion.setNroVin("0");
		recepcion.setNroChasis("0");
		recepcion.setNroMotor("0");
		recepcion.setNroSerie("0");
		recepcion.setCarroceria("0");
		recepcion.setCapacidaCarga("0");
		recepcion.setCapacidad("0");
		recepcion.setNroLicencia("0");		
		recepcion.setFechaMatricula(fecha.getTime());
		recepcion.setFechaExpedicionSoat(fecha.getTime());
		recepcion.setFechaFinExpedicionSoat(fecha.getTime());		
		recepcion.setFechaInicExpedicionSoat(fecha.getTime());
		
		em.persist(recepcion);		
				
		preRevision.setPlaca(recepcion.getPlaca());
		preRevision.setFkRecepcion(recepcion.getIdRecepcion());					
		preRevision.setFechaRevision(fecha.getTime());
		preRevision.setCulminado(0);
		preRevision.setNoTurno(0);
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("AUTOMOVIL") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMIONETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMPERO")) {
		    preRevision.setTipoVehiculo(1);
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
			preRevision.setTipoVehiculo(4);	
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("CAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("TRACTOCAMION")  || recepcion.getClaseVehiculo().equalsIgnoreCase("VOLQUETA")  ) {
			preRevision.setTipoVehiculo(3);
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
			preRevision.setTipoVehiculo(5);			   
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("BUSETA")) {
			preRevision.setTipoVehiculo(3);
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MICROBUS")) {
			preRevision.setTipoVehiculo(3);
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("BUS")) {
			preRevision.setTipoVehiculo(3);
	   	}
		preRevision.setVacio(false);
		preRevision.setFecha(fecha.getTime());
		preRevision.setPrimeraVez(Integer.parseInt(flujo.nextToken().trim()));
		preRevision.setNacionalidad(flujo.nextToken().trim());
		
		
		String esRtm=flujo.nextToken().trim();
		if(esRtm.equalsIgnoreCase("N")) {
			preRevision.setEsRTM(Boolean.parseBoolean("true"));
		}else {
			preRevision.setEsRTM(Boolean.parseBoolean("false"));
		}			
		em.persist(preRevision);						
		return preRevision.getNoRevision().toString()+";"+recepcion.getPlaca()+";"+recepcion.getCombustible()+";"+recepcion.getClaseVehiculo()+";"+recepcion.getNroEjes()+";"+recepcion.getServicio() +";"+recepcion.getCedula()+";"+ recepcion.getPesoBruto() +";"+recepcion.getCiudadaniaConduc()+";"+preRevision.getNacionalidad() ;
	}

	@Override
	public String servRegAutomatizadoRunt(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		Recepcion recepcion = new Recepcion();
		PreRevision preRevisionExist=null ;
		Recepcion recepcionExist=null ;
		Integer fkRevision = Integer.parseInt(flujo.nextToken().trim());
		String ctxRevision =flujo.nextToken().trim();
		
		if(ctxRevision.equalsIgnoreCase("2")) {
			preRevisionExist = em.find(PreRevision.class, fkRevision);
			recepcionExist = em.find(Recepcion.class, preRevisionExist.getFkRecepcion());
		}
		recepcion.setUsuario(0);
		recepcion.setCedula(flujo.nextToken());		
		recepcion.setCiudadania(flujo.nextToken());
		recepcion.setCiudadaniaConduc(recepcion.getCiudadania());
		recepcion.setPlaca(flujo.nextToken());
		recepcion.setEdoVehiculo(flujo.nextToken());
		recepcion.setNroLicencia(flujo.nextToken().trim());				
		recepcion.setServicio(flujo.nextToken().trim());
		recepcion.setClaseVehiculo(flujo.nextToken().trim());
		recepcion.setMarca(flujo.nextToken().trim());		
		recepcion.setLinea(flujo.nextToken().trim());		
		recepcion.setModelo(flujo.nextToken().trim());
		if (ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setColor(recepcionExist.getColor());
			recepcion.setEsScooter(recepcionExist.getEsScooter());
			flujo.nextToken();
		} else {
			recepcion.setColor(flujo.nextToken().trim());
		}
		recepcion.setNroSerie(flujo.nextToken().trim());
		recepcion.setNroMotor(flujo.nextToken().trim());
			
		recepcion.setNroChasis(flujo.nextToken().trim());		
		recepcion.setNroVin(flujo.nextToken().trim());
		recepcion.setCilindrada(flujo.nextToken().trim());		
		recepcion.setCarroceria(flujo.nextToken().trim());
		
				
		if(ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setCombustible(recepcionExist.getCombustible());
			flujo.nextToken();
		}else {
			recepcion.setCombustible(flujo.nextToken().trim());			
		}		
		recepcion.setApellidos(" ");
		recepcion.setNombres(" ");
		recepcion.setNroFactura(" ");
		Calendar fecha = Calendar.getInstance();
		String strFecha = flujo.nextToken();
		String[] parseoFecha = strFecha.split("/");
		fecha.set(Calendar.DATE,Integer.parseInt(parseoFecha[0].trim()));
		fecha.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fecha.set(Calendar.YEAR,Integer.parseInt(parseoFecha[2].trim()));
		recepcion.setFechaMatricula(fecha.getTime());
		recepcion.setEsEnsenanza(flujo.nextToken().trim());
		recepcion.setNroPuertas(flujo.nextToken().trim());
		recepcion.setNroPolizaSoat(flujo.nextToken().trim());
		strFecha = flujo.nextToken();
		parseoFecha = strFecha.split("/");
		fecha.set(Calendar.DATE,Integer.parseInt(parseoFecha[0].trim()));
		fecha.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fecha.set(Calendar.YEAR,Integer.parseInt(parseoFecha[2].trim()));
		recepcion.setFechaExpedicionSoat(fecha.getTime());	
		strFecha = flujo.nextToken();
		parseoFecha = strFecha.split("/");
		fecha.set(Calendar.DATE,Integer.parseInt(parseoFecha[0].trim()));
		fecha.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fecha.set(Calendar.YEAR,Integer.parseInt(parseoFecha[2].trim()));
		recepcion.setFechaInicExpedicionSoat(fecha.getTime());
		parseoFecha = flujo.nextToken().split("/");
		fecha.set(Calendar.DATE,Integer.parseInt(parseoFecha[0].trim()));
		fecha.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fecha.set(Calendar.YEAR,Integer.parseInt(parseoFecha[2].trim()));
		recepcion.setFechaFinExpedicionSoat(fecha.getTime());
		recepcion.setEntidadSoat(flujo.nextToken().trim());
		recepcion.setCapacidaCarga(flujo.nextToken().trim());
		recepcion.setCapacidad("0");
		
		Integer pesoBruto;		
		if(ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setPesoBruto(recepcionExist.getPesoBruto());
			flujo.nextToken();
		}else {
			recepcion.setPesoBruto(flujo.nextToken().trim());
			try {
				 pesoBruto = Integer.parseInt(recepcion.getPesoBruto());			
			}catch(NumberFormatException ex) {
				recepcion.setPesoBruto("0");			
			}
		}
		
		if(ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setNroSillas(recepcionExist.getNroSillas());
			flujo.nextToken();
		}else {
			recepcion.setNroSillas(flujo.nextToken().trim());
			try {
				 pesoBruto = Integer.parseInt(recepcion.getNroSillas());			
			}catch(NumberFormatException ex) {
				recepcion.setNroSillas("2");			
			}
		}	
		
		if(ctxRevision.equalsIgnoreCase("2")) {
			recepcion.setNroEjes(recepcionExist.getNroEjes());
			flujo.nextToken();
		}else {
			recepcion.setNroEjes(flujo.nextToken().trim());
			try {
				 pesoBruto = Integer.parseInt(recepcion.getNroEjes());			
			}catch(NumberFormatException ex) {
				recepcion.setNroEjes("2");			
			}
		}		
		recepcion.setNombresConduc(flujo.nextToken().trim().toUpperCase());
		recepcion.setApellidosConduc(flujo.nextToken().trim().toUpperCase());
		em.persist(recepcion);
		PreRevision preRevision = new PreRevision();
		preRevision.setPlaca(recepcion.getPlaca());
		preRevision.setFkRecepcion(recepcion.getIdRecepcion());	
		preRevision.setCulminado(0);
		preRevision.setNoTurno(0);		
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("AUTOMOVIL") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMIONETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMPERO")) {
		    preRevision.setTipoVehiculo(1);
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
			preRevision.setTipoVehiculo(4);
			if(ctxRevision.equalsIgnoreCase("2")) {
				recepcion.setEsScooter(recepcionExist.getEsScooter());				
			}else {
				recepcion.setEsScooter(false);
			}
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("CAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("TRACTOCAMION")  || recepcion.getClaseVehiculo().equalsIgnoreCase("VOLQUETA")  ) {
			preRevision.setTipoVehiculo(3);
			if(ctxRevision.equalsIgnoreCase("2")) {
				recepcion.setPoseePacha(recepcionExist.getPoseePacha());				
			}else {
				recepcion.setPoseePacha(false);
			}
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
			preRevision.setTipoVehiculo(5);			   
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("BUSETA")) {
			preRevision.setTipoVehiculo(3);
			if(ctxRevision.equalsIgnoreCase("2")) {
				recepcion.setPoseePacha(recepcionExist.getPoseePacha());				
			}else {
				recepcion.setPoseePacha(false);
			}
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MICROBUS")) {
			preRevision.setTipoVehiculo(3);
			if(ctxRevision.equalsIgnoreCase("2")) {
				recepcion.setPoseePacha(recepcionExist.getPoseePacha());				
			}else {
				recepcion.setPoseePacha(false);
			}
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("BUS")) {
			preRevision.setTipoVehiculo(3);
			if(ctxRevision.equalsIgnoreCase("2")) {
				recepcion.setPoseePacha(recepcionExist.getPoseePacha());				
			}else {
				recepcion.setPoseePacha(false);
			}
	   	}		
		preRevision.setVacio(false);
		preRevision.setPrimeraVez(Integer.parseInt(flujo.nextToken().trim()));
		String esRtm=flujo.nextToken().trim();
		if(esRtm.equalsIgnoreCase("N")) {
			preRevision.setEsRTM(Boolean.parseBoolean("true"));
		}else {
			preRevision.setEsRTM(Boolean.parseBoolean("false"));
		}
		 if(flujo.hasMoreElements()) {
		    preRevision.setUltimaRevision(flujo.nextToken());
		 }   
		fecha = Calendar.getInstance();
		preRevision.setFechaRevision(fecha.getTime());
		preRevision.setFecha(fecha.getTime());
		preRevision.setCategoria(10);
		if(ctxRevision.equalsIgnoreCase("2")) {
			preRevision.setNoLLanta(preRevisionExist.getNoLLanta());			
			preRevision.setTipoVehiculo(preRevisionExist.getTipoVehiculo());	
			preRevision.setNroCertificado(preRevisionExist.getNroCertificado());
			preRevision.setEmpresaCertificado(preRevisionExist.getEmpresaCertificado());			
			preRevision.setFechaVencCertificado(preRevisionExist.getFechaVencCertificado());			
			preRevision.setVidriosPolarizados(preRevisionExist.getVidriosPolarizados());			
			preRevision.setBlindado(preRevisionExist.getBlindado());			
			preRevision.setEsEnsenanza(preRevisionExist.getEsEnsenanza());			
			preRevision.setEsAmbulancia(preRevisionExist.getEsAmbulancia());
			preRevision.setEsBombero(preRevisionExist.getEsBombero());	
			preRevision.setTiemposMotor(preRevisionExist.getTiemposMotor());
			preRevision.setKilometraje(preRevisionExist.getKilometraje());
			preRevision.setNoExosto(preRevisionExist.getNoExosto());			
		}
		
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
			preRevision.setCategoria(4); 
		}
		if(recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
			preRevision.setCategoria(5); 
		}
		preRevision.setNacionalidad("N");
		em.persist(preRevision);
		return preRevision.getNoRevision().toString()+";"+recepcion.getPlaca()+";"+recepcion.getCombustible()+";"+recepcion.getClaseVehiculo()+";"+recepcion.getNroEjes()+" ;"+recepcion.getServicio() +";"+recepcion.getCedula()+";"+recepcion.getPesoBruto()+";"+recepcion.getCiudadania()+";"+ recepcion.getNroSillas();
	}

	@Override
	public String servFindOrdenPreRevision(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		String tramaServOrden = "";
		Calendar fechaHoy = Calendar.getInstance();		
		queryDao  = em.createNamedQuery("PreRevision.findOrdenPrerevision");			
		queryDao.setParameter("fechaRevision",fechaHoy.getTime());		
		queryDao.setParameter("culminado",6);
		queryDao.setParameter("categoria",Integer.parseInt(flujo.nextToken()));	
		queryDao.setParameter("tipoRevision",Boolean.parseBoolean(flujo.nextToken()));
		queryDao.setParameter("ctxInspeccion",Integer.parseInt(flujo.nextToken()));
		queryDao.setMaxResults(12);
		Timestamp fecha;
		Boolean esRtm;
		List<Object[]> lstScalar = queryDao.getResultList();			
		Integer idRevision;
		Integer condicion; 
		Integer nroTurno;
		for (Object[] result : lstScalar ) {
			esRtm =(Boolean) result[3];
			fecha = (Timestamp)	result[4];			
			idRevision = (Integer)result[8];
			condicion= (Integer)result[9];			
			nroTurno= (Integer)result[10];
			tramaServOrden = tramaServOrden.concat((String) result[0]).concat(";").concat((String) result[1]).concat(";").concat((String) result[2]).concat(";").concat(String.valueOf(esRtm)).concat(";").concat(String.valueOf(fecha)).concat(";").concat((String) result[5]).concat(",").concat((String) result[6]).concat("; ").concat((String)result[7]).concat(";").concat(idRevision.toString()).concat(";").concat(condicion.toString().trim()).concat(";").concat(nroTurno.toString().trim()).concat(";").concat((String)result[11]).concat("]");		
		}
		if(lstScalar.size()==0) {
			tramaServOrden="0";	
		}
		return tramaServOrden;
		
	}
	
	@Override
	public String setAnulacionPreRevision(Integer idPreRevision,  EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class, idPreRevision);
		preRevision.setCulminado(10);
		em.merge(preRevision);
		return "1";
	}
	
	@Override
	public String servInspeccionPreRevision(Integer idPreRevision,String streaming,  EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class, idPreRevision);
		Recepcion recepcion = em.find(Recepcion.class, preRevision.getFkRecepcion());
		StringTokenizer flujo = new StringTokenizer(streaming,"%");
		String strRechazos = flujo.nextToken();					
		String strOtros =flujo.nextToken();		
		flujo = new StringTokenizer(strRechazos,";");
		String hayRechazo = flujo.nextToken();		
		if( preRevision.getCategoria()==4) {
			preRevision.setLimpio(new Boolean(flujo.nextToken().trim()));
			preRevision.setMismoColor(new Boolean(flujo.nextToken().trim()));			
			preRevision.setSeguridad(new Boolean(flujo.nextToken().trim()));
			preRevision.setLucesEncendida(new Boolean(flujo.nextToken().trim()));
			preRevision.setMirillFreno(new Boolean(flujo.nextToken().trim()));
			preRevision.setTapaCubos(new Boolean("false"));
			preRevision.setVacio(new Boolean("false"));
			preRevision.setPoseeCertificadoGas(new Boolean("false"));			
			
			if(preRevision.getLimpio()==true) {
				preRevision.setObservacion("SE ENCONTRO NO LIMPIO;");
			}
			if(preRevision.getMismoColor()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" NO COINCIDE EL COLOR;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO NO COINCIDE EL COLOR;");
				}			  			
			}
			if(preRevision.getSeguridad()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" DISPOSITIVO DE SEGURIDAD SIN DESACTIVAR;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO DISPOSITIVO DE SEGURIDAD SIN DESACTIVAR;");
				}			  			
			}
			if(preRevision.getLucesEncendida()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" LUCES MOTOCICLETA NO ENCIENDEN;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO LUCES MOTOCICLETA NO ENCIENDEN;");
				}			  			
			}
			if(preRevision.getMirillFreno()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" NO ES VISIBLE MIRILLA DE FRENOS DE LA MOTOCICLETA ;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO NO ES VISIBLE MIRILLA DE FRENOS DE LA MOTOCICLETA ;");
				}			  			
			}
		}else {			
			preRevision.setLimpio(new Boolean(flujo.nextToken().trim()));
			preRevision.setMismoColor(new Boolean(flujo.nextToken().trim()));
			preRevision.setVacio(new Boolean(flujo.nextToken().trim()));
			preRevision.setSeguridad(new Boolean(flujo.nextToken().trim()));
			preRevision.setTapaCubos(new Boolean(flujo.nextToken().trim()));	
			preRevision.setPoseeCertificadoGas(new Boolean(flujo.nextToken().trim()));			
			preRevision.setBaulAccesible(new Boolean(flujo.nextToken().trim()));			
			preRevision.setBateriaAccesible((new Boolean(flujo.nextToken().trim())));			
			preRevision.setFiltroAire((new Boolean(flujo.nextToken().trim())));		
			
			preRevision.setLucesEncendida(new Boolean("false"));						
			if(preRevision.getLimpio()==true) {
				preRevision.setObservacion("SE ENCONTRO NO LIMPIO;");
			}
			if(preRevision.getMismoColor()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" NO COINCIDE EL COLOR;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO NO COINCIDE EL COLOR;");
				}			  			
			}
			if(preRevision.getVacio()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" NO VACIO;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO NO VACIO;");
				}			  			
			}		
			if(preRevision.getSeguridad()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" DISPOSITIVO DE SEGURIDAD SIN DESACTIVAR;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO DISPOSITIVO DE SEGURIDAD SIN DESACTIVAR;");
				}			  			
			}
			if(preRevision.getPoseeCertificadoGas()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" SIN CERTIFICADO SEGURIDAD;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO SIN CERTIFICADO SEGURIDAD;");
				}			  			
			}			
			if(preRevision.getTapaCubos()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" NO SE PUDO QUITAR TAPACUBOS;"));
				}else {
					preRevision.setObservacion("SE ENCONTRO NO SE PUDO QUITAR TAPACUBOS;");
				}			  			
			}
			
			if(preRevision.getBaulAccesible()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" BAUL INACCESIBLE"));
				}else {
					preRevision.setObservacion("SE ENCONTRO BAUL INACCESIBLE;");
				}			  			
			}
			if(preRevision.getBateriaAccesible()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" BATERIA INACCESIBLE"));
				}else {
					preRevision.setObservacion("SE ENCONTRO BATERIA INACCESIBLE;");
				}			  			
			}
			if(preRevision.getFiltroAire()==true) {
				if(preRevision.getObservacion()!=null) {
					preRevision.setObservacion(preRevision.getObservacion().concat(" FILTRO AIRE INACCESIBLE"));
				}else {
					preRevision.setObservacion("SE ENCONTRO FILTRO AIRE INACCESIBLE;");
				}			  			
			}
		}
		flujo = new StringTokenizer(strOtros,";");
		preRevision.setUsuario(Integer.parseInt(flujo.nextToken()));
		Date fechaInic = new Date(Long.parseLong(flujo.nextToken()));		  
		preRevision.setFechaRevision(fechaInic);		
		Calendar fec = Calendar.getInstance();		
		if(hayRechazo.equalsIgnoreCase("1")) {
			flujo = new StringTokenizer(strRechazos,";");
			int lng = flujo.countTokens();
			String txt ;
			for(int i =0; i < lng; i++) {
				txt=flujo.nextToken();
				if(txt.trim().equalsIgnoreCase("true")) {
					RegistroRechazo rR = new RegistroRechazo();
					rR.setPlaca(preRevision.getPlaca());
					rR.setUsuario(preRevision.getUsuario());
					rR.setFechaRegistro(fec.getTime());
					rR.setTypeHandler(i);					
					rR.setDescripcionRechazo(preRevision.getObservacion());
					rR.setFkPreRevision(preRevision.getNoRevision());
					em.persist(rR);
				}
			}
			preRevision.setPreparado(new Boolean(false));	        
			preRevision.setCulminado(10);
			em.merge(preRevision);
			return ("1");  		
		}else {
			preRevision.setPreparado(new Boolean(true));
		}		
		preRevision.setCulminado(0);
		em.merge(preRevision);
		em.merge(recepcion);
		return preRevision.getNoTurno().toString().concat(";").concat(";").concat(hayRechazo);
	}
	@Override
	public String servRegPSIPreRevision(Integer idPreRevision,String streaming,  EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class, idPreRevision);
		Recepcion recepcion = em.find(Recepcion.class, preRevision .getFkRecepcion());
		StringTokenizer flujo = new StringTokenizer(streaming,";");
		Integer nroEjes = Integer.parseInt(flujo.nextToken().trim());
		recepcion.setNroEjes(String.valueOf(nroEjes));
	    preRevision.setNoLLanta(flujo.nextToken());	    
	    recepcion.setPoseePacha(new Boolean(flujo.nextToken()));
	    recepcion.setEsScooter(new Boolean(flujo.nextToken()));	    
	    preRevision.setLlantaRepuestoVisible(new Boolean(flujo.nextToken()));
	    preRevision.setPsiRepuesto(new Float(flujo.nextToken()));        
	    preRevision.setPsiEj1Der(new Float(flujo.nextToken()));	    
	    preRevision.setPsiEj1Izq(new Float(flujo.nextToken()));	    
	    preRevision.setPsiEj2Der(new Float(flujo.nextToken()));
	    preRevision.setPsiEj2DerInt(new Float(flujo.nextToken()));
	    preRevision.setPsiEj2Izq(new Float (flujo.nextToken()));	    
	    preRevision.setPsiEj2IzqInt(new Float (flujo.nextToken()));
	    
	    if(nroEjes>2) {
	       preRevision.setPsiEj3Der(new Float(flujo.nextToken()));
	       preRevision.setPsiEj3DerInt(new Float(flujo.nextToken()));
	       preRevision.setPsiEj3Izq(new Float(flujo.nextToken()));		   
		   preRevision.setPsiEj3IzqInt(new Float (flujo.nextToken()));
	    }
	    if(nroEjes>3) {
	    	preRevision.setPsiEj4Der(new Float(flujo.nextToken()));
	    	preRevision.setPsiEj4DerInt(new Float(flujo.nextToken()));
		    preRevision.setPsiEj4Izq(new Float(flujo.nextToken()));		    
		    preRevision.setPsiEj4IzqInt(new Float (flujo.nextToken()));	   
	    }
	    
	    if(nroEjes>4) {
	    	preRevision.setPsiEj5Der(new Float(flujo.nextToken()));
	    	preRevision.setPsiEj5DerInt(new Float(flujo.nextToken()));
	    	preRevision.setPsiEj5Izq(new Float(flujo.nextToken()));	    	
		    preRevision.setPsiEj5IzqInt(new Float (flujo.nextToken()));		   
	    }
	    
	    
	    preRevision.setCulminado(1);
	    em.merge(recepcion);
	    em.merge(preRevision);
	    return "1";
	}
	@Override
	public String servRegistroInventario(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming,"%");
		String strInventario = flujo.nextToken();
		String pkPreRevision = flujo.nextToken();			
		PreRevision preRevision = em.find(PreRevision.class, Integer.parseInt(pkPreRevision));
		StringTokenizer flujoInv = new StringTokenizer(strInventario,";");
		preRevision.setPoseeRadio(new Boolean(flujoInv.nextToken()));
		preRevision.setPoseeEncendedor(new Boolean(flujoInv.nextToken()));
		preRevision.setPoseeAntena(new Boolean(flujoInv.nextToken()));
		preRevision.setOtrosElementosDeclarados(flujoInv.nextToken());
		preRevision.setCulminado(3);
		em.merge(preRevision);		
		return "1";		
	}
	
	@Override
	public String servPersistPreRevision2Fase(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming,"%");
		String strPreIngreso = flujo.nextToken();
		String strLegalizacion = flujo.nextToken();		
		Integer idPreRevision = Integer.parseInt(flujo.nextToken());		
		PreRevision preRevision = em.find(PreRevision.class, idPreRevision);
		flujo = new StringTokenizer(strPreIngreso,";");		
		preRevision.setKilometraje(new Integer(flujo.nextToken()));
		Recepcion recepcion = em.find(Recepcion.class, preRevision.getFkRecepcion());		
		recepcion.setPesoBruto(flujo.nextToken());		
		preRevision.setNoExosto(new Integer(flujo.nextToken()));
		/*preRevision.setNoEmpresa(new Integer(flujo.nextToken()));
		preRevision.setNoGestor(new Integer(flujo.nextToken()));*/
		recepcion.setNroSillas(flujo.nextToken().trim());
		String nroCert = "";
		if(preRevision.getTipoVehiculo()!=4) {
			preRevision.setEmpresaCertificado(flujo.nextToken().trim());
			nroCert = flujo.nextToken().trim();
			preRevision.setNroCertificado(nroCert);
		}		
		String idVeh =flujo.nextToken();
		if(!idVeh.equalsIgnoreCase("na")) {
			preRevision.setTipoVehiculo(new Integer(idVeh));	
		}
		
		preRevision.setTiemposMotor(new Integer(flujo.nextToken()));
		
		StringTokenizer flujoLeg = new StringTokenizer(strLegalizacion,";");
		preRevision.setBlindado(new Boolean(flujoLeg.nextToken()));
		preRevision.setVidriosPolarizados(new Boolean(flujoLeg.nextToken()));
		preRevision.setEsEnsenanza(new Boolean(flujoLeg.nextToken()));
			
		
		if (preRevision.getTipoVehiculo() != 4) {
			preRevision.setEsAmbulancia(new Boolean(flujoLeg.nextToken()));
			preRevision.setEsBombero(new Boolean(flujoLeg.nextToken()));						
			recepcion.setCombustible(flujoLeg.nextToken());
			if (nroCert.length() > 1) {
				Calendar fec = Calendar.getInstance();
				String strFecha =flujoLeg.nextToken();
				String[] parseoFecha = strFecha.split("-");
				fec.set(Calendar.YEAR, Integer.parseInt(parseoFecha[0].trim()));
				fec.set(Calendar.MONTH, (Integer.parseInt(parseoFecha[1].trim())) - 1);
				fec.set(Calendar.DATE, Integer.parseInt(parseoFecha[2].trim()));
				preRevision.setFechaVencCertificado(fec.getTime());
			}
		}
		preRevision.setCulminado(3);
		em.merge(preRevision);
		em.merge(recepcion);
		return "1";		
	}

	@Override
	public String servPersistFirmaRechazo(InputStream firma, String noRevision, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class, Integer.parseInt(noRevision));
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
	    try {
	    	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		    byte[] bytesFirma =  new byte[firma.available()];
		    int nRead;
		    while ((nRead = firma.read(bytesFirma, 0, bytesFirma.length)) != -1) {
		    	  buffer.write(bytesFirma, 0, nRead);
		    }   
	    	buffer.flush();
	    	Calendar fec = Calendar.getInstance();
	        preRevision.setFirmaRevision(buffer.toByteArray());
	        preRevision.setFechaFinProceso(fec.getTime());
	        queryDao  = em.createNamedQuery("PreRevision.findFollowingTurno");
			queryDao.setParameter("fecha",fec.getTime());
			queryDao.setParameter("ctxCategoria",preRevision.getCategoria());				
			Integer cnt =(Integer) queryDao.getSingleResult();
			if(cnt!=null) {
			   preRevision.setNoTurno(cnt+1);
			}else {
				preRevision.setNoTurno(1);
			}
	        preRevision.setCulminado(6);
	          em.merge(preRevision);			
			} catch (IOException e) {			
				e.printStackTrace();
			}	
	      		
		 return "1"; 
    }

	@Override
	public byte[] restoredFirmaRechazo(String noRevision, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class, Integer.parseInt(noRevision));
		return preRevision.getFirmaRevision();		
	}
	@Override
	public String servFindDatosRecepcion(Integer fkRevision, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,fkRevision);		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
		if(preRevision.getPrimeraVez() == 1) {
			return recepcion.getPlaca().concat(";").concat(recepcion.getMarca()).concat(";").concat(recepcion.getLinea()).concat(";").concat(recepcion.getModelo()).concat(";").concat(recepcion.getCilindrada()).concat(";").concat(recepcion.getColor()).concat(";").concat(recepcion.getServicio()).concat(";").concat(recepcion.getClaseVehiculo()).concat(";").concat(recepcion.getCarroceria()).concat(";").concat(recepcion.getCombustible()).concat(";").concat(recepcion.getCapacidaCarga()).concat(";").concat(recepcion.getNroMotor()).concat(";").concat(recepcion.getNroVin()).concat(";").concat(recepcion.getNroSerie()).concat(";").concat(recepcion.getNroChasis()).concat(";").concat(recepcion.getNroLicencia().concat(";").concat(recepcion.getApellidos())).concat(";").concat(recepcion.getNombres()).concat(";").concat(recepcion.getCedula()).concat(";").concat(String.valueOf(recepcion.getFechaMatricula().getTime())).concat(";").concat(recepcion.getNroEjes()).concat(";").concat(preRevision.getNacionalidad()).concat(";").concat(recepcion.getCiudadaniaConduc());	
		}else {
			return recepcion.getPlaca();
		}
	}
	@Override
	public String servConfirmDatosRunt(String atrrConfirmacion, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(atrrConfirmacion, ";");
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(flujo.nextToken()));	
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion() );
		recepcion.setCombustible(flujo.nextToken());
		recepcion.setNroMotor(flujo.nextToken());
		recepcion.setNroSerie(flujo.nextToken());
		recepcion.setNroChasis(flujo.nextToken());		
		recepcion.setCilindrada(flujo.nextToken());
		recepcion.setCarroceria(flujo.nextToken());
		recepcion.setCapacidaCarga(flujo.nextToken());		
		recepcion.setNroVin(flujo.nextToken());		
		recepcion.setNroLicencia(flujo.nextToken());		
		recepcion.setMarca(flujo.nextToken());
		recepcion.setLinea(flujo.nextToken());		
		recepcion.setModelo(flujo.nextToken());
		recepcion.setColor(flujo.nextToken());		
		recepcion.setServicio(flujo.nextToken());		
		recepcion.setApellidos(flujo.nextToken().toUpperCase());
		recepcion.setNombres(flujo.nextToken().toUpperCase());
		recepcion.setCedula(flujo.nextToken());		
		Calendar fec = Calendar.getInstance();		
		String[] parseoFecha=  flujo.nextToken().split("-");		 
		fec.set(Calendar.YEAR,Integer.parseInt(parseoFecha[0].trim()));		
		fec.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fec.set(Calendar.DATE,Integer.parseInt(parseoFecha[2].trim()));						
		recepcion.setFechaMatricula(fec.getTime());		
		em.merge(recepcion);
		return "1";
	}
	
	@Override
	public String servFindCtxPreRevision(String ctxFuncion,Integer fkRevision, EntityManager em) {
		String responseServicio;
		PreRevision preRevision = em.find(PreRevision.class,fkRevision);
		
		if(ctxFuncion.equalsIgnoreCase("medidasPSI")) {
			if(preRevision.getPrimeraVez()==1) {
				return "noAplica";
			}else {
				Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
				responseServicio=";".concat(preRevision.getNoLLanta()).concat(";").concat(recepcion.getNroEjes()).concat(";").concat(recepcion.getEsScooter().toString()).concat(";").concat(recepcion.getPoseePacha().toString());
			}
		}else {
			if(preRevision.getPrimeraVez()==1) {
				return "noAplica";
			}else {
				Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());
				responseServicio=";".concat("R;").concat(String.valueOf(preRevision.getKilometraje())).concat(";").concat(recepcion.getPesoBruto()).concat(";").concat(preRevision.getNoExosto().toString()).concat(";").concat(preRevision.getTipoVehiculo().toString()).concat(";").concat(preRevision.getTiemposMotor().toString()).concat(";").concat(recepcion.getCombustible()).concat(";").concat(preRevision.getNroCertificado()).concat(";").concat(preRevision.getEmpresaCertificado()).concat(";").concat(String.valueOf(preRevision.getFechaVencCertificado().getTime())).concat(";").concat(String.valueOf(preRevision.getVidriosPolarizados())).concat(";").concat(String.valueOf(preRevision.getBlindado())).concat(";").concat(String.valueOf(preRevision.getEsEnsenanza())).concat(";").concat(String.valueOf(preRevision.getEsAmbulancia())).concat(";").concat(String.valueOf(preRevision.getEsBombero())).concat(";").concat(recepcion.getNroSillas());
				
			}			
		}		
		return responseServicio;
		
	}
	
	@Override
	public String servUltimaPreRevision(String placa, EntityManager em) {
		queryDao  = em.createNamedQuery("PreRevision.findMaxPreRevPlaca");		
		queryDao.setParameter("placa",placa);
		Integer idPreRev =(Integer) queryDao.getSingleResult();
		if(idPreRev !=null) {
		   PreRevision preRevision = em.find(PreRevision.class, idPreRev);
		   return preRevision.getNoRevision().toString().concat(";").concat(preRevision.getUsuario().toString());		
		}else {
			return "0";
		}		
	}
	@Override
	public String setInformeRecPista(Date fechaInicial, EntityManager em) {
		
		queryDao  = em.createNamedQuery("PreRevision.findCountOrdenPrep");		
		queryDao.setParameter("fecha",fechaInicial);		
		List<Long> lstScalar = queryDao.getResultList();
		Long tot =  lstScalar.get(0); 
		Calendar fechaIni = Calendar.getInstance();
		fechaIni.setTime(fechaInicial);
		fechaIni.set(Calendar.HOUR,5 );
		fechaIni.set(Calendar.HOUR_OF_DAY,5);	
		fechaIni.set(Calendar.MINUTE,10 );	
		Calendar fechaFin = Calendar.getInstance();		
		fechaFin.setTime(fechaInicial);
		fechaFin.set(Calendar.HOUR,23 );
		fechaFin.set(Calendar.HOUR_OF_DAY,23);	
		fechaFin.set(Calendar.MINUTE,10 );				
		queryDao  = em.createNamedQuery("RegistroRechazo.FindByDate");		
		queryDao.setParameter("fechaRegistroIni",fechaIni.getTime());
		queryDao.setParameter("fechaRegistroFin",fechaFin.getTime());
		List<RegistroRechazo> lstRegistroRechazo = queryDao.getResultList();
		int contSoat=0;		
		int contNoLimpio=0;
		int contNoColor=0;
		int contNoVacio=0;
		int contNoSeguridad=0;
		int contNoExistTapa=0;
		int contNoPin=0;
		int contNoConAtc=0;
		int contNoPresCertGas=0;
		int contVencRTM=0;
		int contGen=0;		
		if(lstRegistroRechazo.size()>0 ) {			
			for (RegistroRechazo rechazo : lstRegistroRechazo) {
				contGen=contGen+1;
				if(rechazo.getTypeHandler()==0 ) {
					contSoat=contSoat+1;					
				}
				if(rechazo.getTypeHandler()==1) {
					contNoLimpio=contNoLimpio+1;
				}
				if(rechazo.getTypeHandler()==2) {
					contNoColor=contNoColor+1;
				}
				if(rechazo.getTypeHandler()==3) {
					contNoVacio=contNoVacio+1;
				}
				if(rechazo.getTypeHandler()==4) {
					contNoSeguridad=contNoSeguridad+1;
				}
				if(rechazo.getTypeHandler()==5) {
					contNoExistTapa=contNoExistTapa+1;
				}
				if(rechazo.getTypeHandler()==8) {
					contNoConAtc=contNoConAtc+1;
				}
				if(rechazo.getTypeHandler()==6) {
					contNoPresCertGas=contNoPresCertGas+1;
				}				
				if(rechazo.getTypeHandler()==10) {
					contVencRTM=contVencRTM+1;
				}
				if(rechazo.getTypeHandler()==7) {
				   contNoPin=contNoPin+1;
				}
			}			
		   return String.valueOf(tot).concat(";").concat(String.valueOf(contGen).concat(";")).concat(String.valueOf(contSoat).concat(";")).concat(String.valueOf(contNoLimpio).concat(";")).concat(String.valueOf(contNoColor).concat(";")).concat(String.valueOf(contNoVacio).concat(";")).concat(String.valueOf(contNoSeguridad).concat(";")).concat(String.valueOf(contNoExistTapa).concat(";")).concat(String.valueOf(contNoConAtc).concat(";")).concat(String.valueOf(contVencRTM).concat(";")).concat(String.valueOf(contNoPin)).concat(";").concat(String.valueOf(contNoPresCertGas));		
		}else {
			return "0";
		}		
	}
	
	@Override
	public String servPersistNovedad(InputStream evidencia, String noRevision, String obsPerpectiva, Integer handler, Integer contexto, EntityManager em) {
		RegNovedades regNovedades = new RegNovedades();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] bytesEvidencia;
		try {
			bytesEvidencia = new byte[evidencia.available()];		
		    regNovedades.setHandler(handler);
		    regNovedades.setObsPerpectiva(obsPerpectiva);
		    regNovedades.setFkPreRevision(Integer.parseInt(noRevision));
		    regNovedades.setContexto(contexto);		 
	        int nRead;
	        while ((nRead = evidencia.read(bytesEvidencia , 0, bytesEvidencia.length)) != -1) {
	    	   buffer.write(bytesEvidencia , 0, nRead);
	        }
    	    buffer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    	regNovedades.setRegEvidencia(buffer.toByteArray());		
		em.persist(regNovedades);
		return regNovedades.getNoNovedad().toString();
	}

	@Override
	public List<byte[]> servFindEvidencias(Integer fkPreRevision, EntityManager em) {
		queryDao  = em.createNamedQuery("RegNovedades.findLstEvidencias");
		queryDao.setParameter("fkPreRevision",fkPreRevision);
		List<byte[]> lstEvidencias =queryDao.getResultList();
		return lstEvidencias ;
	}

	@Override
	public String servAutorizacionPreRevision(String streaming, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(flujo.nextToken()));		
		Recepcion recepcion = em.find(Recepcion.class, preRevision.getFkRecepcion());
		recepcion.setCedulaConduc(flujo.nextToken());
		recepcion.setNombresConduc(flujo.nextToken().toUpperCase());		
		recepcion.setApellidosConduc(flujo.nextToken().toUpperCase());				
		recepcion.setUsuario(Integer.parseInt(flujo.nextToken().trim()));
		recepcion.setDireccionConduc(flujo.nextToken().trim());
		recepcion.setTelefono(flujo.nextToken().trim());
		recepcion.setCorreo(flujo.nextToken().trim());		
		preRevision.setCulminado(4);
		em.merge(recepcion);		
		em.merge(preRevision);
		return recepcion.getCedulaConduc().concat(";").concat(recepcion.getNombresConduc()).concat(";").concat(recepcion.getApellidosConduc());
	}

	@Override
	public Integer servAutorizacionVehPista(String fkRevision,String nroFactura,String nroPin, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(fkRevision));		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());		
		recepcion.setNroFactura(nroFactura);
		recepcion.setNroPin(nroPin);
		Calendar fec = Calendar.getInstance();
		recepcion.setFechaFinProceso(fec.getTime());
		preRevision.setCulminado(7); 
		em.merge(recepcion);		
		return preRevision.getNoRevision() ;
	}

	@Override
	public String servFindDatosSoat(Integer fkRevision, EntityManager em) {
		PreRevision preRevision = em.find(PreRevision.class,fkRevision);		
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion());				
		return recepcion.getPlaca().concat(";").concat(recepcion.getNroPolizaSoat()).concat(";").concat(String.valueOf(recepcion.getFechaExpedicionSoat().getTime())).concat(";").concat(String.valueOf(recepcion.getFechaInicExpedicionSoat().getTime())).concat(";").concat(String.valueOf(recepcion.getFechaFinExpedicionSoat().getTime())).concat(";").concat(recepcion.getEntidadSoat().concat(";").concat(String.valueOf(fkRevision))).concat(";").concat(recepcion.getServicio()).concat(";").concat(preRevision.getNacionalidad());
	}

	@Override
	public String servConfirmDatosSoat(String atrrConfirmacion, EntityManager em) {
		StringTokenizer flujo = new StringTokenizer(atrrConfirmacion, ";");
		PreRevision preRevision = em.find(PreRevision.class,Integer.parseInt(flujo.nextToken()));	
		Recepcion recepcion = em.find(Recepcion.class,preRevision.getFkRecepcion() );
		recepcion.setNroPolizaSoat(flujo.nextToken());
		Calendar fec = Calendar.getInstance();		
		String[] parseoFecha=  flujo.nextToken().split("-");		 
		fec.set(Calendar.YEAR,Integer.parseInt(parseoFecha[0].trim()));		
		fec.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fec.set(Calendar.DATE,Integer.parseInt(parseoFecha[2].trim()));						
		recepcion.setFechaExpedicionSoat(fec.getTime());
		parseoFecha=  flujo.nextToken().split("-");
		fec.set(Calendar.YEAR,Integer.parseInt(parseoFecha[0].trim()));
		fec.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fec.set(Calendar.DATE,Integer.parseInt(parseoFecha[2].trim()));						
		recepcion.setFechaInicExpedicionSoat(fec.getTime());
		parseoFecha=  flujo.nextToken().split("-");
		fec.set(Calendar.YEAR,Integer.parseInt(parseoFecha[0].trim()));		
		fec.set(Calendar.MONTH,(Integer.parseInt(parseoFecha[1].trim()))-1);
		fec.set(Calendar.DATE,Integer.parseInt(parseoFecha[2].trim()));				
		recepcion.setFechaFinExpedicionSoat(fec.getTime());		
		recepcion.setEntidadSoat(flujo.nextToken());			
		em.merge(recepcion);
		return "1";
	}

	
	
}
