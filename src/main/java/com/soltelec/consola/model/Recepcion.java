package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "recepcion")
@NamedQueries({
    @NamedQuery(name = "Recepcion.findAll", query = "SELECT r FROM Recepcion r")})
public class Recepcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="sequence_recepcion")
    @TableGenerator(
		    name="sequence_recepcion",
		    table="generator_table",
		    pkColumnName = "key",
		    valueColumnName = "next",
		    pkColumnValue="recepcion",
		    allocationSize=5
		)
    @Column(name = "id_recepcion")
    private Integer idRecepcion; 
    @Basic(optional = true)
    @Column(name = "placa",nullable=true)
    private String placa;
    @Basic(optional = true)
    @Column(name = "id_departamento")
    private Integer idDepartamento;    
    @Basic(optional = true)
    @Column(name = "id_ciudad")
    private Integer idCiudad;  
    @Basic(optional = true)
    @Column(name = "nro_licencia",nullable=true)
    private String nroLicencia;
    @Basic(optional = true)
    @Column(name = "ciudadania",nullable=true)
    private String ciudadania;
    @Basic(optional = true)
    @Column(name = "marca",nullable=true)
    private String marca;
    @Basic(optional =true)
    @Column(name = "nombres",nullable=true)
    private String nombres;
    @Basic(optional = true)
    @Column(name = "apellidos",nullable=true)
    private String apellidos;
    @Basic(optional = true)
    @Column(name = "cedula",nullable=true)
    private String cedula;
    @Basic(optional = true)
    @Column(name = "direccion",nullable=true)
    private String direccion;
    @Basic(optional = true)
    @Column(name = "telefono",nullable=true)
    private String telefono;
    @Basic(optional = true)
    @Column(name = "correo",nullable=true)
    private String correo;
    @Basic(optional = true)
    @Column(name = "linea",nullable=true)
    private String Linea;
    @Basic(optional = true)
    @Column(name = "modelo",nullable=true)
    private String modelo;    
    @Basic(optional =true)
    @Column(name = "color",nullable=true)
    private String color;    
    @Basic(optional =true)
    @Column(name = "servicio",nullable=true)
    private String servicio;   
    @Basic(optional =true)
    @Column(name = "cilindrada",nullable=true)
    private String cilindrada;    
    @Basic(optional = true)
    @Column(name = "combustible",nullable=true)
    private String combustible; 
    @Basic(optional = true)
    @Column(name = "capacidad",nullable=true)
    private String capacidad;  
    @Basic(optional =true)
    @Column(name = "carroceria",nullable=true)
    private String carroceria;    
    @Basic(optional = true)
    @Column(name = "nro_serie",nullable=true)
    private String nroSerie;    
    @Basic(optional = true)
    @Column(name = "nro_chasis",nullable=true)
    private String nroChasis;    
    @Basic(optional = true)
    @Column(name = "nro_motor",nullable=true)
    private String nroMotor;    
    @Basic(optional = true)
    @Column(name = "nro_sillas",nullable=true)
    private String nroSillas;  
    @Basic(optional = true)
    @Column(name = "nro_vin",nullable=true)
    private String nroVin;
    @Basic(optional = true)
    @Column(name = "fecha_matricula",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMatricula;
    @Basic(optional = true)
    @Column(name = "es_mismo",nullable=true)
    private Boolean esMismo;
    @Basic(optional = true)
    @Column(name = "posee_pacha",nullable=true)
    private Boolean poseePacha;
    @Basic(optional = true)
    @Column(name = "es_scooter",nullable=true)
    private Boolean esScooter;    
    @Column(name = "ciudadania_conduc",nullable=true)
    private String ciudadaniaConduc;    
    @Basic(optional =true)
    @Column(name = "nombres_conduc",nullable=true)
    private String nombresConduc;
    @Basic(optional = true)
    @Column(name = "apellidos_conduc",nullable=true)
    private String apellidosConduc;;
    @Basic(optional = true)
    @Column(name = "cedula_conduc",nullable=true)
    private String cedulaConduc;
    @Basic(optional = true)
    @Column(name = "direccion_conduc",nullable=true)
    private String direccionConduc;    
    @Basic(optional = true)
    @Column(name = "edo_vehiculo",nullable=true)
    private String edoVehiculo;    
    @Basic(optional = true)
    @Column(name = "clase_vehiculo",nullable=true)
    private String claseVehiculo;    
    @Basic(optional = true)
    @Column(name = "esEnsenanza",nullable=true)
    private String esEnsenanza;    
    @Basic(optional = true)
    @Column(name = "nro_puertas",nullable=true)
    private String nroPuertas;    
    @Basic(optional = true)
    @Column(name = "nro_polizaSoat",nullable=true)
    private String nroPolizaSoat;    
    @Basic(optional = true)
    @Column(name = "entidad_soat",nullable=true)
    private String EntidadSoat;  
    @Basic(optional = true)
    @Column(name = "capacida_carga",nullable=true)
    private String capacidaCarga;    
    @Basic(optional = true)
    @Column(name = "peso_bruto",nullable=true)
    private String pesoBruto;
    @Basic(optional = true)
    @Column(name = "nroejes",nullable=true)
    private String nroEjes;    
    @Basic(optional = true)
    @Column(name = "fecha_expedicion_soat",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpedicionSoat;
    @Basic(optional = true)
    @Column(name = "fecha_fin_expedicion_soat",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinExpedicionSoat;    
    @Basic(optional = true)
    @Column(name = "fecha_inic_expedicion_soat",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicExpedicionSoat;   
    @Basic(optional = true)
    @Column(name = "fecha_registro",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = true)
    @Column(name = "fecha_fin_proceso",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinProceso;    
    @Basic(optional = true)
    @Column(name = "fk_usuario")
    private Integer usuario;    
    @Basic(optional = true)
    @Column(name = "nro_factura")
    private String nroFactura;
    
    @Basic(optional = true)
    @Column(name = "nro_pin")
    private String nroPin;
    
    
    public Recepcion() {
    }
   
    public Integer getIdRecepcion() {
		return idRecepcion;
	}

	public void setIdRecepcion(Integer idRecepcion) {
		this.idRecepcion = idRecepcion;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public String getNroPin() {
		return nroPin;
	}

	public void setNroPin(String nroPin) {
		this.nroPin = nroPin;
	}

	public String getNroLicencia() {
		return nroLicencia;
	}

	public void setNroLicencia(String nroLicencia) {
		this.nroLicencia = nroLicencia;
	}

	public Date getFechaFinProceso() {
		return fechaFinProceso;
	}

	public void setFechaFinProceso(Date fechaFinProceso) {
		this.fechaFinProceso = fechaFinProceso;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}		
	public Integer getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Integer getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getLinea() {
		return Linea;
	}

	public void setLinea(String linea) {
		Linea = linea;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getPoseePacha() {
		return poseePacha;
	}

	public void setPoseePacha(Boolean poseePacha) {
		this.poseePacha = poseePacha;
	}

	public Boolean getEsScooter() {
		return esScooter;
	}

	public void setEsScooter(Boolean esScooter) {
		this.esScooter = esScooter;
	}

	public String getServicio() {
		return servicio;
	}

	public String getCiudadania() {
		return ciudadania;
	}

	public void setCiudadania(String ciudadania) {
		this.ciudadania = ciudadania;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEsMismo() {
		return esMismo;
	}

	public void setEsMismo(Boolean esMismo) {
		this.esMismo = esMismo;
	}

	public String getCiudadaniaConduc() {
		return ciudadaniaConduc;
	}

	public void setCiudadaniaConduc(String ciudadaniaConduc) {
		this.ciudadaniaConduc = ciudadaniaConduc;
	}

	public String getNombresConduc() {
		return nombresConduc;
	}

	public void setNombresConduc(String nombresConduc) {
		this.nombresConduc = nombresConduc;
	}

	public String getApellidosConduc() {
		return apellidosConduc;
	}

	public void setApellidosConduc(String apellidosConduc) {
		this.apellidosConduc = apellidosConduc;
	}

	public String getCedulaConduc() {
		return cedulaConduc;
	}

	public void setCedulaConduc(String cedulaConduc) {
		this.cedulaConduc = cedulaConduc;
	}

	public String getDireccionConduc() {
		return direccionConduc;
	}

	public void setDireccionConduc(String direccionConduc) {
		this.direccionConduc = direccionConduc;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getNroSerie() {
		return nroSerie;
	}

	public void setNroSerie(String nroSerie) {
		this.nroSerie = nroSerie;
	}

	public String getNroChasis() {
		return nroChasis;
	}

	public void setNroChasis(String nroChasis) {
		this.nroChasis = nroChasis;
	}

	public String getNroMotor() {
		return nroMotor;
	}

	public void setNroMotor(String nroMotor) {
		this.nroMotor = nroMotor;
	}

	public String getNroSillas() {
		return nroSillas;
	}

	public void setNroSillas(String nroSillas) {
		this.nroSillas = nroSillas;
	}

	public Date getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(Date fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public Recepcion(String placa) {
        this.placa = placa;
    }    

    
    public String getNroVin() {
		return nroVin;
	}

	public void setNroVin(String nroVin) {
		this.nroVin = nroVin;
	}	
	
	public String getEdoVehiculo() {
		return edoVehiculo;
	}

	public void setEdoVehiculo(String edoVehiculo) {
		this.edoVehiculo = edoVehiculo;
	}

	public String getClaseVehiculo() {
		return claseVehiculo;
	}

	public void setClaseVehiculo(String claseVehiculo) {
		this.claseVehiculo = claseVehiculo;
	}

	public String getEsEnsenanza() {
		return esEnsenanza;
	}

	public void setEsEnsenanza(String esEnsenanza) {
		this.esEnsenanza = esEnsenanza;
	}

	public String getNroPuertas() {
		return nroPuertas;
	}

	public void setNroPuertas(String nroPuertas) {
		this.nroPuertas = nroPuertas;
	}

	public String getNroPolizaSoat() {
		return nroPolizaSoat;
	}

	public void setNroPolizaSoat(String nroPolizaSoat) {
		this.nroPolizaSoat = nroPolizaSoat;
	}

	public String getEntidadSoat() {
		return EntidadSoat;
	}

	public void setEntidadSoat(String entidadSoat) {
		EntidadSoat = entidadSoat;
	}

	public String getCapacidaCarga() {
		return capacidaCarga;
	}

	public void setCapacidaCarga(String capacidaCarga) {
		this.capacidaCarga = capacidaCarga;
	}

	public String getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(String pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getNroEjes() {
		return nroEjes;
	}

	public void setNroEjes(String nroEjes) {
		this.nroEjes = nroEjes;
	}

	public Date getFechaExpedicionSoat() {
		return fechaExpedicionSoat;
	}

	public void setFechaExpedicionSoat(Date fechaExpedicionSoat) {
		this.fechaExpedicionSoat = fechaExpedicionSoat;
	}

	public Date getFechaFinExpedicionSoat() {
		return fechaFinExpedicionSoat;
	}

	public void setFechaFinExpedicionSoat(Date fechaFinExpedicionSoat) {
		this.fechaFinExpedicionSoat = fechaFinExpedicionSoat;
	}

	public Date getFechaInicExpedicionSoat() {
		return fechaInicExpedicionSoat;
	}

	public void setFechaInicExpedicionSoat(Date fechaInicExpedicionSoat) {
		this.fechaInicExpedicionSoat = fechaInicExpedicionSoat;
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecepcion != null ? idRecepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recepcion)) {
            return false;
        }
        Recepcion other = (Recepcion) object;
        return (this.idRecepcion != null || other.idRecepcion == null) && (this.idRecepcion == null || this.idRecepcion.equals(other.idRecepcion));
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Recepcion[id=" + idRecepcion + "]";
    }

}
