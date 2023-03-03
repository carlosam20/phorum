package serviciosWEB;

import java.util.Calendar;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		List<Map<String, Object>> postsResults = servicioPosts.obtenerPostsParaListadoAleatorio();
		Gson gson = new Gson();

			//Añadimos el nombre del foro a los resultados de los posts
		if(postsResults.size() != 0 || !postsResults.isEmpty()) {
			for(int i =0 ; i < postsResults.size(); i++) {
				Map<String, Object> postForo = servicioForos.obtenerForoPorIdEnMap(Long.parseLong(String.valueOf(postsResults.get(i).get("foro"))));	
				postsResults.get(i).put("foroNombre", String.valueOf(postForo.get("nombre")));
			}
		}
		
		
		JsonArray forosArray = gson.toJsonTree(forosResults).getAsJsonArray();
		JsonArray postsArray = gson.toJsonTree(postsResults).getAsJsonArray();
		
		
		JsonObject combinacionDatos = new JsonObject();
		combinacionDatos.add("foros", forosArray);
		combinacionDatos.add("posts", postsArray);
		
		String json = combinacionDatos.toString();
		
		System.out.println(json);
		return new ResponseEntity<String>(json, HttpStatus.OK);
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
		
		//Eliminamos la hora del guardado de fecha
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(f.getFechaCreacion());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    f.setFechaCreacion(calendar.getTime());
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
