package modelo;


import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
   
    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z 0-9]{3,60}$", message="Los nombres solo admiten letras, numeros y espacios")
    private String nombre;
   
    @Pattern(regexp ="/^[\\w\\s\\d,.<>áéíóúÁÉÍÓÚñÑüÜ]{1,300}+$/u\n"
    		+ "", message="La descripción no es correcta:"
    				+ "Se pueden introducir guiones bajos, espacios en blanco, numeros, acentos y mayúsculas y minúsculas")
    @Length(min=1, max=300)
    @Column(length=300)
    private String descripcion;
    
	@NotBlank(message = "Se requiere una fecha para el foro")
	@Pattern(regexp = "/^([1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/\\d{4}$/\n"
			+ "" + "", message = "La fecha de creación no es correcta:"
			+ "Se tiene que introducir en formato dd/mm/yyyy")
	@Length(min=10)
	@Column(length=10)
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
   
    @OneToMany (cascade = {CascadeType.MERGE},mappedBy="post")
    private  List<Valoracion> postValoraciones = new ArrayList<Valoracion>();

	public Post() {
    	
    }


	public Post(Long id, String nombre, String descripcion, String fechaCreacion, Foro foro, MultipartFile imagen,
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


	public List<Valoracion> getPostValoraciones() {
		return postValoraciones;
	}


	public void setPostValoraciones(List<Valoracion> postValoraciones) {
		this.postValoraciones = postValoraciones;
	}


	

}
