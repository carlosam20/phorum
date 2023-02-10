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
public class Valoracion {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuariosValoraciones")
    private Usuario usuario;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Post.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "postValoraciones")
    private Post post;
    
    //LIKE = true, DISLIKE = false
    private boolean valor;
	
	
	public Valoracion() {
		
	}


	


	public Valoracion(Long id, Usuario usuario, Post post, boolean valor) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.post = post;
		this.valor = valor;
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
