package com.soltelec.consola.dao;

import javax.persistence.EntityManager;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface ProcStreamingRecepcionDao {
	public String procStreamingDocPropiedad(String streaming, EntityManager em);
	public String procStreamingDocCedula(String streaming, EntityManager em);
	public String servPersistPaso1Recepcion(String streaming, EntityManager em);
	public String servPersistRegistroCedula(String streaming, EntityManager em);
	public String servRegManualRunt(String streaming, EntityManager em);
	public String servAutorizacionPreRevision(String streaming, EntityManager em);
	public String servRegAutomatizadoRunt(String streaming, EntityManager em);
	public String servInspeccionPreRevision(Integer idPreRevision, String streaming, EntityManager em) ;
	public String servPersistPreRevision2Fase(String streaming, EntityManager em);
	public String servPersistRegistroRechazo(String placa, String descRechazo, Integer typeHandler, String observacion, Integer usuario, EntityManager em);
	public String servPersistFirmaRechazo(InputStream firma, String noRevision, EntityManager em);
	public String servPersistNovedad(InputStream evidencia, String noRevision, String obsPerpectiva, Integer handler, Integer contexto, EntityManager em);
	public byte[] restoredFirmaRechazo(String noRevision, EntityManager em);
	public String servUltimaPreRevision(String placa, EntityManager em);
	public List<byte[]> servFindEvidencias(Integer fkPreRevision, EntityManager em);
	public String servFindOrdenPreRevision(String streaming, EntityManager em);
	public String servRegPSIPreRevision(Integer idPreRevision, String streaming, EntityManager em);
	public String servFindDatosRecepcion(Integer fkRevision, EntityManager em);
	public String servConfirmDatosRunt(String atrrConfirmacion, EntityManager em);
	public String servConfirmDatosSoat(String atrrConfirmacion, EntityManager em);
	public String servFindDatosConductor(String streaming, EntityManager em);
	public String servActualizarDatosConductor(String streaming, EntityManager em);
	public Integer servAutorizacionVehPista(String fkRevision, String nroFactura, String nroPin, EntityManager em);
	public String setFechaInicioRecepcion(Integer fkRevision, Integer usuario, EntityManager em);
	public String servFindDatosSoat(Integer fkRevision, EntityManager em);
	public String servFindDatosPropietario(String streaming, EntityManager em);
	public String setInformeRecPista(Date fechaInicial, EntityManager em);
	public String setAnulacionPreRevision(Integer idPreRevision, EntityManager em);
	public String servFindDatosUbicConductor(String streaming, EntityManager em);
	public String servFindCtxPreRevision(String ctxFuncion, Integer fkRevision, EntityManager em);
	public String servRegistroInventario(String streaming, EntityManager em);
	
}
