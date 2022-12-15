package serviciosWEB.identificado;








import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;


import servicios.ServicioComentarios;
import servicios.ServicioPosts;




@Controller("servicioWebComentariosIdentificado")
@RequestMapping("identificado/servicioWebComentarios/")
public class ServicioWebComentarios {	
	
	
	@Autowired
	private ServicioComentarios servicioComentarios;
	
	
	
}
