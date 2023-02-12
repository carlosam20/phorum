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
	
    
    @Transient
    @NumberFormat
    private long idPost;

    @Transient
    @NumberFormat
    private long idUsuario;
	
	public Valoracion() {
		
	}


	public Valoracion(Long id, Usuario usuario, Post post, boolean valor, long idPost, long idUsuario) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.post = post;
		this.valor = valor;
		this.idPost = idPost;
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


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
	}


	public boolean isValor() {
		return valor;
	}


	public void setValor(boolean valor) {
		this.valor = valor;
	}


	public long getIdPost() {
		return idPost;
	}


	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}


	public long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}


	

}
