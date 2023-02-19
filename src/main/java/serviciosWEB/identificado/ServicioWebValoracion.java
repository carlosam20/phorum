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

import modelo.Usuario;
import modelo.Valoracion;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;

@Controller("servicioWebValoracionIdentificado")
@RequestMapping("identificado/servicioWebValoracion/")
public class ServicioWebValoracion {

	@Autowired
	private ServicioValoracion servicioValoracion;
	


	@RequestMapping("registrarValoracion")
	public ResponseEntity<String> registrarValoracion(@RequestParam Map<String, Object> formData,
			HttpServletRequest request) {

		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		String respuesta = "";

		System.out.println("--------" + formData);
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		System.out.println("--------" + json);
		
		Valoracion v = gson.fromJson(json, Valoracion.class);
		
	
		v.setIdUsuario(u.getId());
		
		servicioValoracion.registrarValoracion(v);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);

	}

	@RequestMapping("eliminarValoracion")
	public ResponseEntity<String> eliminarValoracion(HttpServletRequest request, String idPost) {

		 Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		 
		 
		  
		    Map<String, Object> usuarioPost = servicioValoracion.obtenerValoracionPorPostIdYPorUsuarioId(u.getId(), Long.parseLong(idPost));

		    if (usuarioPost != null && usuarioPost.get("id") == null) {
		        
		        return new ResponseEntity<String>("ok", HttpStatus.OK);
		    } else {
		    	servicioValoracion.eliminaValoraciones(Long.parseLong(String.valueOf(usuarioPost.get("id"))));
		        return new ResponseEntity<String>("ok", HttpStatus.OK);
		    }

	}
	@RequestMapping("comprobarValoracion")
	public ResponseEntity<String>comprobarValoracion(HttpServletRequest request, String idPost){
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		boolean existe = servicioValoracion.comprobarExisteValoracion(Long.parseLong(idPost), Long.parseLong(String.valueOf(u.getId())));
		 
		if(existe) {
			return new ResponseEntity<String>("ok, true", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("ok, false", HttpStatus.OK);
		}
		
	}


}
