package com.soltelec.consola.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "registro_rechazo")
@NamedQueries({
    @NamedQuery(name = "RegistroRechazo.findAll", query = "SELECT r FROM RegistroRechazo  r"),
    @NamedQuery(name = "RegistroRechazo.FindByDate", query = "SELECT r FROM RegistroRechazo r  WHERE  r.fechaRegistro BETWEEN  :fechaRegistroIni and :fechaRegistroFin ")
})
public class RegistroRechazo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="sequence_rechazo")
    @TableGenerator(
		    name="sequence_rechazo",
		    table="generator_table",
		    pkColumnName = "key",
		    valueColumnName = "next",
		    pkColumnValue="reg_rechazo",
		    allocationSize=50
		)
    @Column(name = "id_rechazo")
    private Integer idRechazo;
    @Basic(optional = false)
    @Column(name = "placa")
    private String placa;    
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;       
    @Basic(optional = false)
    @Column(name = "descripcion_rechazo")
    private String descripcionRechazo;    
    @Basic(optional = false)
    @Column(name = "type_handler")
    private Integer typeHandler;      
    @Basic(optional = false)
    @Column(name = "usuario")
    private Integer usuario;    
    @Column(name = "OBSERVACION")
    private String Observacion;
    
    @Basic(optional = false)
    @Column(name = "fk_pre_revision")
    private Integer fkPreRevision;
    
    public RegistroRechazo() {
    }

    public RegistroRechazo(Integer idRechazo) {
        this.idRechazo= idRechazo;
    }    

    public Integer getIdRechazo() {
		return idRechazo;
	}

	public void setIdRechazo(Integer idRechazo) {
		this.idRechazo = idRechazo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDescripcionRechazo() {
		return descripcionRechazo;
	}

	public void setDescripcionRechazo(String descripcionRechazo) {
		this.descripcionRechazo = descripcionRechazo;
	}

	public Integer getTypeHandler() {
		return typeHandler;
	}

	public void setTypeHandler(Integer typeHandler) {
		this.typeHandler = typeHandler;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }
    
    

    public Integer getFkPreRevision() {
		return fkPreRevision;
	}

	public void setFkPreRevision(Integer fkPreRevision) {
		this.fkPreRevision = fkPreRevision;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idRechazo!= null ? idRechazo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroRechazo)) {
            return false;
        }
        RegistroRechazo other = (RegistroRechazo) object;
        return (this.idRechazo!= null || other.idRechazo== null) && (this.idRechazo== null || this.idRechazo.equals(other.idRechazo));
    }

    @Override
    public String toString() {
        return "com.soltelec.model.RegistroRechazo[idRechazo=" + idRechazo+ "]";
    }
}
