package controladores.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import modelo.Post;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;

@Controller
@RequestMapping("admin/")
public class ControladoresPost {

	@Autowired
	private ServicioPosts servicioPosts;
	@Autowired
	private ServicioForos servicioForos;
	@Autowired
	private ServicioUsuarios servicioUsuarios;
	@Autowired
	private ServicioComentarios servicioComentarios;

	@RequestMapping("listarPosts")
	public String listarPosts(@RequestParam(defaultValue = "") String nombre, Integer comienzo, Model model) {

		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}

		model.addAttribute("info", servicioPosts.obtenerPosts(nombre, comienzo_int));
		model.addAttribute("siguiente", comienzo_int + 10);
		model.addAttribute("anterior", comienzo_int - 10);
		model.addAttribute("total", servicioPosts.obtenerTotalDePosts(nombre));
		model.addAttribute("nombre", nombre);

		return "admin/posts";
	}

	@RequestMapping("registrarPost")
	public String registrarPost(Model model) {
		Post nuevo = new Post();

		Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		model.addAttribute("foros", mapForos);
		model.addAttribute("usuarios", mapUsuarios);
		model.addAttribute("nuevoPost", nuevo);

		return "admin/formularioRegistroPost";
	}

	@RequestMapping("guardarNuevoPost")
	public String guardarNuevoPost(@ModelAttribute("nuevoPost") @Valid Post nuevoPost, BindingResult br, Model model,
			HttpServletRequest request) throws IOException {
		// Eliminamos la hora del guardado de fecha
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		if(nuevoPost.getIdForo() == 0 ||  nuevoPost.getIdUsuario() == 0) {
			FieldError error = new FieldError("nuevoPost", "imagen", "No se ha creado ningún foro o usuario antes");
			br.addError(error);
		}

		// Comprobamos que la fecha no sea nula
		if (nuevoPost.getFechaCreacion() == null) {
			FieldError error = new FieldError("nuevoPost", "fechaCreacion", "Se tiene que introducir una fecha");
			br.addError(error);
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();

			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("nuevoPost", nuevoPost);

			return "admin/formularioRegistroPost";
		}
		
		// Comprobamos el tamaño de la imagen
		if (nuevoPost.getImagen().getSize() == 0) {
			FieldError error = new FieldError("nuevoPost", "imagen", "Se tiene que introducir una imagen");
			br.addError(error);
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();

			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("nuevoPost", nuevoPost);
			
			return "admin/formularioRegistroPost";
		}
		
		// Comprobamos si la fecha es anterior o actual a la fecha en la que estamos
		LocalDate localDate = LocalDate.now();
		Date dateActual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		LocalDate localDateCreacion = nuevoPost.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate localDateActual = dateActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (!localDateCreacion.isBefore(localDateActual) && !localDateCreacion.isEqual(localDateActual)) {
			FieldError error = new FieldError("nuevoPost", "fechaCreacion",
					"La fecha tiene que ser anterior o actual");
			br.addError(error);
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();

			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("nuevoPost", nuevoPost);

			return "admin/formularioRegistroPost";
		}

		if (!br.hasErrors()) {

			// Eliminamos la hora del guardado de fecha
			calendar.setTime(nuevoPost.getFechaCreacion());
			nuevoPost.setFechaCreacion(calendar.getTime());

			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			servicioPosts.registrarPost(nuevoPost);
			GestorArchivos.guardarImagenPostAdmin(nuevoPost, rutaRealDelProyecto);
			return "admin/registroPostOk";
		}
		else {
			// Rellenar selectores
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();

			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);

			model.addAttribute("nuevoPost", nuevoPost);
		}


		return "admin/formularioRegistroPost";
	}

	@RequestMapping("guardarCambiosPost")
	public String guardarCambiosPost(@ModelAttribute("post") @Valid Post post, BindingResult br, Model model,
			HttpServletRequest request) {

		// Eliminamos la hora del guardado de fecha
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		if (!br.hasErrors()) {
			servicioPosts.guardarCambiosPosts(post);
			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenPostAdmin(post, rutaRealDelProyecto);

			return listarPosts("", 0, model);
		} else {

			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("post", post);
			return "admin/formularioEditarPost";
		}

	}

	@RequestMapping("editarPost")
	public String editarPost(String id, Model model) {

		Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		model.addAttribute("foros", mapForos);
		model.addAttribute("usuarios", mapUsuarios);

		Post p = servicioPosts.obtenerPostPorId(Long.parseLong(id));
		model.addAttribute("post", p);
		return "admin/formularioEditarPost";

	}

	@RequestMapping("borrarPost")
	public String borrarPost(String id, Model model) {

		servicioComentarios.borrarComentariosPoridPost(Long.parseLong(id));
		servicioPosts.eliminarPosts(Long.parseLong(id));

		return listarPosts("", 0, model);
	}

}
