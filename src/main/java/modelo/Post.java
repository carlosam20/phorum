package modelo;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotEmpty
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotEmpty
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;
    private String fechaCreacion;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Foro.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "foro")
    private Foro foro;

    @Transient
    private MultipartFile imagen;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Usuario.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    
    @Transient
    private long idForo;

    @Transient
    private long idUsuario;


   
    private long likes;

   


	public Post() {
    	
    }


	public Post(Long id, String nombre, String descripcion, String fechaCreacion, Foro foro, MultipartFile imagen,
			Usuario usuario, long idForo, long idUsuario, long likes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.foro = foro;
		this.imagen = imagen;
		this.usuario = usuario;
		this.idForo = idForo;
		this.idUsuario = idUsuario;
		this.likes = likes;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Foro getForo() {
		return foro;
	}


	public void setForo(Foro foro) {
		this.foro = foro;
	}


	public MultipartFile getImagen() {
		return imagen;
	}


	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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


	public long getLikes() {
		return likes;
	}


	public void setLikes(long likes) {
		this.likes = likes;
	}





}
