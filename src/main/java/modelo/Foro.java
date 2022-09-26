package modelo;



import org.springframework.web.multipart.MultipartFile;


import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Foro {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Se requiere un nombre para el foro")
    private String nombre;
    @NotBlank(message = "Se requiere una descripción")
    private String descripcion;
    @OneToMany(cascade = {CascadeType.MERGE},mappedBy="foro", fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<Post>();

    private String fechaCreacion;

    @Transient
    private MultipartFile imagen;
    
    

    

 
 

    public Foro() {
    }

    

    public Foro(Long id, String nombre, String descripcion, List<Post> posts, String fechaCreacion,
			MultipartFile imagen, List<Usuario> usuarios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.posts = posts;
		this.fechaCreacion = fechaCreacion;
		this.imagen = imagen;
		
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
}
