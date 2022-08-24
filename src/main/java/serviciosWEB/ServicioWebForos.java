package serviciosWEB;




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

import modelo.Foro;
import servicios.ServicioForos;
import utilidadesArchivos.GestorArchivos;




@Controller
@RequestMapping("servicioWebForos/")
public class ServicioWebForos {

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
	
	@RequestMapping("registrarForos")
	public ResponseEntity<String> registrarForo(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto,
			HttpServletRequest request){
		String respuesta = "";
		System.out.println("--------"+formData);
		
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		
		System.out.println("--------"+json);
		Foro f = gson.fromJson(json, Foro.class);
		System.out.println("foro a registrar: " + f.toString());
		servicioForos.registrarForo(f);
		//tras hacer un registro con hibernate, hibernate asigna a este usuario la id del 
		//registro en la tabla de la base de datos
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenForo(f, rutaRealDelProyecto, foto);
		respuesta = "ok";
		
		return new ResponseEntity<String>(
				respuesta,HttpStatus.OK);
	}
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


