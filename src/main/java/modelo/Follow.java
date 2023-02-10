package modelo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;

@Entity
public class Follow {
	
@Id
@GeneratedValue(strategy = IDENTITY)
private Long id;

@ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
@JoinColumn(name = "usuario")
private Usuario usuario;

@ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Foro.class, optional = false, fetch = FetchType.EAGER)
@JoinColumn(name = "foro")
private Foro foro;

@Transient
@NumberFormat
private long idForo;

@Transient
@NumberFormat
private long idUsuario;

public Follow() {
	// TODO Auto-generated constructor stub
}





public Follow(Long id, Usuario usuario, Foro foro, long idForo, long idUsuario) {
	super();
	this.id = id;
	this.usuario = usuario;
	this.foro = foro;
	this.idForo = idForo;
	this.idUsuario = idUsuario;
}





public Long getId() {
	return id;
}



public void setId(Long id) {
	this.id = id;
}



public Usuario getUsuario() {
	return usuario;
}



public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}



public Foro getForo() {
	return foro;
}



public void setForo(Foro foro) {
	this.foro = foro;
}





public long getIdForo() {
	return idForo;
}





public void setIdForo(long idForo) {
	this.idForo = idForo;
}





public long getIdUsuario() {
	return idUsuario;
}





public void setIdUsuario(long idUsuario) {
	this.idUsuario = idUsuario;
}




}
