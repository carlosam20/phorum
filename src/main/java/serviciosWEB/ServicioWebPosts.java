package serviciosWEB;




import java.time.LocalDate;
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

import modelo.Post;

import servicios.ServicioPosts;
import utilidadesArchivos.GestorArchivos;




@Controller
@RequestMapping("servicioWebPosts/")
public class ServicioWebPosts {

	@Autowired
	private ServicioPosts servicioPosts;
	
	@RequestMapping("obtenerPosts")
	public ResponseEntity<String> obtenerPosts(){
		String json = new Gson().toJson(servicioPosts.obtenerPostsParaListado());
		return new ResponseEntity<String>(
				json,HttpStatus.OK);	
	}
	@RequestMapping("obtenerPostPorForoId")
	public ResponseEntity<String> obtenerPost(String id, HttpServletRequest request){
			String json = new Gson().toJson(servicioPosts.obtenerIdPostPorForoId(Long.parseLong(id)));
		return new ResponseEntity<String>(
				json,HttpStatus.OK);
	}
	
	@RequestMapping("registrarPosts")
	public ResponseEntity<String> registrarPost(String idForo, String idUsuario, @RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto,
			HttpServletRequest request){
		String respuesta = "";
		System.out.println("--------"+formData);
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		
		System.out.println("--------"+json);
		Post f = gson.fromJson(json, Post.class);
		f.setIdForo(Long.parseLong(idForo));
		f.setIdUsuario(Long.parseLong(idUsuario));
		System.out.println("foro a registrar: " + f.toString());
		f.setLikes(0);
		f.setFechaCreacion(LocalDate.now().getDayOfMonth()+"-"+LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
		servicioPosts.registrarPost(f);
		//tras hacer un registro con hibernate, hibernate asigna a este usuario la id del 
		//registro en la tabla de la base de datos
	 	String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenPost(f, rutaRealDelProyecto);
		respuesta = "ok";
		
		return new ResponseEntity<String>(
				respuesta,HttpStatus.OK);
	}
}
	
	


