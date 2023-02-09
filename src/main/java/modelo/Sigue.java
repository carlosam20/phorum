package modelo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Sigue {
	
@Id
@GeneratedValue(strategy = IDENTITY)
private Long id;

@ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
@JoinColumn(name = "forosSeguidos")
private Usuario usuario;

@ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Foro.class, optional = false, fetch = FetchType.EAGER)
@JoinColumn(name = "seguidores")
private Foro foro;
}
