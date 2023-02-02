package serviciosWEB.identificado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Post;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;

@Controller("servicioWebPostsIdentificado")
@RequestMapping("identificado/servicioWebPosts/")
public class ServicioWebPosts {

	@Autowired
	private ServicioPosts servicioPosts;

	@Autowired
	private ServicioForos servicioForos;

	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@Autowired
	private ServicioComentarios servicioComentarios;


	@RequestMapping("registrarPosts")
	public ResponseEntity<String> registrarPosts(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, String idForo, String idUsuario,
			HttpServletRequest request) {
		String respuesta = "";
		System.out.println("--------" + formData);
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		System.out.println("--------" + json);

		Post p = gson.fromJson(json, Post.class);

		System.out.println("idForo" + idForo);
		System.out.println("idUsuario" + idUsuario);

		p.setIdForo(Long.parseLong(idForo));
		p.setIdUsuario(Long.parseLong(idUsuario));
		p.setForo(servicioForos.obtenerForosPorId(p.getIdForo()));
		p.setUsuario(servicioUsuarios.obtenerUsuario(p.getIdForo()));

		System.out.println("post ForoId:  " + p.getForo());

		System.out.println(p.toString());

		servicioPosts.registrarPost(p);

		// tras hacer un registro con hibernate, hibernate asigna a este usuario la id
		// del
		// registro en la tabla de la base de datos

		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenPost(p, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}
	


	@RequestMapping("obtenerPostYComentariosPorId")
	public ResponseEntity<String> obtenerPostYComentariosPorId(String idPost, HttpServletRequest request) {

		String jsonPosts = new Gson().toJson(servicioPosts.obtenerPostsPorId(Long.parseLong(idPost)));

		List<Map<String, Object>> comentariosResults = servicioComentarios
				.obtenerComentariosPost(Long.parseLong(idPost));
				

		//Eliminamos todos los datos del usuario que no nos interesen
		Set<String> keySetsAEliminarUsuario = new HashSet<> ();
		keySetsAEliminarUsuario.add("pass");
		keySetsAEliminarUsuario.add("email");
		keySetsAEliminarUsuario.add("descripcion");
		keySetsAEliminarUsuario.add("fechaCreacion");
		
	
		//Añadimos los datos del usuario en concreto al comentario realizado por el mismo
		for (int i = 0; i < comentariosResults.size(); i++) {
		  Map<String, Object> usuarioComentario = servicioUsuarios.obtenerUsuarioPorId(Long.parseLong(String.valueOf(comentariosResults.get(0).get("usuario"))));
		  usuarioComentario.keySet().removeAll(keySetsAEliminarUsuario);
		  comentariosResults.get(i).put("idUsuario",usuarioComentario.get("id"));
		  comentariosResults.get(i).put("nombreUsuario",usuarioComentario.get("nombre"));
		  
		  
		}
		
		for (int i = 0; i < comentariosResults.size(); i++) {
			comentariosResults.get(i).keySet().remove("usuario");
			comentariosResults.get(i).put("fechaCreacionComentario",comentariosResults.get(i).get("fechaCreacion"));
			comentariosResults.get(i).keySet().remove("fechaCreacion");
			comentariosResults.get(i).put("idComentario",comentariosResults.get(i).get("id"));
			comentariosResults.get(i).keySet().remove("id");
		}
		
		
		String jsonComentarios = new Gson().toJson(comentariosResults);
			
		// Recogemos las keys del map
//		Set<String> keysComentarios = comentariosResults.get(0).keySet();


		// Las parseamos a iterador para poder remplazarlas
//		Iterator<String> keysValuesComentarios = keysComentarios.iterator();

		

		// Recogemos las keys de la busqueda 
//		while (keysValuesComentarios.hasNext()) {
//			String key = keysValuesComentarios.next();
//		
//
//		}
//		
		jsonPosts = jsonPosts.replaceAll("}]", ",");
		jsonComentarios = jsonComentarios.replaceAll(Pattern.quote("[{"), "");
		
		System.out.println("JSON jsonComentarios: "+jsonComentarios);
		System.out.println("JSON jsonPosts: "+jsonPosts);
		
		jsonPosts = jsonPosts + jsonComentarios;
		
		
		return new ResponseEntity<String>(jsonPosts, HttpStatus.OK);
	}
}
