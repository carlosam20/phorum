package serviciosWEB.identificado;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import parseo.FechaParaUsuario;
import servicios.ServicioComentarios;
import servicios.ServicioFollow;
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
	private ServicioFollow servicioFollow;
	

	@RequestMapping("obtenerUsuarioPorId")
	public ResponseEntity<String> obtenerUsuarioPorId(HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		int numeroComentarios = servicioComentarios.obtenerTotalDeComentariosDeUsuario(Long.parseLong(String.valueOf(u.getId())));
		int numeroPost = servicioComentarios.obtenerTotalDeComentariosDeUsuario(Long.parseLong(String.valueOf(u.getId())));
		int numeroForosSeguidos = servicioFollow.obtenerTotalDeFollowsDeUsuario(Long.parseLong(String.valueOf(u.getId())));
		
		Map<String,Object> usuario = servicioUsuarios.obtenerUsuarioPorId(u.getId());
		
		usuario.put("numeroComentarios", numeroComentarios);
		usuario.put("numeroPost", numeroPost);
		usuario.put("numeroForosSeguidos", numeroForosSeguidos);
		
	
		//Formateo de fecha al String parseado de fecha
		Date fechaUsuario= (Date) usuario.get("fechaCreacion");
		usuario.put("fechaCreacion", FechaParaUsuario.parseoDeFecha(fechaUsuario));
	
		
		String json = new Gson().toJson(usuario);
		System.out.println("json: "+json);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping("obtenerUsuarioComentarioPorId")
	public ResponseEntity<String> obtenerUsuarioComentarioPorId(String idUsuarioComentario){
		
		int numeroComentarios = servicioComentarios.obtenerTotalDeComentariosDeUsuario(Long.parseLong(idUsuarioComentario));
		int numeroPost = servicioComentarios.obtenerTotalDeComentariosDeUsuario(Long.parseLong(String.valueOf(idUsuarioComentario)));
		
		Map<String,Object> usuario = servicioUsuarios.obtenerUsuarioPorId(Long.parseLong(idUsuarioComentario));
		
		usuario.put("numeroComentarios", numeroComentarios);
		usuario.put("numeroPost", numeroPost);		
	
		
		String json = new Gson().toJson(usuario);
		System.out.println("json: "+json);
		
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
		//Eliminamos la hora del guardado de fecha
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(nu.getFechaCreacion());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    nu.setFechaCreacion(calendar.getTime());
	    
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
		
		//Eliminar comentarios de post del usuario
		
		List<Map<String, Object>> postHechosPorUsuario = servicioPosts.obtenerPostsPorIdUsuario(u.getId());
	
		for (int i = 0; i < postHechosPorUsuario.size(); i++) {
			System.out.println("Post Usuario:"+Long.parseLong(String.valueOf(postHechosPorUsuario.get(i).get("id"))));
			servicioComentarios.borrarComentariosPoridPost( Long.parseLong(String.valueOf(postHechosPorUsuario.get(i).get("id"))));
		}
		
		//Eliminar post hechos por el usuario
		servicioPosts.eliminarPostUsuarios(u.getId());
		servicioUsuarios.eliminarUsuario(u.getId());
		
		//Elimina la sesión del usuario
		request.getSession().invalidate();
		
		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("comprobarIdentificacion")
	public ResponseEntity<String> comprobarIdentificacion(HttpServletRequest request) {
		String respuesta = "";
		if (request.getSession().getAttribute("usuario") != null) {
			respuesta = "ok";
		} else {
			respuesta = "no identificado";
		}
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
