package serviciosWEB;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;


import servicios.ServicioForos;




@Controller
@RequestMapping("servicioWebComentarios/")
public class ServicioWebComentarios {

	@Autowired
	private ServicioForos servicioForos;
	
	@RequestMapping("obtenerForos")
	public ResponseEntity<String> obtenerForos(){
		String json = new Gson().toJson(servicioForos.obtenerForosParaListado());
		return new ResponseEntity<String>(
				json,HttpStatus.OK);	
	}
	@RequestMapping("obtenerForo")
	public ResponseEntity<String> obtenerForo(String id){
		String json = new Gson().toJson(servicioForos.obtenerForo(Long.parseLong(id)));
		return new ResponseEntity<String>(
				json,HttpStatus.OK);
	}
	
	/*
	@RequestMapping("obtenerPostsForo")
	public ResponseEntity<String> obtenerPostsForo(HttpServletRequest request){
		String respuesta = "";
		System.out.println("----ENTRA EN OBTENER PRODUCTOS CARRITO----");
		
		respuesta = new Gson().toJson(servicioForos.obtenerPostsForo(
					(Usuario)request.getSession().getAttribute("usuario")));
		
		return new ResponseEntity<String>(
				respuesta,HttpStatus.OK);
	}
	*/

}
