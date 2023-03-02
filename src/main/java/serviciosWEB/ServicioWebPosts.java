package serviciosWEB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelo.Post;
import modelo.Valoracion;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import utilidadesArchivos.GestorArchivos;

@Controller
@RequestMapping("servicioWebPosts/")
public class ServicioWebPosts {

	@Autowired
	private ServicioPosts servicioPosts;

	@Autowired
	private ServicioForos servicioForos;

	@RequestMapping("obtenerPosts")
	public ResponseEntity<String> obtenerPosts() {

		List<Map<String, Object>> idsPostsTop3 = servicioPosts.obtenerPostsConMasValoraciones();
		List<Map<String, Object>> infoPostsTop3 = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < idsPostsTop3.size(); i++) {
			
			Post post = servicioPosts.obtenerPostPorId(Long.parseLong(String.valueOf(idsPostsTop3.get(i).get("post"))));
			Map<String, Object> postMap = new HashMap<String, Object>();
			postMap.put("id", post.getId());
			postMap.put("nombre", post.getNombre());
			infoPostsTop3.add(postMap);
			

		}

		JsonObject combinacionDatos = new JsonObject();
		combinacionDatos.add("postsListado", new Gson().toJsonTree(servicioPosts.obtenerPostsParaListado()));
		combinacionDatos.add("postsTop3", new Gson().toJsonTree(infoPostsTop3));

		String json = combinacionDatos.toString();
		System.out.println(json);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping("obtenerPostPorForoId")
	public ResponseEntity<String> obtenerPost(String id, HttpServletRequest request) {
		List<Map<String, Object>> postsForo = servicioPosts.obtenerIdPostPorForoId(Long.parseLong(id));

		for (int i = 0; i < postsForo.size(); i++) {
			Map<String, Object> obtenerForo = servicioForos.obtenerForo(Long.parseLong(id));
			postsForo.get(i).put("nombreForo", obtenerForo.get("nombre"));
		}

		JsonObject json = new JsonObject();
		json.add("posts", new Gson().toJsonTree(postsForo));

		String datos = json.toString();

		return new ResponseEntity<String>(datos, HttpStatus.OK);
	}

	@RequestMapping("registrarPosts")
	public ResponseEntity<String> registrarPost(String idForo, String idUsuario,
			@RequestParam Map<String, Object> formData, @RequestParam("foto") CommonsMultipartFile foto,
			HttpServletRequest request) {
		String respuesta = "";
		System.out.println("--------" + formData);
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);

		System.out.println("--------" + json);
		Post p = gson.fromJson(json, Post.class);
		p.setIdForo(Long.parseLong(idForo));
		p.setIdUsuario(Long.parseLong(idUsuario));
		

		// Post Valoraciones
		List<Valoracion> postValoraciones = new ArrayList<Valoracion>();
		p.setPostValoraciones(postValoraciones);
		Date currentDate = Calendar.getInstance().getTime();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(currentDate);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
        
        p.setFechaCreacion(currentDate); 
		// tras hacer un registro con hibernate, hibernate asigna a este usuario la id
		// del
		// registro en la tabla de la base de datos
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenPost(p, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}
}
