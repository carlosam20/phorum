package serviciosWEB.identificado;




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

	
	
	@RequestMapping("registrarPosts")
	public ResponseEntity<String> registrarPosts(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, String idForo, String idUsuario,
			HttpServletRequest request){
		String respuesta = "";
		System.out.println("--------"+formData);
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		System.out.println("--------"+json);
		
		Post p = gson.fromJson(json, Post.class);
		
		System.out.println("idForo"+idForo);
		System.out.println("idUsuario"+idUsuario);
		
		p.setIdForo(Long.parseLong(idForo));
		
		p.setIdUsuario(Long.parseLong(idUsuario));
		
		p.setForo(servicioForos.obtenerForosPorId(p.getIdForo()));
		
		p.setUsuario(servicioUsuarios.obtenerUsuario(p.getIdForo()));
		
		System.out.println("post ForoId:  " + p.getForo());
		
		System.out.println(p.toString());
		
		servicioPosts.registrarPost(p);
		
		//tras hacer un registro con hibernate, hibernate asigna a este usuario la id del 
		//registro en la tabla de la base de datos
		
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenPost(p, rutaRealDelProyecto);
		respuesta = "ok";
		
		return new ResponseEntity<String>(
				respuesta,HttpStatus.OK);
	}
}
	


