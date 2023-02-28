package modelo;

import org.springframework.web.multipart.MultipartFile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Foro {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank(message = "Se requiere un nombre para el foro") // No puede ser ni nulo, ni tener longitud menor a 0
	@Column(length = 60) // Tamaño de la columna en la base de datos
	@Length(min = 1, max = 60) // Rango de tamaño que tiene
	@Pattern(regexp = "^[a-zA-Z 0-9]{3,60}$", message="Los nombres solo admiten letras, numeros y espacios")
	private String nombre;

	@NotBlank(message = "Se requiere una descripción para el foro") // No puede ser ni nulo, ni tener longitud menor a 0
	@Pattern(regexp = "/^[\\w\\s\\d,.áéíóúÁÉÍÓÚñÑüÜ]{1,300}+$/u\n" + "", message = "La descripción no es correcta:"
			+ "Se pueden introducir guiones bajos, espacios en blanco, numeros, acentos y mayúsculas y minúsculas")
	@Column(length = 200)
	@Length(min = 1, max = 300) // Rango de tamaño que tiene
	private String descripcion;

	@OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "foro")
	private List<Post> posts = new ArrayList<Post>();

	@OneToMany(cascade = { CascadeType.MERGE }, mappedBy = "foro")
	private List<Follow> seguidores;

	@NotBlank(message = "Se requiere una fecha para el foro")
	@Pattern(regexp = "/^([1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/\\d{4}$/\n"
			+ "" + "", message = "La fecha de creación no es correcta:"
			+ "Se tiene que introducir en formato dd/mm/yyyy")
	@Length(min=10)
	@Column(length=10)
	private String fechaCreacion;

	@Transient
	private MultipartFile imagen;

	public Foro() {
	}

	public Foro(Long id, String nombre, String descripcion, List<Post> posts, String fechaCreacion,
			MultipartFile imagen, List<Follow> seguidores) {
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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
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
