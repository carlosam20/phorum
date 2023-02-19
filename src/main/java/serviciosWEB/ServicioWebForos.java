package serviciosWEB;

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

import modelo.Foro;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import utilidadesArchivos.GestorArchivos;

@Controller
@RequestMapping("servicioWebForos/")
public class ServicioWebForos {

	@Autowired
	private ServicioForos servicioForos;

	@Autowired
	private ServicioPosts servicioPosts;

	@RequestMapping("obtenerForosDeNombreIntroducido")
	public ResponseEntity<String> obtenerForosDeNombreIntroducido(String nombreForo, HttpServletRequest request) {
		String json = new Gson().toJson(servicioForos.obtenerForosParaListadoBusquedaForo(nombreForo));
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping("obtenerForos")
	public ResponseEntity<String> obtenerForos() {
		List<Map<String, Object>> foros = servicioForos.obtenerForosParaListado();
		String json = new Gson().toJson(foros);
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping("obtenerForosYPosts")
	public ResponseEntity<String> obtenerForosYPosts() {

		List<Map<String, Object>> forosResults = servicioForos.obtenerForosParaListadoAleatorios();

		String jsonForos = new Gson().toJson(forosResults);

		List<Map<String, Object>> postsResults = servicioPosts.obtenerPostsParaListadoAleatorio();
		String jsonPosts = new Gson().toJson(postsResults);

		if (postsResults.size() != 0) {
			// Recogemos las keys del map
			Set<String> keys = postsResults.get(0).keySet();

			// Las parseamos a iterdor para poder remplazarlas
			Iterator<String> keysValues = keys.iterator();

			// Anyadimos a las keys el "Post" para diferenciarlas
			while (keysValues.hasNext()) {
				String key = keysValues.next();
				jsonPosts = jsonPosts.replaceAll(key, key + "Post");
			}

			// Cambiamos el cierre de jsonForos por una coma, para unirlo a jsonPosts,
			// Adem�s de eliminar la abertura de jsonPost
			jsonForos = jsonForos.replaceAll("}]", ",");
			jsonPosts = jsonPosts.replaceAll(Pattern.quote("[{"), "");

			jsonForos = jsonForos + jsonPosts;
			return new ResponseEntity<String>(jsonForos, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(jsonForos, HttpStatus.OK);
		}

	}

	@RequestMapping("registrarForo")
	public ResponseEntity<String> registrarForo(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {
		String respuesta = "";
		System.out.println("--------" + formData);

		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);

		System.out.println("--------" + json);
		Foro f = gson.fromJson(json, Foro.class);
		System.out.println("foro a registrar: " + f.toString());
		servicioForos.registrarForo(f);

		// tras hacer un registro con hibernate, hibernate asigna a este usuario la id
		// del
		// registro en la tabla de la base de datos
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenForo(f, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
