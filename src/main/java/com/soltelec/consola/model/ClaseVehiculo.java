/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;

/**
*
* @author GerenciaDesarrollo
*/
@Entity
@Table(name = "clases_vehiculo")
@NamedQueries({
    @NamedQuery(name = "ClaseVehiculo.findByClasesActivas", query = "SELECT c FROM ClaseVehiculo c WHERE c.activo = 1 order by c.id")})
public class ClaseVehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLASS")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre_clase")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "activo")
    private Integer activo;
    

    public ClaseVehiculo() {
    }

    public ClaseVehiculo(Integer id) {
        this.id = id;
    }

    public ClaseVehiculo(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer class1) {
        this.id = class1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreclase) {
        this.nombre = nombreclase;
    }

    
    public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
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
        if (!(object instanceof ClaseVehiculo)) {
            return false;
        }
        ClaseVehiculo other = (ClaseVehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
