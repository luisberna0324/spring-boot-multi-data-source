/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*
* @author Gerencia Desarrollo de Soluciones Tecnologicas
*/
@Entity
@NamedQueries({
    @NamedQuery(name = "Conductor.findByIdentificacion", query = "SELECT c FROM Conductor  c WHERE c.id = :identificacion")
})
@Table(name = "conductor")
public class Conductor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Id_conductor")
    private Long id;
    @Column(name = "tipo_identificacion")
    @Enumerated(EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "Nombres")
    private String nombres;    
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "Numero_telefono")
    private String telefono;    
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Num_licencia")
    private String licencia;
     
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario",fetch= FetchType.LAZY)
    private List<Vehiculo> vehiculoList;
    @OneToMany(mappedBy = "propietario",fetch= FetchType.LAZY)
    private List<HojaPruebas> listHojaPruebas;
    @ManyToOne
    @JoinColumn(name = "Ciudad_for")
    private Ciudad ciudad;

    public Conductor() {        
        fechaRegistro = new Date();
    }

    public Conductor(Long carowner) {
        this.id = carowner;
    }

    public Conductor(Long carowner, String varcharBorrar, String nombres, int geuser, Date fechaRegistro, String numerotelefono, String direccion) {
        this.id = carowner;        
        this.nombres = nombres;        
        this.fechaRegistro = fechaRegistro;
        this.telefono = numerotelefono;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long identificacion) {
        this.id = identificacion;
    }

    

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

        
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculosList) {
        this.vehiculoList = vehiculosList;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<HojaPruebas> getListHojaPruebas() {
        return listHojaPruebas;
    }

    public void setListHojaPruebas(List<HojaPruebas> listHojaPruebas) {
        this.listHojaPruebas = listHojaPruebas;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conductor)) {
            return false;
        }
        Conductor other = (Conductor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.soltelec.persistencia.Conductor[Id_Conductor=" + id + "]";
    }

}
