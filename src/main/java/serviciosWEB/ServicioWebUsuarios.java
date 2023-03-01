package serviciosWEB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import modelo.Usuario;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;

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
		//Creamos una fecha y la guardamos en fechaCreacion
		
		
		//Recogemos la fecha actual en el formato adecuado
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-DD-MM");
        String formattedDate = dateFormat.format(currentDate);
        Date fechaCreacion = null;
        try {
            fechaCreacion = dateFormat.parse(formattedDate);
            u.setFechaCreacion(fechaCreacion);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
		//Se inserta una descripcion vacia que posteriormente se podrá editar
		u.setDescripcion("Estoy usando phorum");
		System.out.println("usuario a registrar: " + u.toString());
		servicioUsuarios.registrarUsuario(u);
		
		//Guardado de imagen
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarFotoUsuario(u, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("loginUsuario")
	public ResponseEntity<String> identificarUsuario(String email, String pass, HttpServletRequest request) {
		Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
		String respuesta = "";
		if (u != null) {
			request.getSession().setAttribute("usuario", u);
			respuesta = "ok," + u.getNombre() + "," + u.getId();

		} else {
			respuesta = "email o pass incorrectos";
		}
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
