package serviciosWEB.identificado;

import java.util.HashSet;
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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelo.Post;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;
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
	private ServicioValoracion servicioValoraciones;

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
		
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		List<Map<String, Object>> postResults = servicioPosts.obtenerPostsPorId(Long.parseLong(idPost));

		List<Map<String, Object>> comentariosResults = servicioComentarios
				.obtenerComentariosPost(Long.parseLong(idPost));

		int like = servicioValoraciones.obtenerTotalValoracionesPostLike(Long.parseLong(idPost));
		int dislike = servicioValoraciones.obtenerTotalValoracionesPostDislike(Long.parseLong(idPost));
		
		Map<String, Object> valoracionUsuarioSesion = servicioValoraciones.obtenerValoracionPorPostIdYPorUsuarioId(Long.parseLong(  String.valueOf(u.getId())) , Long.parseLong(idPost));
		
		//Necesito obtener la valoracion del post con el usuario actual

		// Eliminamos todos los datos del usuario que no nos interesen

		Set<String> keySetsAEliminarUsuario = new HashSet<>();
		keySetsAEliminarUsuario.add("pass");
		keySetsAEliminarUsuario.add("email");
		keySetsAEliminarUsuario.add("descripcion");
		keySetsAEliminarUsuario.add("fechaCreacion");

		// Cambiar datos del post
		for (int i = 0; i < postResults.size(); i++) {
			Map<String, Object> usuarioPost = servicioUsuarios
					.obtenerUsuarioPorId(Long.parseLong(String.valueOf(postResults.get(i).get("usuario"))));
			postResults.get(i).put("idUsuarioPost", postResults.get(i).get("usuario"));
			postResults.get(i).remove("usuario");
			postResults.get(i).put("nombreUsuarioPost", usuarioPost.get("nombre"));
			postResults.get(i).put("totalComentarios", comentariosResults.size());

		}

	
		//TODO revisar

		// Añadimos los datos del usuario en concreto al comentario realizado por él
		// mismo
		for (int i = 0; i < comentariosResults.size(); i++) {
			Map<String, Object> usuarioComentario = servicioUsuarios
					.obtenerUsuarioPorId(Long.parseLong(String.valueOf(comentariosResults.get(i).get("usuario"))));
			comentariosResults.get(i).put("idUsuario", usuarioComentario.get("id"));
			comentariosResults.get(i).put("nombreUsuario", usuarioComentario.get("nombre"));
			usuarioComentario.keySet().removeAll(keySetsAEliminarUsuario);
		}

		for (int i = 0; i < comentariosResults.size(); i++) {
			comentariosResults.get(i).keySet().remove("usuario");
			comentariosResults.get(i).put("fechaCreacionComentario", comentariosResults.get(i).get("fechaCreacion"));
			comentariosResults.get(i).keySet().remove("fechaCreacion");
			comentariosResults.get(i).put("idComentario", comentariosResults.get(i).get("id"));
			comentariosResults.get(i).keySet().remove("id");
		}
		
		
			valoracionUsuarioSesion.remove("id");
			valoracionUsuarioSesion.remove("post");
			
			
		//TODO contemplar que no haya valoraciones previas hechas por el usuario

		if (comentariosResults.size() != 0) {

			JsonObject valoracionesPostResults = new JsonObject();
			JsonObject valoracionUsuario = new JsonObject();

			valoracionesPostResults.addProperty("like", like);
			valoracionesPostResults.addProperty("dislike", dislike);
			
			valoracionUsuario.addProperty("idUsuarioSesion", String.valueOf( valoracionUsuarioSesion.get("usuario")));
			valoracionUsuario.addProperty("valorUsuarioSesion", String.valueOf( valoracionUsuarioSesion.get("valor")));

			// Combinamos las consultas modificadas en un objeto json
			JsonObject combinacionDatos = new JsonObject();
			combinacionDatos.add("post", new Gson().toJsonTree(postResults));
			combinacionDatos.add("post_valoraciones", new Gson().toJsonTree(valoracionesPostResults));
			combinacionDatos.add("post_comentarios", new Gson().toJsonTree(comentariosResults));
			combinacionDatos.add("valoracion_usuario_sesion", valoracionUsuario);

			// Pasamos el JSON en forma de string
			String jsonPostComentarios = combinacionDatos.toString();
			System.out.println(jsonPostComentarios);

			return new ResponseEntity<String>(jsonPostComentarios, HttpStatus.OK);
		} else {

			JsonObject valoracionesPostResults = new JsonObject();

			valoracionesPostResults.addProperty("like", like);
			valoracionesPostResults.addProperty("dislike", dislike);

			JsonObject combinacionDatos = new JsonObject();
			combinacionDatos.add("post", new Gson().toJsonTree(postResults));
			combinacionDatos.add("post_valoraciones", new Gson().toJsonTree(valoracionesPostResults));
			
			String jsonPostComentarios = combinacionDatos.toString();
			return new ResponseEntity<String>(jsonPostComentarios, HttpStatus.OK);
		}

	}

}
