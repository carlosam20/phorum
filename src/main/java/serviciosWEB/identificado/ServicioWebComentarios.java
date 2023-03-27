package serviciosWEB.identificado;

import java.util.Date;
import org.joda.time.LocalDate;
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

import modelo.Comentario;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioPosts;

@Controller("servicioWebComentariosIdentificado")
@RequestMapping("identificado/servicioWebComentarios/")
public class ServicioWebComentarios {

	@Autowired
	private ServicioComentarios servicioComentarios;
	
	@Autowired
	private ServicioPosts servicioPosts ;
	
	@RequestMapping("registrarComentario")
	public ResponseEntity<String> registrarComentario(@RequestParam Map<String, Object> formData, String idPost,
			HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
	
		String respuesta = "";
		System.out.println("--------" + formData);
		
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		System.out.println("--------" + json);


		Comentario c = gson.fromJson(json, Comentario.class);
		c.setIdPostComentario(servicioPosts.obtenerPostPorId(Long.parseLong(idPost)).getId());
		c.setIdUsuario(u.getId());
		
		//Recogemos la fecha actual en el formato adecuado
		LocalDate currentDate = LocalDate.now();
		Date formattedDate = currentDate.toDate();
        c.setFechaCreacion(formattedDate); 
        
		servicioComentarios.registrarComentario(c);
	
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
