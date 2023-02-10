package modelo;



import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import enums.Valoracion;

@Entity
public class ValoracionesPostUsuario {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuariosValoraciones")
    private Usuario usuario;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Post.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "postValoraciones")
    private Post post;
    
    //es un enum que nos da los posibles valores que puede tener que son: LIKE, DISLIKE y NO_VALUE
    Valoracion valoracion;
	
	
	public ValoracionesPostUsuario() {
		
	}


	public ValoracionesPostUsuario(long id, Usuario usuario, Post post, Valoracion valoracion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.post = post;
		this.valoracion = valoracion;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
	}

	

}
