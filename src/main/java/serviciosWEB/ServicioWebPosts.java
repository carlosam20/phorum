package serviciosWEB;

import java.util.ArrayList;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import modelo.Post;
import parseo.FechaParaUsuario;
import servicios.ServicioForos;
import servicios.ServicioPosts;

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

		if (idsPostsTop3.isEmpty() || idsPostsTop3.equals(null)) {

			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		for (int i = 0; i < idsPostsTop3.size(); i++) {

			Post post = servicioPosts.obtenerPostPorId(Long.parseLong(String.valueOf(idsPostsTop3.get(i).get("post"))));
			Map<String, Object> postMap = new HashMap<String, Object>();
			postMap.put("id", post.getId());
			postMap.put("nombre", post.getNombre());
			infoPostsTop3.add(postMap);

		}
		
		if (infoPostsTop3.isEmpty() || infoPostsTop3.equals(null)) {

			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
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

		List<Map<String, Object>> postsForo = servicioPosts.obtenerPostPorForoId(Long.parseLong(id));
		Map<String, Object> foroInfo = servicioForos.obtenerForoPorIdEnMap(Long.parseLong(id));


		if (foroInfo.isEmpty() || foroInfo.equals(null)) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		for (int i = 0; i < postsForo.size(); i++) {
			postsForo.get(i).put("nombreForo", foroInfo.get("nombre"));
		}

		// Formateo de fecha al String parseado de fecha
		Date fechaPost = (Date) foroInfo.get("fechaCreacion");
		foroInfo.put("fechaCreacion", FechaParaUsuario.parseoDeFecha(fechaPost));

		JsonObject json = new JsonObject();
		json.add("posts", new Gson().toJsonTree(postsForo));
		json.add("foro", new Gson().toJsonTree(foroInfo));

		String datos = json.toString();

	
		return new ResponseEntity<String>(datos, HttpStatus.OK);
	}
}
