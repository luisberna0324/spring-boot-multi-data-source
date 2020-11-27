package com.soltelec.consola.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "reg_novedades")
@NamedQueries({
    @NamedQuery(name = "RegNovedades.findAll", query = "SELECT rn FROM RegNovedades rn"),
    @NamedQuery(name = "RegNovedades.findLstEvidencias", query = "SELECT rn.regEvidencia FROM RegNovedades rn WHERE  rn.fkPreRevision= :fkPreRevision")
})
public class RegNovedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="sequence_novedades")
    @TableGenerator(
		    name="sequence_novedades",
		    table="generator_table",
		    pkColumnName = "key",
		    valueColumnName = "next",
		    pkColumnValue="reg_novedades",
		    allocationSize=50
		)
    @Column(name = "no_novedad")
    private Integer noNovedad;    
    @Basic(optional = true)
    @Column(name = "obs_perpectiva",nullable=true)
    private String obsPerpectiva;
    @Basic(optional = true)
    @Column(name = "contexto",nullable=true)
    private Integer contexto;  
    @Basic(optional = false)
    @Column(name = "handler")
    private Integer handler;
    @Basic(optional = false)
    @Column(name = "fk_pre_revision")
    private Integer fkPreRevision;    
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "reg_evidencia", columnDefinition="bytea")
    private byte[] regEvidencia; 
        
    public RegNovedades() {
    }
    
	public Integer getNoNovedad() {
		return noNovedad;
	}

	public void setNoNovedad(Integer noNovedad) {
		this.noNovedad = noNovedad;
	}

	public String getObsPerpectiva() {
		return obsPerpectiva;
	}

	public void setObsPerpectiva(String obsPerpectiva) {
		this.obsPerpectiva = obsPerpectiva;
	}

	public Integer getHandler() {
		return handler;
	}


	public void setHandler(Integer handler) {
		this.handler = handler;
	}


	public Integer getFkPreRevision() {
		return fkPreRevision;
	}

	public void setFkPreRevision(Integer fkPreRevision) {
		this.fkPreRevision = fkPreRevision;
	}

	public Integer getContexto() {
		return contexto;
	}

	public void setContexto(Integer contexto) {
		this.contexto = contexto;
	}

	public byte[] getRegEvidencia() {
		return regEvidencia;
	}

	public void setRegEvidencia(byte[] regEvidencia) {
		this.regEvidencia = regEvidencia;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (noNovedad!= null ? noNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegNovedades)) {
            return false;
        }
        RegNovedades other = (RegNovedades) object;
        return (this.noNovedad!= null || other.noNovedad== null) && (this.noNovedad== null || this.noNovedad.equals(other.noNovedad));
    }

    @Override
    public String toString() {
        return "com.soltelec.model.RegNovedades[noNovedad=" + noNovedad+ "]";
    }
}
