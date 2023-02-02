package serviciosWEB.identificado;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;

@Controller("servicioWebUsuariosIdentificado")
@RequestMapping("identificado/servicioWebUsuarios/")

public class ServicioWebUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;
	
	@Autowired
	private ServicioComentarios servicioComentarios;
	
	@Autowired
	private ServicioPosts servicioPosts;
	
	@Autowired
	private ServicioForos servicioForos;
	

	@RequestMapping("obtenerUsuarioPorId")
	public ResponseEntity<String> obtenerUsuarioPorId(HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		System.out.println("----El id----" + u.getId());
		String json = new Gson().toJson(servicioUsuarios.obtenerUsuarioPorId(u.getId()));
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping("editarUsuarioPorId")
	public ResponseEntity<String> editarUsuarioPorId(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		String respuesta = "";
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);

		Usuario nu = gson.fromJson(json, Usuario.class);
		nu.setId((u.getId()));
		System.out.println("usuario a editar: " + nu.toString());
		servicioUsuarios.guardarCambiosUsuario(nu);
		// tras hacer un registro con hibernate, hibernate asigna a este usuario la id
		// del
		// registro en la tabla de la base de datos
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarFotoUsuario(nu, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("borrarUsuarioPorId")
	public ResponseEntity<String> borrarUsuarioPorId(HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		String respuesta = "";
		
		
		
		//Hay que eliminar todos sus comentarios
		servicioComentarios.borrarComentariosPorIdUsuario(u.getId());
		//Hay que eliminar todos sus post
		servicioPosts.eliminarPostUsuarios(u.getId());
		//Hay que eliminar todos los foros
		
		//El problema es que si borro el usuario y no cambio el usuario del foro hay que borrar el foro
		//Si no no me deja cerrarlo la verdad es una putada
		
		servicioUsuarios.eliminarUsuario(u.getId());
		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("comprobarIdentificacion")
	public ResponseEntity<String> comprobarIdentificacion(HttpServletRequest request) {
		String respuesta = "";
		if (request.getSession().getAttribute("usuario") != null) {
			respuesta = "ok," + ((Usuario) request.getSession().getAttribute("usuario")).getNombre();
		} else {
			respuesta = "no identificado";
		}
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
