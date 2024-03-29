package modelo;


import org.springframework.format.annotation.DateTimeFormat;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
   
    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^.{1,60}$", message="Los nombres tienen un tama�o entre 1 y 60 caracteres")
    private String nombre;
   
    @NotBlank(message = "Se requiere una descripci�n para el foro")
    @Pattern(regexp ="^.{1,300}$")
    @Length(min=1, max=300)
    private String descripcion;
 
    @FutureOrPresent (message = "La fecha tiene que ser actual o posterior")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
   
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
   
    @OneToMany (cascade = {CascadeType.MERGE},mappedBy="post")
    private  List<Valoracion> postValoraciones = new ArrayList<Valoracion>();

	public Post() {
    	
    }


	public Post(Long id, String nombre, String descripcion, Date fechaCreacion, Foro foro, MultipartFile imagen,
			Usuario usuario, long idForo, long idUsuario, List<Valoracion> postValoraciones) {
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
		this.postValoraciones =postValoraciones;
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


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
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


	public List<Valoracion> getPostValoraciones() {
		return postValoraciones;
	}


	public void setPostValoraciones(List<Valoracion> postValoraciones) {
		this.postValoraciones = postValoraciones;
	}


	

}
