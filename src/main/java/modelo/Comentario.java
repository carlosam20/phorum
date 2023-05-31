package modelo;



import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;


@Entity
public class Comentario {
    @Id	
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotEmpty(message ="El texto del comentario no puede estar nulo o vacío")
    @Pattern(regexp ="^.{1,60}$"
    		+ "", message="")
    @Length(min=1, max=60)
    private String textoComentario;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Post.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "postComentario")
    private Post postComentario;
    
    @PastOrPresent(message ="La fecha tiene que ser presente o anterior")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private Usuario usuario;
    
    @Transient
    private long idPostComentario;
    
    @Transient
    private long idUsuario;


    public Comentario() {}

       
    public Comentario(Long id, String textoComentario, Post postComentario, Date fechaCreacion, Usuario usuario,
			long idPostComentario, long idUsuario) {
		super();
		this.id = id;
		this.textoComentario = textoComentario;
		this.postComentario = postComentario;
		this.fechaCreacion = fechaCreacion;
		this.usuario = usuario;
		this.idPostComentario = idPostComentario;
		this.idUsuario = idUsuario;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public long getIdPostComentario() {
		return idPostComentario;
	}


	public void setIdPostComentario(long idPostComentario) {
		this.idPostComentario = idPostComentario;
	}


	public long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}


	

   
}
