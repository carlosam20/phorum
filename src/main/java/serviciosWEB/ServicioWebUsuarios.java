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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Usuario;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;
import validaciones.ValidacionesImpl;

@Controller
@RequestMapping("servicioWebUsuarios/")
public class ServicioWebUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@RequestMapping("registrarUsuario")
	public ResponseEntity<String> registrarUsuario(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {

		// Hay que realizar una comprobación de que no se duplica el Usuario aqui y
		// devolver duplicado si ocurre

		String respuesta = "";
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

		// Se inserta una descripcion vacia que posteriormente se podrá editar
		u.setDescripcion("Estoy usando phorum");
		System.out.println("usuario a registrar: " + u.toString());
		
		BeanPropertyBindingResult bp = new BeanPropertyBindingResult(u, "usuario");

		Map<Boolean, String> resultadoValidacion =  ValidacionesImpl.validarUsuario(u,bp);
		
		
		if(resultadoValidacion.get(true) != null) {
			servicioUsuarios.registrarUsuario(u);
			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			//Guardado de Imagen
			GestorArchivos.guardarFotoUsuario(u, rutaRealDelProyecto, foto);
			respuesta = resultadoValidacion.get("ok");
			return new ResponseEntity<String>(respuesta, HttpStatus.OK);
		}else {
			respuesta = "Error en registro del usuario";
			return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
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
		    respuesta = "Contraseña incorrecta";
		    return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		request.getSession().setAttribute("usuario", u);
		respuesta = "ok," + u.getNombre() + "," + u.getId();
		return new ResponseEntity<String>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
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
