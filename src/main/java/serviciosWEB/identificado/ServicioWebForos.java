package serviciosWEB.identificado;

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

import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;

import utilidadesArchivos.GestorArchivos;

@Controller("servicioWebForosIdentificado")
@RequestMapping("identificado/servicioWebForos/")

public class ServicioWebForos {

	@Autowired
	private ServicioForos servicioForos;

	@Autowired
	private ServicioPosts servicioPosts;

	@RequestMapping("obtenerForoPorId")
	public ResponseEntity<String> obtenerForoPorId(HttpServletRequest request) {
		Foro f = (Foro) request.getSession().getAttribute("foro");
		System.out.println("----El id----" + f.getId());
		String json = new Gson().toJson(servicioForos.obtenerForosPorId(f.getId()));
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping("registroForo")
	public ResponseEntity<String> registroPost(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {

		String respuesta = "";
		System.out.println("--------" + formData);

		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);

		System.out.println("--------" + json);
		Foro f = gson.fromJson(json, Foro.class);
		System.out.println("Foro a registrar " + f.toString());
		servicioForos.registrarForo(f);

		// tras hacer un registro con hibernate, hibernate asigna a este foro la id del
		// registro en la tabla de la base de datos

		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenForo(f, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("borrarForoPorId")
	public ResponseEntity<String> borrarUsuarioPorId(HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		String respuesta = "";

		// Eliminar los servicioPosts y servicioComentarios antes
		// for(int i =0; i <
		// servicioComentarios.obtenerComentariosPorId(u.getId()).size(); i++) {}

		for (int i = 0; i < servicioPosts.obtenerIdPostPorForoId(u.getId()).size(); i++) {

			// long j=servicioPosts.obtenerIdPostPorForoId(u.getId()).get(i);
			// servicioComentarios.eliminarComentariosPost(j);
		}

		for (int i = 0; i < servicioForos.obtenerIdPostDeForo(u.getId()).size(); i++) {

			long z = servicioForos.obtenerIdPostDeForo(u.getId()).get(i);
			servicioPosts.eliminarPostsDeForo(z);
		}

		servicioForos.borrarForo(u.getId());

		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

}
