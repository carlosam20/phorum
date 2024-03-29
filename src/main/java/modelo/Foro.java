package modelo;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.multipart.MultipartFile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Foro {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank(message = "Se requiere un nombre para el foro") // No puede ser ni nulo, ni tener longitud menor a 0
	@Pattern(regexp = "^.{1,60}$", message = "Se admiten entre 1 y 60 caracteres en el nombre")
	private String nombre;

	@NotBlank(message = "Se requiere una descripci�n para el foro") // No puede ser ni nulo, ni tener longitud menor a 0
	@Pattern(regexp = "^.{1,300}$" + "", message = "Se admiten entre 1 y 300 caracteres en la descripci�n")
	@Length(min = 1, max = 300) // Rango de tama�o que tiene
	private String descripcion;

	@OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "foro")
	private List<Post> posts = new ArrayList<Post>();

	@OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "foro")
	private List<Follow> seguidores;

	@PastOrPresent (message = "La fecha tiene que ser actual o anterior")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@Transient
	private MultipartFile imagen;

	public Foro() {
	}

	public Foro(Long id, String nombre, String descripcion, List<Post> posts, Date fechaCreacion, MultipartFile imagen,
			List<Follow> seguidores) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.posts = posts;
		this.fechaCreacion = fechaCreacion;
		this.imagen = imagen;
		this.seguidores = seguidores;

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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public List<Follow> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Follow> segidores) {
		this.seguidores = segidores;
	}

}
