package serviciosWEB;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import servicios.ServicioForos;
import servicios.ServicioPosts;


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

}
