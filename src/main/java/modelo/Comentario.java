package modelo;



import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotEmpty
    private String textoComentario;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Post.class, optional = false, fetch = FetchType.EAGER)
    private Post postComentario;
    private String fechaCreacion;



    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;


    public Comentario() {
    }

   
    public Comentario(Long id, String textoComentario, Post postComentario, String fechaCreacion, Usuario usuario) {
		super();
		this.id = id;
		this.textoComentario = textoComentario;
		this.postComentario = postComentario;
		this.fechaCreacion = fechaCreacion;
		this.usuario = usuario;
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


	public String getTextoComentario() {
        return textoComentario;
    }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
    }

    public Post getPostComentario() {
        return postComentario;
    }

    public void setPostComentario(Post postComentario) {
        this.postComentario = postComentario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

   
}
