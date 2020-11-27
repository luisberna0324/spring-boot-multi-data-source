package com.soltelec.consola.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "pre_revision")
@NamedQueries({ @NamedQuery(name = "PreRevision.findAll", query = "SELECT p FROM PreRevision p"),
		@NamedQuery(name = "PreRevision.findFollowingTurno", query = "SELECT Max(pr.noTurno) FROM  PreRevision pr  where pr.categoria = :ctxCategoria and  pr.fecha = :fecha"),
		@NamedQuery(name = "PreRevision.findCountOrdenPrep", query = "SELECT count(p.noRevision) FROM PreRevision p where p.fecha = :fecha and p.culminado =7"),
		@NamedQuery(name = "PreRevision.findOrdenPrerevision", query = "SELECT pr.placa,r.servicio,r.claseVehiculo,pr.esRTM,pr.fechaRevision,r.apellidosConduc ,r.nombresConduc,r.nroEjes,pr.noRevision,pr.primeraVez,pr.noTurno,pr.nacionalidad FROM Recepcion r, PreRevision pr  where pr.fkRecepcion = r.idRecepcion and pr.culminado = :culminado and pr.fecha = :fechaRevision and pr.categoria = :categoria and pr.esRTM = :tipoRevision and pr.primeraVez = :ctxInspeccion    ORDER BY pr.noRevision "),
		@NamedQuery(name = "PreRevision.findHojaPend", query = "SELECT pr.noRevision,pr.placa,pr.esRTM,pr.primeraVez,pr.noTurno,r.servicio,r.claseVehiculo,pr.tipoVehiculo,r.nroEjes,pr.esEnsenanza,r.poseePacha,pr.nacionalidad FROM PreRevision pr,Recepcion r WHERE pr.fkRecepcion = r.idRecepcion and pr.culminado = 7 and pr.fecha = :fechaIngreso and  pr.primeraVez = :primeraVez ORDER BY pr.noTurno"),
		@NamedQuery(name = "PreRevision.bindRecepcion", query = "SELECT pr.noRevision  FROM PreRevision pr  where pr.fkRecepcion = :recepcion "),
		@NamedQuery(name = "PreRevision.findMaxPreRevPlaca", query = "SELECT Max(p.noRevision) FROM PreRevision p where p.placa = :placa") })

