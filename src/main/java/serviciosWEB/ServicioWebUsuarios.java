package serviciosWEB;

import java.util.Calendar;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Usuario;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;
import validacionObjetos.ParValidacion;
import validaciones.ValidacionesImpl;

@Controller
@RequestMapping("servicioWebUsuarios/")
public class ServicioWebUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@RequestMapping("registrarUsuario")
	public ResponseEntity<String> registrarUsuario(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {
		Gson gson = new Gson();

		JsonElement json = gson.toJsonTree(formData);

		Usuario u = gson.fromJson(json, Usuario.class);
		
		// Creamos una fecha y la guardamos en fechaCreacion

		// Recogemos la fecha actual en el formato adecuado
		Date currentDate = Calendar.getInstance().getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		u.setFechaCreacion(currentDate);

		// Se inserta una descripcion vacia que posteriormente se podr� editar
		u.setDescripcion("Estoy usando phorum");
		
		BeanPropertyBindingResult bp = new BeanPropertyBindingResult(u, "usuario");

		ParValidacion resultadoValidacion =  ValidacionesImpl.validarUsuario(u,bp,foto);
		
		if(servicioUsuarios.comprobarEmail(u.getEmail())) {
			resultadoValidacion.setRespuesta("Hay un email con esta cuenta");
			resultadoValidacion.setResultado(false);
		}
		
		if(resultadoValidacion.getResultado() == true) {
			servicioUsuarios.registrarUsuario(u);
			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			//Guardado de Imagen
			GestorArchivos.guardarFotoUsuario(u, rutaRealDelProyecto, foto);
			
			return new ResponseEntity<String>(resultadoValidacion.getRespuesta(), HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(resultadoValidacion.getRespuesta(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping("loginUsuario")
	public ResponseEntity<String> identificarUsuario(String email, String pass, HttpServletRequest request) {
		Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
		String respuesta = "";

		if (u == null) {
		    respuesta = "Usuario incorrecto";
		    return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!email.equals(u.getEmail())) {
		    respuesta = "E-mail incorrecto";
		    return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!pass.equals(u.getPass())) {
		    respuesta = "Contrase�a incorrecta";
		    return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		request.getSession().setAttribute("usuario", u);
		respuesta = "ok," + u.getNombre() + "," + u.getId();
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("comprobarLogin")
	public ResponseEntity<String> comprobarLogin(HttpServletRequest request) {
		String respuesta = "";

		if (request.getSession().getAttribute("usuario") != null) {
			respuesta = "ok";
		} else {
			respuesta = "Te debes identificar para acceder al contenido";
		}
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String respuesta = "";
		request.getSession().invalidate();
		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
