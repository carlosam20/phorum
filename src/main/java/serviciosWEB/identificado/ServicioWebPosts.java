package serviciosWEB.identificado;

import java.util.Date;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelo.Post;
import modelo.Usuario;
import parseo.FechaParaUsuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;
import utilidadesArchivos.GestorArchivos;
import validacionObjetos.ParValidacion;
import validaciones.ValidacionesImpl;

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
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");


		
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		

		Post p = gson.fromJson(json, Post.class);

		p.setIdForo(p.getIdForo());
		p.setIdUsuario(u.getId());
		p.setForo(servicioForos.obtenerForosPorId(p.getIdForo()));
		p.setUsuario(servicioUsuarios.obtenerUsuario(p.getIdForo()));

		LocalDate currentDate = LocalDate.now();
		Date formattedDate = currentDate.toDate();
		p.setFechaCreacion(formattedDate);

		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		BeanPropertyBindingResult bp = new BeanPropertyBindingResult(p, "post");
		ParValidacion resultadoValidacion = ValidacionesImpl.validarPost(p, bp, foto);

		if (resultadoValidacion.getResultado() == true) {
			servicioPosts.registrarPost(p);
			//hibernate asigna el id

			GestorArchivos.guardarImagenPost(p, rutaRealDelProyecto, foto);
		
			return new ResponseEntity<String>(resultadoValidacion.getRespuesta(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(resultadoValidacion.getRespuesta(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping("obtenerPostYComentariosPorId")
	public ResponseEntity<String> obtenerPostYComentariosPorId(String idPost, HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		Map<String, Object> postResult = servicioPosts.obtenerPostPorIdEnMap(Long.parseLong(idPost));
		List<Map<String, Object>> comentariosResults = servicioComentarios
				.obtenerComentariosPost(Long.parseLong(idPost));

		int like = servicioValoraciones.obtenerTotalValoracionesPostLike(Long.parseLong(idPost));
		int dislike = servicioValoraciones.obtenerTotalValoracionesPostDislike(Long.parseLong(idPost));

		Map<String, Object> valoracionUsuarioSesion = servicioValoraciones.obtenerValoracionPorPostIdYPorUsuarioId(
				Long.parseLong(String.valueOf(u.getId())), Long.parseLong(idPost));

		boolean hayValor[] = servicioValoraciones.comprobarExisteValoracion(Long.parseLong(idPost), u.getId());

		// Eliminamos todos los datos del usuario que no nos interesen

		Set<String> keySetsAEliminarUsuario = new HashSet<>();
		keySetsAEliminarUsuario.add("pass");
		keySetsAEliminarUsuario.add("email");
		keySetsAEliminarUsuario.add("descripcion");
		keySetsAEliminarUsuario.add("fechaCreacion");

		// Añadimos los datos al post
		postResult.put("totalComentarios", comentariosResults.size());
		Map<String, Object> usuario = servicioUsuarios.obtenerUsuarioPorId(Long.parseLong(String.valueOf(u.getId())));
		postResult.put("idUsuario", usuario.get("id"));
		postResult.put("usuario", usuario.get("nombre"));
		Date fechaPost = (Date) postResult.get("fechaCreacion");
		postResult.put("fechaCreacion", FechaParaUsuario.parseoDeFecha(fechaPost));

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

			Date fechaComentarioUsuario = (Date) comentariosResults.get(i).get("fechaCreacion");
			comentariosResults.get(i).put("fechaCreacionComentario",
					FechaParaUsuario.parseoDeFecha(fechaComentarioUsuario));

			comentariosResults.get(i).keySet().remove("usuario");

			comentariosResults.get(i).keySet().remove("fechaCreacion");
			comentariosResults.get(i).put("idComentario", comentariosResults.get(i).get("id"));
			comentariosResults.get(i).keySet().remove("id");
		}

		// Eliminamos datos de la valoracion del usuario
		if (hayValor[0]) {
			valoracionUsuarioSesion.remove("id");
			valoracionUsuarioSesion.remove("post");
		}

		if (comentariosResults.size() != 0) {
			JsonObject valoracionesPostResults = new JsonObject();
			JsonObject valoracionUsuario = new JsonObject();

			valoracionesPostResults.addProperty("like", like);
			valoracionesPostResults.addProperty("dislike", dislike);

			if (hayValor[0]) {
				valoracionUsuario.addProperty("idUsuarioSesion",
						String.valueOf(valoracionUsuarioSesion.get("usuario")));
				valoracionUsuario.addProperty("valorUsuarioSesion",
						String.valueOf(valoracionUsuarioSesion.get("valor")));
			}

			// Combinamos las consultas modificadas en un objeto json
			JsonObject combinacionDatos = new JsonObject();
			combinacionDatos.add("post", new Gson().toJsonTree(postResult));
			combinacionDatos.add("post_valoraciones", new Gson().toJsonTree(valoracionesPostResults));
			combinacionDatos.add("post_comentarios", new Gson().toJsonTree(comentariosResults));
			combinacionDatos.add("valoracion_usuario_sesion", valoracionUsuario);

			// Pasamos el JSON en forma de string
			String jsonPostComentarios = combinacionDatos.toString();
			

			return new ResponseEntity<String>(jsonPostComentarios, HttpStatus.OK);
		} else {

			JsonObject valoracionUsuario = new JsonObject();
			JsonObject valoracionesPostResults = new JsonObject();

			if (hayValor[0]) {
				valoracionUsuario.addProperty("idUsuarioSesion",
						String.valueOf(valoracionUsuarioSesion.get("usuario")));
				valoracionUsuario.addProperty("valorUsuarioSesion",
						String.valueOf(valoracionUsuarioSesion.get("valor")));
			}

			valoracionesPostResults.addProperty("like", like);
			valoracionesPostResults.addProperty("dislike", dislike);

			// Hay que convertir el postResults en un JsonObject para incluirlo a un
			// JsonTree
			JsonObject combinacionDatos = new JsonObject();
			combinacionDatos.add("post", new Gson().toJsonTree(postResult));
			combinacionDatos.add("post_valoraciones", new Gson().toJsonTree(valoracionesPostResults));
			combinacionDatos.add("valoracion_usuario_sesion", valoracionUsuario);

			String jsonPostComentarios = combinacionDatos.toString();
			

			return new ResponseEntity<String>(jsonPostComentarios, HttpStatus.OK);
		}

	}

}
