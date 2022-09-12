package serviciosWEB.identificado;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import modelo.Post;
import servicios.ServicioComentarios;
import servicios.ServicioForos;




@Controller("servicioWebComentariosIdentificado")
@RequestMapping("identificado/servicioWebComentarios/")
public class ServicioWebComentarios {

	@Autowired
	private ServicioComentarios servicioComentarios;
	
	@RequestMapping("obtenerComentarios")
	public ResponseEntity<String> obtenerComentarios(){
		
		String json = new Gson().toJson(servicioComentarios.obtenerIdComentariosDePost(0));
		return new ResponseEntity<String>(
				json,HttpStatus.OK);	
	}
	
	
}
