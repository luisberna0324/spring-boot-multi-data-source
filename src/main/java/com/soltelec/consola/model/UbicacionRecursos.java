/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;

/**
*
* @author Gerencia Desarrollo de Soluciones Tecnologicas
*/
@Entity
@Table(name = "ubicacion_recurso")
@NamedQueries({
    @NamedQuery(name = "UbicacionRecursos.findAll", query = "SELECT ub FROM UbicacionRecursos ub") })
public class UbicacionRecursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ip_visual")
    private String ipVisual ;
    @Column(name = "instancia")
    private Integer instancia;

    
    public UbicacionRecursos() {
    }

    public UbicacionRecursos(Integer id) {
        this.id = id;
    }

    public UbicacionRecursos(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idDepartamento) {
        this.id = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIpVisual() {
		return ipVisual;
	}

	public void setIpVisual(String ipVisual) {
		this.ipVisual = ipVisual;
	}

	public void setNombre(String nombreDepartamento) {
        this.nombre = nombreDepartamento;
    }
    
    public Integer getInstancia() {
		return instancia;
	}

	public void setInstancia(Integer instancia) {
		this.instancia = instancia;
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
        if (!(object instanceof UbicacionRecursos)) {
            return false;
        }
        UbicacionRecursos other = (UbicacionRecursos) object;
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
