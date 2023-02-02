package serviciosWEB.identificado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

	
	
	@Controller
	public class YourController {

	  @RequestMapping("/registrarPosts")
	  public ResponseEntity<Map<String, Object>> registrarPosts(@RequestParam Map<String, Object> formData,
				@RequestParam("foto") CommonsMultipartFile foto, String idForo, String idUsuario,
				HttpServletRequest request) {
	    String respuesta = "";
	    String url = "https://example.com";
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

	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("respuesta", respuesta);
	    responseData.put("url", url);

	    return new ResponseEntity<Map<String, Object>>(responseData, HttpStatus.OK);
	  }
	}


	@RequestMapping("obtenerPostYComentariosPorId")
	public ResponseEntity<String> obtenerPostYComentariosPorId(String idPost, HttpServletRequest request) {

		String jsonPosts = new Gson().toJson(servicioPosts.obtenerPostsPorId(Long.parseLong(idPost)));

		List<Map<String, Object>> comentariosResults = servicioComentarios
				.obtenerComentariosPost(Long.parseLong(idPost));
		String jsonComentarios = new Gson().toJson(comentariosResults);

		// Obtengo los datos de cada comentario de los usuarios
		List<Map<String, Object>> usuariosComentariosResults = new ArrayList<>();
		
		for (int i = 0; i < comentariosResults.size(); i++) {
		  Map<String, Object> usuarioComentario = servicioUsuarios.obtenerUsuarioPorId(Long.parseLong(String.valueOf(comentariosResults.get(0).get("usuario"))));
		  usuariosComentariosResults.add(usuarioComentario);
		}
		
		String jsonUsuariosComentarios = new Gson().toJson(usuariosComentariosResults);
		
		
		// Recogemos las keys del map
		Set<String> keysComentarios = comentariosResults.get(0).keySet();
		Set<String>keysUsuariosComentarios = usuariosComentariosResults.get(0).keySet();

		// Las parseamos a iterdor para poder remplazarlas
		Iterator<String> keysValuesComentarios = keysComentarios.iterator();
		Iterator<String> keysValuesUsuariosComentarios= keysUsuariosComentarios.iterator();

		// Recogemos las keys de la busqueda y las añadimos Comentario a las que no lo
		// tengan
		
		while (keysValuesComentarios.hasNext()) {
			String key = keysValuesComentarios.next();
			if (!key.contains("Comentario")) {
				jsonComentarios = jsonComentarios.replace(key, key + "Comentario");
			}
			// Cambiamos el cierre de jsonForos por una coma, para unirlo a jsonPosts,
			// Ademas de eliminar la abertura de jsonPost
			jsonPosts = jsonPosts.replaceAll("}]", ",");
			jsonComentarios = jsonComentarios.replaceAll(Pattern.quote("[{"), "");
			jsonComentarios = jsonComentarios.replaceAll("}]", ",");
			
		}
		System.out.println("JSON jsonComentarios: "+jsonComentarios);
		
		while (keysValuesUsuariosComentarios.hasNext()) {
			String key = keysValuesUsuariosComentarios.next();
			jsonUsuariosComentarios = jsonUsuariosComentarios.replace(key, key + "Usuario");
			jsonUsuariosComentarios = jsonUsuariosComentarios.replaceAll(Pattern.quote("[{"), "");
			
		}
		
		System.out.println("JSON jsonUsuariosComentarios"+ jsonUsuariosComentarios);
		
		jsonPosts = jsonPosts + jsonComentarios +jsonUsuariosComentarios;
		
		
		return new ResponseEntity<String>(jsonPosts, HttpStatus.OK);
	}
}
