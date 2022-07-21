package modelo;


import org.hibernate.validator.constraints.Email;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario  {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Introduce un nombre de usuario")
    @Pattern(regexp = "^[a-zA-Z 0-9]{3,60}$", message="Los nombres solo admiten letras, numeros y espacios")
    private String nombre;
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,60}$")
    private String pass = Arrays.toString(new byte[60]);
    @Email
    //https://stackoverflow.com/questions/1423195/what-is-the-actual-minimum-length-of-an-email-address-as-defined-by-the-ietf#:~:text=The%20shortest%20valid%20email%20address,two%20parts%3A%20name%20and%20domain.&text=Since%20both%20the%20name%20and,length%20resolves%20to%203%20characters.
    //https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
    @Pattern(regexp = "^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{3,254}+$", message="El email debe contener '@', '.' y al menos 10 caracteres")
    @NotEmpty(message = "Introduce un email")
    private String email;
    
    private String fechaCreacion;
    
    
    //@Pattern(regexp = "^[0-9a-z A-Z·ÈÌÛ˙¡…Õ”⁄]{3,60}$", message="Las descripciones solo admiten letras, numeros y espacios")
    //private String descripcion;
    
  
    private String descripcion;

    
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "usuario" , fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<Post>();
    
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "usuario" , fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<Comentario>();
    

    @Transient
    private MultipartFile imagen;


    public Usuario() {}




	public Usuario(Long id, String nombre, String pass, String email, String fechaCreacion, String descripcion,
			List<Post> posts, List<Comentario> comentarios, MultipartFile imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pass = pass;
		this.email = email;
		this.fechaCreacion = fechaCreacion;
		this.descripcion = descripcion;
		this.posts = posts;
		this.comentarios = comentarios;
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




	public String getPass() {
		return pass;
	}




	public void setPass(String pass) {
		this.pass = pass;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getFechaCreacion() {
		return fechaCreacion;
	}




	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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




	public List<Comentario> getComentarios() {
		return comentarios;
	}




	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}




	public MultipartFile getImagen() {
		return imagen;
	}




	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}





    

}
