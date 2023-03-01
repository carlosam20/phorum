package modelo;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.*;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario  {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Introduce un nombre de usuario")
    @Pattern(regexp = "^.{1,60}$", message="Los nombres solo admiten letras, numeros y espacios")
    private String nombre;
    @NotBlank(message = "Introduce una contraseña")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,60}$")
    private String pass;
    @Email
    //https://stackoverflow.com/questions/1423195/what-is-the-actual-minimum-length-of-an-email-address-as-defined-by-the-ietf#:~:text=The%20shortest%20valid%20email%20address,two%20parts%3A%20name%20and%20domain.&text=Since%20both%20the%20name%20and,length%20resolves%20to%203%20characters.
    //https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
    @Pattern(regexp = "^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{3,254}+$", message="El email debe contener '@', '.' y al menos 10 caracteres")
    @Length(min=3, max=254)
    @Column(length = 254)
    @NotEmpty(message = "Introduce un email")	
    private String email;
    
	@NotBlank(message = "Se requiere una fecha")

	@DateTimeFormat("yyyy/MM/dd")
	private Date fechaCreacion;
    
    @Pattern(regexp ="^.{1,300}$"
    		+ "", message="La descripción no es correcta:"
    				+ "El tamaño no es el adecuado")
    @Length(min=1, max=300)
    private String descripcion;
    
    @OneToMany (cascade = {CascadeType.MERGE},mappedBy="usuario")
    private  List<Valoracion> usuariosValoraciones = new ArrayList<Valoracion>();
    
    
    @OneToMany (cascade = {CascadeType.MERGE},mappedBy="usuario")
    private  List<Follow> forosSeguidos = new ArrayList<Follow>();
    
    
    
   
    @Transient
    private MultipartFile imagen;


    public Usuario(){}


	public Usuario(Long id, String nombre, String pass, String email, String fechaCreacion, String descripcion,
			List<Valoracion> usuariosValoraciones, List<Follow> forosSeguidos, MultipartFile imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pass = pass;
		this.email = email;
		this.fechaCreacion = fechaCreacion;
		this.descripcion = descripcion;
		this.usuariosValoraciones = usuariosValoraciones;
		this.forosSeguidos = forosSeguidos;
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


	public MultipartFile getImagen() {
		return imagen;
	}




	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}



	public List<Valoracion> getUsuariosValoraciones() {
		return usuariosValoraciones;
	}



	public void setUsuariosValoraciones(List<Valoracion> usuariosValoraciones) {
		this.usuariosValoraciones = usuariosValoraciones;
	}



	public List<Follow> getForosSeguidos() {
		return forosSeguidos;
	}



	public void setForosSeguidos(List<Follow> forosSeguidos) {
		this.forosSeguidos = forosSeguidos;
	}

    

}
