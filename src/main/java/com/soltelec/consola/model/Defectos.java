/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
*
* @author Gerencia Desarrollo de Soluciones Tecnologicas
*/
@Entity
@Table(name = "defectos")
@NamedQueries({
	 @NamedQuery(name = "Defectos.findByCtxSensorial", query = "SELECT d FROM  Defectos d WHERE d.tipoVehiculo = :tipoVehiculo and d.particionVehiculo= :particionVehiculo and d.tipoMiga= :tipoMiga order by d.secuenciaVista"),
    @NamedQuery(name = "Defectos.findAll", query = "SELECT d FROM Defectos d")})
public class Defectos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CARDEFAULT")
    private Integer cardefault;    
    @Basic(optional = false)
    @Column(name = "tipo_vehiculo")
    private Integer tipoVehiculo;    
    @Basic(optional = false)
    @Column(name = "particion_vehiculo")
    private Integer particionVehiculo;    
    @Basic(optional = false)
    @Column(name = "tipo_miga")
    private Integer tipoMiga;    
    @Basic(optional = false)
    @Column(name = "consecutivo_defecto")
    private Integer secuenciaVista;   
    
    @Column(name = "Nombre_problema",length = 670)
    private String nombreproblema;
    @Basic(optional = false)
    @Column(name = "Tipo_defecto")
    private String tipodefecto;  
    @Basic(optional = false)
    @Column(name = "Codigo_Super")
    private String codigoSuperintendencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardefault")
    private List<Permisibles> permisiblesList;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "defectos")
    private List<Defxprueba> defxpruebaList;    
     @JoinColumn(name = "DEFGROUPSSUB", referencedColumnName = "SCDEFGROUPSUB")
    @ManyToOne(optional = false)
    private SubGrupo subGrupo;

    public Defectos() {
    }

    public Defectos(Integer cardefault) {
        this.cardefault = cardefault;
    }

    public Defectos(Integer cardefault, String tipodefecto) {
        this.cardefault = cardefault;
        this.tipodefecto = tipodefecto;
    }

    public Integer getCardefault() {
        return cardefault;
    }

    public void setCardefault(Integer cardefault) {
        this.cardefault = cardefault;
    }

    public String getNombreproblema() {
        return nombreproblema;
    }

    public void setNombreproblema(String nombreproblema) {
        this.nombreproblema = nombreproblema;
    }

    public String getTipodefecto() {
        return tipodefecto;
    }

    public void setTipodefecto(String tipodefecto) {
        this.tipodefecto = tipodefecto;
    }

  
    public SubGrupo getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(SubGrupo subGrupo) {
        this.subGrupo = subGrupo;
    }

    
    public String getCodigoSuperintendencia() {
        return codigoSuperintendencia;
    }

    public void setCodigoSuperintendencia(String codigoSuperintendencia) {
        this.codigoSuperintendencia = codigoSuperintendencia;
    }

    public Integer getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(Integer tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Integer getParticionVehiculo() {
		return particionVehiculo;
	}

	public void setParticionVehiculo(Integer particionVehiculo) {
		this.particionVehiculo = particionVehiculo;
	}

	public Integer getTipoMiga() {
		return tipoMiga;
	}

	public void setTipoMiga(Integer tipoMiga) {
		this.tipoMiga = tipoMiga;
	}

	public Integer getSecuenciaVista() {
		return secuenciaVista;
	}

	public void setSecuenciaVista(Integer secuenciaVista) {
		this.secuenciaVista = secuenciaVista;
	}

	public List<Permisibles> getPermisiblesList() {
        return permisiblesList;
    }

    public void setPermisiblesList(List<Permisibles> permisiblesList) {
        this.permisiblesList = permisiblesList;
    }

     

    public List<Defxprueba> getDefxpruebaList() {
        return defxpruebaList;
    }

    public void setDefxpruebaList(List<Defxprueba> defxpruebaList) {
        this.defxpruebaList = defxpruebaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardefault != null ? cardefault.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Defectos)) {
            return false;
        }
        Defectos other = (Defectos) object;
        if ((this.cardefault == null && other.cardefault != null) || (this.cardefault != null && !this.cardefault.equals(other.cardefault))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.sart.core.ejb.domain.Defectos[ cardefault=" + cardefault + " ]";
    }

}
