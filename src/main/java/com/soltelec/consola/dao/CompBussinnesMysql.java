package com.soltelec.consola.dao;


import javax.persistence.EntityManager;
import java.io.InputStream;
import java.util.Date;


public interface CompBussinnesMysql {	
	public String servBuiltGrafoHP(Integer fkPreRevision, EntityManager emMysql, EntityManager em);
	public String servFindClasesVehiculoAct(EntityManager emMysql);
	public String servFindCtxCDA(EntityManager emMysql);
	public String servFindExisRevision(String placa, EntityManager emMysql, EntityManager em);
	public String servFindPropietarioExistente(String cedula, EntityManager emMysql);
	public String servFindFuncConsola(String placa, EntityManager em);
	public String servFuncCmbEdoSicov(String strAtributos, EntityManager emMysql);
	public String servFuncTipoRevision(String strAtributos, EntityManager emMysql);
	public String servFuncCambioPlaca(String strAtributos, EntityManager emMysql);
	public String servFuncAnularRTM(String strAtributos, EntityManager emMysql);
	public String servFuncCorregirPin(String strAtributos, EntityManager emMysql);
	public String servFindHojaPruebasAbiertas(Integer ctxPista, String ctxRevision, Integer tipoRevision, EntityManager emMysql, EntityManager em);
	public String servFindStatusPruebas(String testSheet, EntityManager emMysql, EntityManager em);
	public String servPersisFotos(InputStream pictureFoto, Integer identPrueba, Integer identHojaPrueba, Integer numeroFoto, Integer fkUser, EntityManager emMysql);
	public String servFindDatosRuntInMysql(String placa, EntityManager emMysql);
	public String servEstadisticaHojaPruebaFor1Vez(String tipoRevision, Date fechaInicial, Date fechaFinal, EntityManager emMysql);
	public String servEstadisticaMovRTM(String tipoRevision, Date fechaInicial, Date fechaFinal, EntityManager emMysql);
	public String servEstadisticaAnalisisPruebas(Integer tipoPrueba, Date fechaInicial, Date fechaFinal, EntityManager emMysql);
	public String servViewDetallesMedidasbyTipoPruebas(Integer tipoPrueba, Date fechaInicial, Date fechaFinal, EntityManager emMysql);
	public String servInformeRevision(Date fechaInicial, Date fechaFinal, EntityManager emMysql);
	public String setPanelConfiguracionCda(String strAtributos, EntityManager emMysql);
	public String servFindDepartametos(EntityManager emMysql);
	public String servFindCiudades(Integer identCiudad, EntityManager emMysql);
	public String serPersitProfLabrado(String strProfundidaLabrado, EntityManager emMysql);
	public String servCtxShowDefectos(Integer ctxPruebaSensorial, Integer ctxTipoVehiculo, Integer ctxPartVehiculo, Integer ctxTipoMiga, EntityManager emMysql);
	public String servSetDefectoPrueba(String ctxFuncion, Integer ctxIdPrueba, Integer ctxDefecto, EntityManager emMysql);
	public String servSetCierrePruebaSesonrial(Integer ctxIdPrueba, Integer ctxUsuario, EntityManager emMysql, EntityManager em);
	public String servFindEstadoPruebas(Integer ctxIdPrueba, EntityManager emMysql);
	public String servSetObservacionesPruebas(Integer ctxIdPrueba, String observaciones, EntityManager emMysql);
	public String serPersitProfLabradoLiviano(String strProfundidaLabrado, EntityManager emMysql);
	public String servFindCtxFotos(Integer ctxHojaPrueba, EntityManager emMysql);
	public String BusqColoresExist(String strColores, EntityManager emMysql);
	public String busqAseguradoraExist(String strAseguradora, EntityManager emMysql);
	public String busqMarcaExist(String strMarca, EntityManager emMysql);
	public String servFindOrdenBienvenida(EntityManager emMysql);
	
	
}
