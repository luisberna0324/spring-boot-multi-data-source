package com.soltelec.consola.facade;

import com.soltelec.consola.dao.CompBussinnesMysql;
import com.soltelec.consola.dao.FactoryDaoImpl;
import com.soltelec.consola.dao.ProcStreamingRecepcionDao;
import com.soltelec.consola.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class ServiciosBasicoFacade implements basicoFacade {

	@Autowired
	FactoryDaoImpl factoryDao ;
	String respImplDao;	
	private Query queryDao;
	
	
	@Override
	public String valAutUserDominio(String user, String password)  {
		UsuarioDao userDao = factoryDao.createUsuarioDao();
		try {					
		    respImplDao= userDao.validUserPasswDominio(user,password,factoryDao.getEntityManager());			
		}catch(NoResultException ex) {
			respImplDao="no;";
		}
		return respImplDao;
	}
	@Override
	public String procStreamingDocPropiedad(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		respImplDao=procStreamingRecepcionDao.procStreamingDocPropiedad(streaming,factoryDao.getEntityManager());
		return respImplDao;
	}
	
	
	
	
	@Override
	public String servPersistRegistroCedula(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();	
		respImplDao = procStreamingRecepcionDao.servPersistPaso1Recepcion(streaming,factoryDao.getEntityManager());
		return respImplDao;
	}
	@Override
	public String procStreamingDocCedula(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();	    
		respImplDao=procStreamingRecepcionDao.procStreamingDocPropiedad(streaming,factoryDao.getEntityManager());
		return respImplDao;		
	}
	@Override
	public String servPersistRechazoVehiculo(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();	
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		return procStreamingRecepcionDao.servPersistRegistroRechazo(flujo.nextToken(),flujo.nextToken(),Integer.parseInt(flujo.nextToken()),flujo.nextToken(),Integer.parseInt(flujo.nextToken()),factoryDao.getEntityManager());
	}
	
	@Override
	public String servFindCtxPreRevision(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		StringTokenizer flujo = new StringTokenizer(streaming, ";");
		String pelo =procStreamingRecepcionDao.servFindCtxPreRevision(flujo.nextToken(),Integer.parseInt(flujo.nextToken()),factoryDao.getEntityManager());
        int i =0;
        i++;
        int e=i;
		return pelo;		
	}
	
	@Override
	public String servRegManualRunt(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();		
		return procStreamingRecepcionDao.servRegManualRunt(streaming,factoryDao.getEntityManager());
	}
	
	@Override
	public String servRegAutomaticoRunt(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String responseBusinnes= procStreamingRecepcionDao.servRegAutomatizadoRunt(streaming,factoryDao.getEntityManager());
		responseBusinnes = responseBusinnes;
		return responseBusinnes;		
	}
	
	@Override
	public String servInspeccionPreRevision(String streaming,String idPreRevision) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();		
		return procStreamingRecepcionDao.servInspeccionPreRevision(Integer.parseInt(idPreRevision), streaming,factoryDao.getEntityManager());		
	}
	
		
	@Override
	public String servPersistPreRevision2Fase(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();		
		return procStreamingRecepcionDao.servPersistPreRevision2Fase(streaming, factoryDao.getEntityManager());	
	}
	
	@Override
	public String servRegistroInventario(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();		
		return procStreamingRecepcionDao.servRegistroInventario(streaming, factoryDao.getEntityManager());	
	}
	
	@Override
	public String servPersistFirmaRechazo(InputStream firma, String noRevision) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();		
		return procStreamingRecepcionDao.servPersistFirmaRechazo(firma, noRevision,factoryDao.getEntityManager());
	}
	@Override
	public byte[] restoredFirmaRechazo(String noRevision) {
	   ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
	   return procStreamingRecepcionDao.restoredFirmaRechazo(noRevision, factoryDao.getEntityManager());		
		
	}
	@Override
	public String servPersistNovedad(InputStream evidencia, String noRevision, String obsPerpectiva, Integer handler,Integer contexto) {
	   ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
	   return procStreamingRecepcionDao.servPersistNovedad(evidencia, noRevision,obsPerpectiva,handler ,contexto,factoryDao.getEntityManager());	
	}
	
	@Override
	public String servPersistFotoPrueba(InputStream pictureFoto, Integer identPrueba, Integer identHojaPrueba,String identCondicion,Integer fkUser) {
		String strDatos;
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
	    strDatos = compBussinnesMysql.servPersisFotos(pictureFoto, identPrueba,identHojaPrueba ,Integer.parseInt(identCondicion),fkUser,factoryDao.getEntityManagerMysql());
	    return strDatos;
	}
	@Override
	public String setAnulacionPreRevision(Integer nroPreRevision) {
	   ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
	   return procStreamingRecepcionDao.setAnulacionPreRevision(nroPreRevision,factoryDao.getEntityManager());	
	}
	
	@Override
	public String servFindOrdenPreRevision(String streaming) {
	   ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
	   return procStreamingRecepcionDao.servFindOrdenPreRevision(streaming,factoryDao.getEntityManager());	
	}
	@Override
	public String servFindDatosRecepcion(String noRevision) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String strDatos;
		strDatos = procStreamingRecepcionDao.servFindDatosRecepcion(Integer.parseInt(noRevision),factoryDao.getEntityManager());
		String[] validEstruc  = strDatos.split(";");
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String response =compBussinnesMysql.servFindDatosRuntInMysql(validEstruc[0],factoryDao.getEntityManagerMysql());
		if(response.equalsIgnoreCase("1")) {
			return 	strDatos;
		}else {
			return response;
		}
	}
	
	@Override
	public String servFindOrdenBienvenida() {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFindOrdenBienvenida(factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servFindVehiculo(String placa) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFindDatosRuntInMysql(placa,factoryDao.getEntityManagerMysql());	
	}
	
	@Override
	public String servCtxShowDefectos(String ctxInpeccSensorial) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String[] validCtx  = ctxInpeccSensorial.split(";");		
		String respPersitProfundidad= compBussinnesMysql.servCtxShowDefectos(Integer.parseInt(validCtx[0]),Integer.parseInt(validCtx[1]),Integer.parseInt(validCtx[2]),Integer.parseInt(validCtx[3]),factoryDao.getEntityManagerMysql());
		return respPersitProfundidad;		
	}
	
	@Override
	public String BusqColoresExist(String strColores) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();				
		String respStrColores= compBussinnesMysql.BusqColoresExist(strColores,factoryDao.getEntityManagerMysql());
		return respStrColores;		
	}
	
	@Override
	public String busqMarcaExist(String strMarca) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();				
		String respStrColores= compBussinnesMysql.busqMarcaExist(strMarca,factoryDao.getEntityManagerMysql());
		return respStrColores;		
	}
	
	@Override
	public String busqAseguradoraExist(String strAseguradora) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();				
		String respStrAseguradora= compBussinnesMysql.busqAseguradoraExist(strAseguradora,factoryDao.getEntityManagerMysql());
		return respStrAseguradora;		
	}
	
	@Override
	public String servFindCtxFotos(String ctxHojaPrueba) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();				
		String respCtxNroFoto= compBussinnesMysql.servFindCtxFotos(Integer.parseInt(ctxHojaPrueba),factoryDao.getEntityManagerMysql());
		return respCtxNroFoto;		
	}
	
	@Override
	public String servSetDefectoPrueba(String strDefectos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String[] validCtx  = strDefectos.split(";");		
		String respPersitProfundidad= compBussinnesMysql.servSetDefectoPrueba(validCtx[0],Integer.parseInt(validCtx[1]),Integer.parseInt(validCtx[2]),factoryDao.getEntityManagerMysql());
		return respPersitProfundidad;		
	}
	
	@Override
	public String servSetCierrePrueba(String strParametroPrueba) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String[] validCtx  = strParametroPrueba.split(";");
		String respPersitProfundidad= compBussinnesMysql.servSetCierrePruebaSesonrial(Integer.parseInt(validCtx[0]),Integer.parseInt(validCtx[1]),factoryDao.getEntityManagerMysql(),factoryDao.getEntityManager());
		return respPersitProfundidad;		
	}	
	
	@Override
	public String servFindEstadoPruebas(String strParametroPrueba) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();		
		String respEstadoPrueba= compBussinnesMysql.servFindEstadoPruebas(Integer.parseInt(strParametroPrueba),factoryDao.getEntityManagerMysql());
		return respEstadoPrueba;		
	}
	
	@Override
	public String servSetObservacionesPruebas(String strParametroPrueba) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();	
		String[] validCtx  = strParametroPrueba.split(";");
		String respEstadoPrueba= compBussinnesMysql.servSetObservacionesPruebas(Integer.parseInt(validCtx[0]),validCtx[1],factoryDao.getEntityManagerMysql());
		return respEstadoPrueba;		
	}
	
	@Override
	public String servFindDatosSoat(String noRevision) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servFindDatosSoat(Integer.parseInt(noRevision),factoryDao.getEntityManager());		 
	}
	
	@Override
	public String serPersitProfLabrado(String strProfundidaLabrado) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String respPersitProfundidad= compBussinnesMysql.serPersitProfLabrado(strProfundidaLabrado,factoryDao.getEntityManagerMysql());
		return respPersitProfundidad;
	}
	
	@Override
	public String serPersitProfLabradoLiviano(String strProfundidaLabrado) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String respPersitProfundidad= compBussinnesMysql.serPersitProfLabradoLiviano(strProfundidaLabrado,factoryDao.getEntityManagerMysql());
		return respPersitProfundidad;
	}
	@Override
	public String servUltimaPreRevision(String placa) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servUltimaPreRevision(placa,factoryDao.getEntityManager());		 
	}
	@Override
	public String getFindVehiculoForPlaca(String placa) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<byte[]> servFindEvidencias(Integer fkPreRevision) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servFindEvidencias(fkPreRevision,factoryDao.getEntityManager());
		
	}
	@Override
	public String servBuiltGrafoHP(Integer fkPreRevision) {		
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String resp= compBussinnesMysql.servBuiltGrafoHP(fkPreRevision, factoryDao.getEntityManagerMysql(),factoryDao.getEntityManager());
		return resp;
		
	}
	@Override
	public String servFindClasesVehiculoAct() {		
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String resp= compBussinnesMysql.servFindClasesVehiculoAct(factoryDao.getEntityManagerMysql());
		return resp;		
	}
	
	@Override
	public String servRegistroUsuario(InputStream firma,String streaming) {
		UsuarioDao userDao = factoryDao.createUsuarioDao();	    
		try {					
		    respImplDao= userDao.servRegistroUsuario(firma,streaming,factoryDao.getEntityManager());			
		}catch(Exception ex) {
			respImplDao="no;";
		}
		return respImplDao;
		
	}
	@Override
	public String servExixtenciaNickUsuario(String strNickUsuario) {
		UsuarioDao userDao = factoryDao.createUsuarioDao();	    
		try {					
		    respImplDao= userDao.servExistenciaNickUsuario(strNickUsuario,factoryDao.getEntityManager());			
		}catch(Exception ex) {
			respImplDao="no;";
		}
		return respImplDao;
	}
	@Override
	public byte[] servFindFirmaUsuario(String strCedula) {
		UsuarioDao userDao = factoryDao.createUsuarioDao();
		byte[] frmUser= null;
		try {					
			frmUser= userDao.servFindFirma(strCedula,factoryDao.getEntityManager());			
		}catch(Exception ex) {
			respImplDao="no;";
		}
		return frmUser;
	}
	@Override
	public String servFindUsuario(String strCedula) {
		UsuarioDao userDao = factoryDao.createUsuarioDao();	    
		try {					
		    respImplDao= userDao.servExistenciaUsuario(strCedula,factoryDao.getEntityManager());			
		}catch(Exception ex) {
			respImplDao="no;";
		}
		return respImplDao;
	}
	@Override
	public String servUpdateUsuario(InputStream firma,String strAtrUser) {
		UsuarioDao userDao = factoryDao.createUsuarioDao();	    
		try {					
		    respImplDao= userDao.servActualizacionUsuario(firma,strAtrUser,factoryDao.getEntityManager());			
		}catch(Exception ex) {
			respImplDao="no;";
		}
		return respImplDao;
	}
	@Override
	public String servAutorizacionPreRevision(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servAutorizacionPreRevision(streaming,factoryDao.getEntityManager());		
	}	
	@Override
	public String servRegistroPSI(String idPreRevision,String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servRegPSIPreRevision(Integer.parseInt(idPreRevision),streaming,factoryDao.getEntityManager());		
	}
	
	@Override
	public String servConfirmDatosRunt(String atrrConfirmacion) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servConfirmDatosRunt(atrrConfirmacion,factoryDao.getEntityManager());
	}
	@Override
	public String servFindFuncConsola(String placa) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFindFuncConsola(placa,factoryDao.getEntityManagerMysql());
	}
	@Override
	public String servFuncCmbEdoSicov(String strAtributos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFuncCmbEdoSicov(strAtributos,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servFuncCambioPlaca(String strAtributos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFuncCambioPlaca(strAtributos,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servEstadisticaHojaPruebaFor1Vez(String tipoRevision,Date fechaInicial,Date fechaFinal) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servEstadisticaHojaPruebaFor1Vez(tipoRevision, fechaInicial, fechaFinal,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servEstadisticaMovRTM(String tipoRevision,Date fechaInicial,Date fechaFinal) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servEstadisticaMovRTM(tipoRevision, fechaInicial, fechaFinal,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servEstadisticaAnalisisPruebas(Integer tipoPrueba,Date fechaInicial,Date fechaFinal) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servEstadisticaAnalisisPruebas(tipoPrueba, fechaInicial, fechaFinal,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servFuncAnularRTM(String strAtributos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFuncAnularRTM(strAtributos,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servFuncCorregirPin(String strAtributos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFuncCorregirPin(strAtributos,factoryDao.getEntityManagerMysql());
	}
	
	@Override
	public String servFuncCmbTipoRevision(String strAtributos) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		return compBussinnesMysql.servFuncTipoRevision(strAtributos,factoryDao.getEntityManagerMysql());
	}
	@Override
	public String servConfirmDatosSoat(String atrrConfirmacion) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servConfirmDatosSoat(atrrConfirmacion,factoryDao.getEntityManager());
	}
	
	@Override
	public String servFindDatosConductor(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servFindDatosConductor(streaming,factoryDao.getEntityManager());
	}
	@Override
	public String servFindDatosPropietario(String streaming) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();		
		return  compBussinnesMysql.servFindPropietarioExistente(streaming,factoryDao.getEntityManagerMysql());		
	}
	
	@Override
	public String servFindDatosUbicacionConductor(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servFindDatosUbicConductor(streaming,factoryDao.getEntityManager());		
		
	}	
	
	@Override
	public String servActualizarDatosConductor(String streaming) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		return procStreamingRecepcionDao.servActualizarDatosConductor(streaming,factoryDao.getEntityManager());
	}
	@Override
	public String servAutorizacionVehPista(String fkRevision,String nroFactura,String nroPin) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		Integer preRevision =procStreamingRecepcionDao.servAutorizacionVehPista(fkRevision,nroFactura,nroPin,factoryDao.getEntityManager());
		servBuiltGrafoHP(preRevision);
        return "1";        
	}
	@Override
	public String setFechaInicioRecepcion(Integer fkRevision,Integer usuario ) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String resp =procStreamingRecepcionDao.setFechaInicioRecepcion(fkRevision, usuario,factoryDao.getEntityManager());
		return resp;
	}
	
	@Override 
	public String setInformeRecPista(Date fechaInicial) {
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String resp =procStreamingRecepcionDao.setInformeRecPista(fechaInicial,factoryDao.getEntityManager());
		return resp;
	}
	
	@Override
	public String servFindExisRevision(String placa) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String ctxRevision= compBussinnesMysql.servFindExisRevision(placa,factoryDao.getEntityManagerMysql(),factoryDao.getEntityManager());
		return ctxRevision;
	}
	
	@Override
	public String servInformeRevision(Date fechaInicial,Date fechaFinal) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String flujoHojasPend= compBussinnesMysql.servInformeRevision(fechaInicial,fechaFinal,factoryDao.getEntityManagerMysql());
		return flujoHojasPend;
	}
	
	@Override
	public String servFindHojaPruebasAbiertas(String streams) {
		StringTokenizer flujo = new StringTokenizer(streams, ";");
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String flujoHojasPend= compBussinnesMysql.servFindHojaPruebasAbiertas(Integer.parseInt(flujo.nextToken()) ,flujo.nextToken(),Integer.parseInt(flujo.nextToken()),factoryDao.getEntityManagerMysql(),factoryDao.getEntityManager());
		return flujoHojasPend;
	}
	@Override
	public String servFindStatusPruebas(String testSheet) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String flujoHojasPend= compBussinnesMysql.servFindStatusPruebas(testSheet,factoryDao.getEntityManagerMysql(),factoryDao.getEntityManager());
		return flujoHojasPend;
	}
	
	
	@Override
	public String setPanelConfiguracionCda(String strAtributosCda) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String flujoHojasPend= compBussinnesMysql.setPanelConfiguracionCda(strAtributosCda,factoryDao.getEntityManagerMysql());
		return flujoHojasPend;
	} 
	@Override
	public String servFindCtxCDA() {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		ProcStreamingRecepcionDao procStreamingRecepcionDao = factoryDao.createProcStreamingRecepcionDao();
		String flujoHojasPend= compBussinnesMysql.servFindCtxCDA(factoryDao.getEntityManagerMysql());
		return flujoHojasPend;
	}
	
	@Override
	public String servFindDepartametos() {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String flujoDepartamentos= compBussinnesMysql.servFindDepartametos(factoryDao.getEntityManagerMysql());
		return flujoDepartamentos;
	}
	@Override
	public String servFindCiudades(Integer identCiudad) {
		CompBussinnesMysql compBussinnesMysql = factoryDao.createCompBussinnesMysqlDao();
		String flujoCiudades= compBussinnesMysql.servFindCiudades(identCiudad,factoryDao.getEntityManagerMysql());
		return flujoCiudades;
	}	
	
	
}