public class PreRevision implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence_revision")
	@TableGenerator(name = "sequence_revision", table = "generator_table", pkColumnName = "key", valueColumnName = "next", pkColumnValue = "pre_revision", allocationSize = 5)
	@Column(name = "no_revision")
	private Integer noRevision;
	@Basic(optional = true)
	@Column(name = "kilometraje", nullable = true)
	private Integer kilometraje;
	@Basic(optional = true)
	@Column(name = "tipo_vehiculo", nullable = true)
	private Integer tipoVehiculo;
	@Basic(optional = true)
	@Column(name = "no_gestor", nullable = true)
	private Integer noGestor;
	@Basic(optional = true)
	@Column(name = "no_empresa", nullable = true)
	private Integer noEmpresa;
	@Basic(optional = true)
	@Column(name = "no_exosto", nullable = true)
	private Integer noExosto;
	@Basic(optional = true)
	@Column(name = "no_llanta", nullable = true)
	private String noLLanta;
	
	@Basic(optional = true)
	@Column(name = "nacionalidad", nullable = true)
	private String nacionalidad;
	
	@Basic(optional = true)
	@Column(name = "psi_ej1_der", nullable = true)
	private Float psiEj1Der;
	@Basic(optional = true)
	@Column(name = "psi_ej2_der", nullable = true)
	private Float psiEj2Der;

	@Basic(optional = true)
	@Column(name = "psi_ej2_der_int", nullable = true)
	private Float psiEj2DerInt;
	@Basic(optional = true)
	@Column(name = "psi_ej3_der_int", nullable = true)
	private Float psiEj3DerInt;
	@Basic(optional = true)
	@Column(name = "psi_ej4_der_int", nullable = true)
	private Float psiEj4DerInt;
	@Basic(optional = true)
	@Column(name = "psi_ej5_der_int", nullable = true)
	private Float psiEj5DerInt;
	@Basic(optional = true)
	@Column(name = "psi_ej2_izq_int", nullable = true)
	private Float psiEj2IzqInt;
	@Basic(optional = true)
	@Column(name = "psi_ej3_izq_int", nullable = true)
	private Float psiEj3IzqInt;
	@Basic(optional = true)
	@Column(name = "psi_ej4_izq_int", nullable = true)
	private Float psiEj4IzqInt;
	@Basic(optional = true)
	@Column(name = "psi_ej5_izq_int", nullable = true)
	private Float psiEj5IzqInt;

	@Basic(optional = true)
	@Column(name = "psi_ej3_der", nullable = true)
	private Float psiEj3Der;
	@Basic(optional = true)
	@Column(name = "psi_ej4_der", nullable = true)
	private Float psiEj4Der;
	@Basic(optional = true)
	@Column(name = "psi_ej5_der", nullable = true)
	private Float psiEj5Der;
	@Basic(optional = true)
	@Column(name = "psi_ej1_izq", nullable = true)
	private Float psiEj1Izq;
	@Basic(optional = true)
	@Column(name = "psi_ej2_izq", nullable = true)
	private Float psiEj2Izq;
	@Basic(optional = true)
	@Column(name = "psi_ej3_izq", nullable = true)
	private Float psiEj3Izq;
	@Basic(optional = true)
	@Column(name = "psi_ej4_izq", nullable = true)
	private Float psiEj4Izq;
	@Basic(optional = true)
	@Column(name = "psi_repuesto", nullable = true)
	private Float psiRepuesto;
	@Basic(optional = true)
	@Column(name = "psi_ej5_izq", nullable = true)
	private Float psiEj5Izq;
	@Basic(optional = true)
	@Column(name = "placa")
	private String placa;
	@Basic(optional = true)
	@Column(name = "fecha_revision")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRevision;
	@Basic(optional = true)
	@Column(name = "fecha_fin_proceso", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFinProceso;
	@Basic(optional = true)
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@Basic(optional = true)
	@Column(name = "fk_usuario")
	private Integer usuario;
	@Basic(optional = true)
	@Column(name = "observacion", nullable = true)
	private String Observacion;
	@Basic(optional = true)
	@Column(name = "otros_elementos_declarados", nullable = true)
	private String otrosElementosDeclarados;
	@Basic(optional = false)
	@Column(name = "no_turno")
	private Integer noTurno;

	@Basic(optional = false)
	@Column(name = "categoria")
	private Integer categoria;

	@Basic(optional = false)
	@Column(name = "nro_certificado")
	private String nroCertificado;
	
			
	@Basic(optional = true)
	@Column(name = "fecha_venc_certificado", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVencCertificado;
	@Basic(optional = false)
	@Column(name = "fk_recepcion")
	private Integer fkRecepcion;

	@Basic(optional = true)
	@Column(name = "limpio", nullable = true)
	private Boolean Limpio;	

	@Basic(optional = true)
	@Column(name = "luces_encendida", nullable = true)
	private Boolean lucesEncendida;	
	@Basic(optional = false)
	@Column(name = "vacio", nullable = true)
	private Boolean vacio;
	@Basic(optional = true)
	@Column(name = "seguridad", nullable = true)
	private Boolean Seguridad;
	@Basic(optional = true)
	@Column(name = "tapaCubos", nullable = true)
	private Boolean tapaCubos;
	
	@Basic(optional = true)
	@Column(name = "posee_certificado_gas", nullable = true)
	private Boolean poseeCertificadoGas;	

	@Basic(optional = true)
	@Column(name = "tarjeta_soat", nullable = true)
	private Boolean tarjetaSoat;
	@Basic(optional = true)
	@Column(name = "mismo_color", nullable = true)
	private Boolean mismoColor;
	@Basic(optional = true)
	@Column(name = "vidrios_polarizados", nullable = true)
	private Boolean vidriosPolarizados;
	@Basic(optional = true)
	@Column(name = "blindado", nullable = true)
	private Boolean blindado;
	@Basic(optional = true)
	@Column(name = "es_ensenanza", nullable = true)
	private Boolean esEnsenanza;
	@Basic(optional = true)
	@Column(name = "exploradoras", nullable = true)
	private Boolean exploradoras;
	@Basic(optional = true)
	@Column(name = "preparado")
	private Boolean preparado;
	@Basic(optional = true)
	@Column(name = "es_rtm")
	private Boolean esRTM;
	@Basic(optional = true)
	@Column(name = "culminado", nullable = true)
	private Integer culminado;
	@Basic(optional = true)
	@Column(name = "primera_vez", nullable = true)
	private Integer primeraVez;
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "firma_revision", columnDefinition = "bytea")
	private byte[] firmaRevision;
	
	
	
	
	@Basic(optional = false)
	@Column(name = "ultima_revision")
	private String ultimaRevision;	

	@Basic(optional = false)
	@Column(name = "empresa_certificado")
	private String empresaCertificado;	
	@Basic(optional = true)
	@Column(name = "es_ambulancia")
	private Boolean esAmbulancia;	
	@Basic(optional = true)
	@Column(name = "es_bombero")
	private Boolean esBombero;	
	@Basic(optional = true)
	@Column(name = "tiempos_motor")
	private Integer tiemposMotor;	
	@Basic(optional = true)
	@Column(name = "posee_radio")
	private Boolean poseeRadio;	
	@Basic(optional = true)
	@Column(name = "posee_encendedor")
	private Boolean poseeEncendedor;	
	@Basic(optional = true)
	@Column(name = "posee_antena")
	private Boolean poseeAntena;	
	@Basic(optional = true)
	@Column(name = "baul_accesible")
	private Boolean baulAccesible;	
	@Basic(optional = true)
	@Column(name = "bateria_accesible")
	private Boolean bateriaAccesible;	
	@Basic(optional = true)
	@Column(name = "filtro_aire")
	private Boolean filtroAire;
	
	@Basic(optional = true)
	@Column(name = "mirilla_freno")
	private Boolean mirillFreno;	
	@Basic(optional = true)
	@Column(name = "llanta_repuesto_visible")
	private Boolean llantaRepuestoVisible;
	

	public PreRevision() {
	}

	public Integer getNoRevision() {
		return noRevision;
	}

	public void setNoRevision(Integer noRevision) {
		this.noRevision = noRevision;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public Date getFechaFinProceso() {
		return fechaFinProceso;
	}

	public void setFechaFinProceso(Date fechaFinProceso) {
		this.fechaFinProceso = fechaFinProceso;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public String getUltimaRevision() {
		return ultimaRevision;
	}

	public void setUltimaRevision(String ultimaRevision) {
		this.ultimaRevision = ultimaRevision;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Integer getUsuario() {
		return usuario;
	}
	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Boolean getVidriosPolarizados() {
		return vidriosPolarizados;
	}
	
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public String getNroCertificado() {
		return nroCertificado;
	}

	public void setNroCertificado(String nroCertificado) {
		this.nroCertificado = nroCertificado;
	}
	
	public Boolean getLucesEncendida() {
		return lucesEncendida;
	}

	public void setLucesEncendida(Boolean lucesEncendida) {
		this.lucesEncendida = lucesEncendida;
	}

	public Date getFechaVencCertificado() {
		return fechaVencCertificado;
	}

	public void setFechaVencCertificado(Date fechaVencCertificado) {
		this.fechaVencCertificado = fechaVencCertificado;
	}

	public Integer getKilometraje() {
		return kilometraje;
	}

	public Integer getPrimeraVez() {
		return primeraVez;
	}

	public void setPrimeraVez(Integer primeraVez) {
		this.primeraVez = primeraVez;
	}

	public String getEmpresaCertificado() {
		return empresaCertificado;
	}

	public void setEmpresaCertificado(String empresaCertificado) {
		this.empresaCertificado = empresaCertificado;
	}

	public Boolean getEsAmbulancia() {
		return esAmbulancia;
	}

	public void setEsAmbulancia(Boolean esAmbulancia) {
		this.esAmbulancia = esAmbulancia;
	}

	public Boolean getEsBombero() {
		return esBombero;
	}

	public void setEsBombero(Boolean esBombero) {
		this.esBombero = esBombero;
	}

	public Integer getTiemposMotor() {
		return tiemposMotor;
	}

	public void setTiemposMotor(Integer tiemposMotor) {
		this.tiemposMotor = tiemposMotor;
	}

	public Boolean getPoseeRadio() {
		return poseeRadio;
	}

	public void setPoseeRadio(Boolean poseeRadio) {
		this.poseeRadio = poseeRadio;
	}

	public Boolean getPoseeEncendedor() {
		return poseeEncendedor;
	}

	public void setPoseeEncendedor(Boolean poseeEncendedor) {
		this.poseeEncendedor = poseeEncendedor;
	}

	public Boolean getPoseeAntena() {
		return poseeAntena;
	}

	public void setPoseeAntena(Boolean poseeAntena) {
		this.poseeAntena = poseeAntena;
	}

	public Boolean getBaulAccesible() {
		return baulAccesible;
	}

	public void setBaulAccesible(Boolean baulAccesible) {
		this.baulAccesible = baulAccesible;
	}

	public Boolean getBateriaAccesible() {
		return bateriaAccesible;
	}

	public void setBateriaAccesible(Boolean bateriaAccesible) {
		this.bateriaAccesible = bateriaAccesible;
	}

	public Boolean getFiltroAire() {
		return filtroAire;
	}

	public void setFiltroAire(Boolean filtroAire) {
		this.filtroAire = filtroAire;
	}

	public Boolean getMirillFreno() {
		return mirillFreno;
	}

	public void setMirillFreno(Boolean mirillFreno) {
		this.mirillFreno = mirillFreno;
	}

	public Boolean getLlantaRepuestoVisible() {
		return llantaRepuestoVisible;
	}

	public void setLlantaRepuestoVisible(Boolean llantaRepuestoVisible) {
		this.llantaRepuestoVisible = llantaRepuestoVisible;
	}

	public Boolean getTarjetaSoat() {
		return tarjetaSoat;
	}

	public void setTarjetaSoat(Boolean tarjetaSoat) {
		this.tarjetaSoat = tarjetaSoat;
	}

	public Boolean getMismoColor() {
		return mismoColor;
	}

	public void setMismoColor(Boolean mismoColor) {
		this.mismoColor = mismoColor;
	}

	public void setKilometraje(Integer kilometraje) {
		this.kilometraje = kilometraje;
	}
	public Boolean getPoseeCertificadoGas() {
		return poseeCertificadoGas;
	}

	public void setPoseeCertificadoGas(Boolean poseeCertificadoGas) {
		this.poseeCertificadoGas = poseeCertificadoGas;
	}
	
	public Integer getNoGestor() {
		return noGestor;
	}

	public void setNoGestor(Integer noGestor) {
		this.noGestor = noGestor;
	}

	public Integer getNoEmpresa() {
		return noEmpresa;
	}

	public Float getPsiRepuesto() {
		return psiRepuesto;
	}

	public void setPsiRepuesto(Float psiRepuesto) {
		this.psiRepuesto = psiRepuesto;
	}

	public void setNoEmpresa(Integer noEmpresa) {
		this.noEmpresa = noEmpresa;
	}

	public Integer getNoExosto() {
		return noExosto;
	}

	public void setNoExosto(Integer noExosto) {
		this.noExosto = noExosto;
	}

	public String getNoLLanta() {
		return noLLanta;
	}

	public void setNoLLanta(String noLLanta) {
		this.noLLanta = noLLanta;
	}

	public Float getPsiEj1Der() {
		return psiEj1Der;
	}

	public void setPsiEj1Der(Float psiEj1Der) {
		this.psiEj1Der = psiEj1Der;
	}

	public Float getPsiEj2Der() {
		return psiEj2Der;
	}

	public void setPsiEj2Der(Float psiEj2Der) {
		this.psiEj2Der = psiEj2Der;
	}

	public Boolean getBlindado() {
		return blindado;
	}

	public void setBlindado(Boolean blindado) {
		this.blindado = blindado;
	}

	public Boolean getEsEnsenanza() {
		return esEnsenanza;
	}

	public void setEsEnsenanza(Boolean esEnsenanza) {
		this.esEnsenanza = esEnsenanza;
	}

	public Integer getCulminado() {
		return culminado;
	}

	public void setCulminado(Integer culminado) {
		this.culminado = culminado;
	}

	public Float getPsiEj3Der() {
		return psiEj3Der;
	}

	public void setPsiEj3Der(Float psiEj3Der) {
		this.psiEj3Der = psiEj3Der;
	}

	public Float getPsiEj4Der() {
		return psiEj4Der;
	}

	public void setPsiEj4Der(Float psiEj4Der) {
		this.psiEj4Der = psiEj4Der;
	}

	public Float getPsiEj5Der() {
		return psiEj5Der;
	}

	public void setPsiEj5Der(Float psiEj5Der) {
		this.psiEj5Der = psiEj5Der;
	}

	public Float getPsiEj1Izq() {
		return psiEj1Izq;
	}

	public void setPsiEj1Izq(Float psiEj1Izq) {
		this.psiEj1Izq = psiEj1Izq;
	}

	public Float getPsiEj2Izq() {
		return psiEj2Izq;
	}

	public void setPsiEj2Izq(Float psiEj2Izq) {
		this.psiEj2Izq = psiEj2Izq;
	}

	public Float getPsiEj3Izq() {
		return psiEj3Izq;
	}

	public void setPsiEj3Izq(Float psiEj3Izq) {
		this.psiEj3Izq = psiEj3Izq;
	}

	public Float getPsiEj4Izq() {
		return psiEj4Izq;
	}

	public void setPsiEj4Izq(Float psiEj4Izq) {
		this.psiEj4Izq = psiEj4Izq;
	}

	public Float getPsiEj5Izq() {
		return psiEj5Izq;
	}

	public void setPsiEj5Izq(Float psiEj5Izq) {
		this.psiEj5Izq = psiEj5Izq;
	}

	public Float getPsiEj2DerInt() {
		return psiEj2DerInt;
	}

	public void setPsiEj2DerInt(Float psiEj2DerInt) {
		this.psiEj2DerInt = psiEj2DerInt;
	}

	public Float getPsiEj3DerInt() {
		return psiEj3DerInt;
	}

	public void setPsiEj3DerInt(Float psiEj3DerInt) {
		this.psiEj3DerInt = psiEj3DerInt;
	}

	public Float getPsiEj4DerInt() {
		return psiEj4DerInt;
	}

	public void setPsiEj4DerInt(Float psiEj4DerInt) {
		this.psiEj4DerInt = psiEj4DerInt;
	}

	public Float getPsiEj5DerInt() {
		return psiEj5DerInt;
	}

	public void setPsiEj5DerInt(Float psiEj5DerInt) {
		this.psiEj5DerInt = psiEj5DerInt;
	}

	public Float getPsiEj2IzqInt() {
		return psiEj2IzqInt;
	}

	public void setPsiEj2IzqInt(Float psiEj2IzqInt) {
		this.psiEj2IzqInt = psiEj2IzqInt;
	}

	public Float getPsiEj3IzqInt() {
		return psiEj3IzqInt;
	}

	public void setPsiEj3IzqInt(Float psiEj3IzqInt) {
		this.psiEj3IzqInt = psiEj3IzqInt;
	}

	public Float getPsiEj4IzqInt() {
		return psiEj4IzqInt;
	}

	public void setPsiEj4IzqInt(Float psiEj4IzqInt) {
		this.psiEj4IzqInt = psiEj4IzqInt;
	}

	public Float getPsiEj5IzqInt() {
		return psiEj5IzqInt;
	}

	public void setPsiEj5IzqInt(Float psiEj5IzqInt) {
		this.psiEj5IzqInt = psiEj5IzqInt;
	}

	public String getOtrosElementosDeclarados() {
		return otrosElementosDeclarados;
	}

	public void setOtrosElementosDeclarados(String otrosElementosDeclarados) {
		this.otrosElementosDeclarados = otrosElementosDeclarados;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getNoTurno() {
		return noTurno;
	}

	public void setNoTurno(Integer noTurno) {
		this.noTurno = noTurno;
	}

	public Integer getFkRecepcion() {
		return fkRecepcion;
	}

	public void setFkRecepcion(Integer fkRecepcion) {
		this.fkRecepcion = fkRecepcion;
	}

	public Boolean getLimpio() {
		return Limpio;
	}

	public void setLimpio(Boolean limpio) {
		Limpio = limpio;
	}

	public Boolean getVacio() {
		return vacio;
	}

	public void setVacio(Boolean vacio) {
		this.vacio = vacio;
	}

	public Boolean getSeguridad() {
		return Seguridad;
	}

	public void setSeguridad(Boolean seguridad) {
		Seguridad = seguridad;
	}

	public Boolean getTapaCubos() {
		return tapaCubos;
	}

	public void setTapaCubos(Boolean tapaCubos) {
		this.tapaCubos = tapaCubos;
	}

	public Boolean getvidriosPolarizados() {
		return vidriosPolarizados;
	}

	public void setVidriosPolarizados(Boolean vidriosPolarizados) {
		this.vidriosPolarizados = vidriosPolarizados;
	}

	public Boolean getExploradoras() {
		return exploradoras;
	}

	public void setExploradoras(Boolean exploradoras) {
		this.exploradoras = exploradoras;
	}

	public Boolean getPreparado() {
		return preparado;
	}

	public void setPreparado(Boolean preparado) {
		this.preparado = preparado;
	}

	public Boolean getEsRTM() {
		return esRTM;
	}

	public void setEsRTM(Boolean esRTM) {
		this.esRTM = esRTM;
	}

	public byte[] getFirmaRevision() {
		return firmaRevision;
	}

	public void setFirmaRevision(byte[] firmaRevision) {
		this.firmaRevision = firmaRevision;
	}

	public Integer getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(Integer tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (noRevision != null ? noRevision.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof PreRevision)) {
			return false;
		}
		PreRevision other = (PreRevision) object;
		return (this.noRevision != null || other.noRevision == null)
				&& (this.noRevision == null || this.noRevision.equals(other.noRevision));
	}

	@Override
	public String toString() {
		return "com.soltelec.model.PreRevision[noRevision=" + noRevision + "]";
	}
}
