/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.consola.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Gerencia TIC
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
		@NamedQuery(name = "Usuarios.findByNickusuario", query = "SELECT u FROM Usuario u WHERE u.nick = :nickusuario"),
		@NamedQuery(name = "Usuarios.findUsuario", query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula"),
		@NamedQuery(name = "Usuarios.validUserPassw", query = "SELECT u.cedula,u.nombre,u.nick,u.rolUsuario,u.usuario,u.ubicacion  FROM Usuario u WHERE u.nick = :nickusuario and u.contrasena = :contrasenia"),
		@NamedQuery(name = "Usuarios.findByNombreusuario", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombreusuario") })
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tabla_sequences")
	@TableGenerator(name = "tabla_sequences", table = "generator_table", pkColumnName = "key", valueColumnName = "next", pkColumnValue = "usuario", allocationSize = 50)
	@Column(name = "GEUSER")
	private Integer usuario;
	@Basic(optional = false)
	@Column(name = "cedula")
	private String cedula;
	@Basic(optional = false)
	@Column(name = "nick_usuario")
	private String nick;	
	@Column(name = "ubicacion")
    private Integer ubicacion;
	@Basic(optional = false)
	@Column(name = "Nombre_usuario")
	private String nombre;
	@Column(name = "rol")
	private String rolUsuario;
	@Column(name = "contrasenia")
	private String contrasena;
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "firma_usuario", columnDefinition = "bytea")
	private byte[] firmaUsuario;

	public Usuario() {
	}

	public Usuario(Integer geuser) {
		this.usuario = geuser;
	}

	public Usuario(Integer geuser, String nick, String nombreusuario) {
		this.usuario = geuser;
		this.nick = nick;
		this.nombre = nombreusuario;

	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nickusuario) {
		this.nick = nickusuario;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreUsuario) {
		this.nombre = nombreUsuario;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario= rolUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	
	/*
	 * public List<Permissions> getPermissionsList() { return permissionsList; }
	 * 
	 * public void setPermissionsList(List<Permissions> permissionsList) {
	 * this.permissionsList = permissionsList; }
	 * 
	 * public List<HojaPruebas> getHojaPruebasList() { return hojaPruebasList; }
	 * 
	 * public void setHojaPruebasList(List<HojaPruebas> hojaPruebasList) {
	 * this.hojaPruebasList = hojaPruebasList; }
	 * 
	 * public List<Prueba> getPruebasList() { return pruebasList; }
	 * 
	 * public void setPruebasList(List<Prueba> pruebasList) { this.pruebasList =
	 * pruebasList; }
	 */

	public byte[] getFirmaUsuario() {
		return firmaUsuario;
	}

	public void setFirmaUsuario(byte[] firmaUsuario) {
		this.firmaUsuario = firmaUsuario;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (usuario != null ? usuario.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		return !((this.usuario == null && other.usuario != null)
				|| (this.usuario != null && !this.usuario.equals(other.usuario)));
	}

	@Override
	public String toString() {
		return nombre;
	}
}
