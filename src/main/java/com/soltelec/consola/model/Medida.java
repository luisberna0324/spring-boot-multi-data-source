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
@Table(name = "medidas")
@NamedQueries({
    @NamedQuery(name = "Medida.findAll", query = "SELECT m FROM Medida m"),
    @NamedQuery(name = "Medida.findByMeasure", query = "SELECT m FROM Medida m WHERE m.id = :id"),
    @NamedQuery(name = "Medida.findByValormedida", query = "SELECT m FROM Medida m WHERE m.valor = :valormedida"),
    @NamedQuery(name = "Medida.findByCondicion", query = "SELECT m FROM Medida m WHERE m.condicion = :condicion")})
public class Medida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASURE")
    private Integer id;
    @Column(name = "Valor_medida")
    private Float valor;
    @Column(name = "Condicion")
    private String condicion;    
    @JoinColumn(name = "MEASURETYPE", referencedColumnName = "MEASURETYPE")
    @ManyToOne(optional = false)
    private TipoMedida tipoMedida;
    @JoinColumn(name = "TEST", referencedColumnName = "Id_Pruebas")
    @ManyToOne(optional = false)
    private Prueba prueba;

    
    public Medida() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer measure) {
        this.id = measure;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valormedida) {
        this.valor = valormedida;
    }

    public String getCondicion() {
        if (condicion != null)
        return " " + condicion;
        else 
            return " ";
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(TipoMedida tiposMedida) {
        this.tipoMedida = tiposMedida;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba pruebas) {
        this.prueba = pruebas;
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
        if (!(object instanceof Medida)) {
            return false;
        }
        Medida other = (Medida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String asterisco = this.getCondicion() != null? "*" : "";        
        return this.getValor()+ ' ' + asterisco;
    }

}
