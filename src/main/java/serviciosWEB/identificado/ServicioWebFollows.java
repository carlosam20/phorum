package serviciosWEB.identificado;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Follow;
import modelo.Usuario;
import modelo.Valoracion;
import servicios.ServicioFollow;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;

@Controller("servicioWebFollowIdentificado")
@RequestMapping("identificado/servicioWebFollow/")
public class ServicioWebFollows {

	@Autowired
	private ServicioFollow servicioFollow;
	


	@RequestMapping("registrarFollow")
	public ResponseEntity<String> registrarFollow(@RequestParam Map<String, Object> formData,
			HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		String respuesta = "";

		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
	
		Follow f = gson.fromJson(json, Follow.class);
		
	
		f.setIdUsuario(u.getId());
		
		servicioFollow.registrarFollow(f);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);

	}

	@RequestMapping("eliminarFollow")
	public ResponseEntity<String> eliminarFollow(HttpServletRequest request, String idForo) {

		System.out.println("ELiminar follow: "+idForo);
		
		 Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		 		  
		    Map<String, Object> usuarioForo = servicioFollow.obtenerFollowPorUsuarioIdYPorForoId(u.getId(), Long.parseLong(idForo));
		    		
		    if (usuarioForo != null && usuarioForo.get("id") == null) {
		        
		        return new ResponseEntity<String>("ok", HttpStatus.OK);
		    } else {	
		    	servicioFollow.eliminarFollow(Long.parseLong(String.valueOf(usuarioForo.get("id"))));
		        return new ResponseEntity<String>("ok", HttpStatus.OK);
		    }

	}
	@RequestMapping("comprobarFollow")
	public ResponseEntity<String> comprobarFollow(HttpServletRequest request, String idForo){
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		boolean existeFollow = servicioFollow.comprobarExisteFollow(Long.parseLong(idForo), Long.parseLong(String.valueOf(u.getId())));
		 
		
		if(existeFollow) {
			return new ResponseEntity<String>("ok, true", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("ok, false", HttpStatus.OK);
		}
		
	}
}
