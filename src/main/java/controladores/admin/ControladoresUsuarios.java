package controladores.admin;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioFollow;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;
import utilidadesArchivos.GestorArchivos;

@Controller
@RequestMapping("admin/")
public class ControladoresUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@Autowired
	private ServicioPosts servicioPosts;

	@Autowired
	private ServicioComentarios servicioComentarios;

	@Autowired
	private ServicioValoracion servicioValoraciones;

	@Autowired
	private ServicioFollow servicioFollows;

	@RequestMapping("listarUsuarios")
	public String listarUsuarios(@RequestParam(defaultValue = "") String nombre, Integer comienzo, Model model) {

		int comienzo_int = 0;

		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}

		model.addAttribute("info", servicioUsuarios.obtenerUsuarios(nombre, comienzo_int));
		model.addAttribute("siguiente", comienzo_int + 10);
		model.addAttribute("anterior", comienzo_int - 10);
		model.addAttribute("total", servicioUsuarios.obtenerTotalDeUsuarios(nombre));
		model.addAttribute("nombre", nombre);

		return "admin/usuarios";
	}

	@RequestMapping("registrarUsuario")
	public String registrarUsuario(Model model) {
		Usuario nuevo = new Usuario();
		model.addAttribute("nuevoUsuario", nuevo);

		return "admin/formularioRegistroUsuario";
	}

	@RequestMapping("guardarNuevoUsuario")
	public String guardarNuevoUsuario(@ModelAttribute("nuevoUsuario") @Valid Usuario nuevoUsuario, BindingResult br,
			Model model, HttpServletRequest request) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		
		//Comprobamos que la fecha no sea nula
		if (nuevoUsuario.getFechaCreacion() == null) {

			FieldError error = new FieldError("nuevoUsuario", "fechaCreacion", "Se tiene que introducir una fecha");
			br.addError(error);
			return "admin/formularioRegistroUsuario";
		} 


		if (!br.hasErrors()) {

			if(servicioUsuarios.comprobarEmail(nuevoUsuario.getEmail())) {
				FieldError error = new FieldError("nuevoUsuario", "email",
						"Existe una cuenta con ese email");
				br.addError(error);
				return "admin/formularioRegistroUsuario";
			}

			//Comprobamos si la fecha es anterior o actual a la fecha en la que estamos
			LocalDate localDate = LocalDate.now();
			Date dateActual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			LocalDate localDateCreacion = nuevoUsuario.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate localDateActual = dateActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (!localDateCreacion.isBefore(localDateActual) && !localDateCreacion.isEqual(localDateActual)) {
				FieldError error = new FieldError("nuevoUsuario", "fechaCreacion",
						"La fecha tiene que ser anterior o actual");
				br.addError(error);
				return "admin/formularioRegistroUsuario";
			}
			//Comprobamos el tamaño de la imagen
			if(nuevoUsuario.getImagen().getSize() == 0 || nuevoUsuario.getImagen().isEmpty()) {
				FieldError error = new FieldError("nuevoUsuario", "imagen",
						"Se tiene que introducir una imagen");
				br.addError(error);
				return "admin/formularioRegistroUsuario";
			}

			// Eliminamos la hora del guardado de fecha
			calendar.setTime(nuevoUsuario.getFechaCreacion());
			nuevoUsuario.setFechaCreacion(calendar.getTime());

			servicioUsuarios.registrarUsuario(nuevoUsuario);
			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			GestorArchivos.guardarFotoUsuarioAdmin(nuevoUsuario, rutaRealDelProyecto);
			return "admin/registroUsuarioOk";

		} else {

			model.addAttribute("nuevoUsuario", nuevoUsuario);
			return "admin/formularioRegistroUsuario";
		}

	}

	@RequestMapping("guardarCambiosUsuario")
	public String guardarCambiosUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult br,
			Model model, HttpServletRequest request) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		
		//Comprobamos que la fecha no sea nula
		if (usuario.getFechaCreacion() == null) {

			FieldError error = new FieldError("usuario", "fechaCreacion", "Se tiene que introducir una fecha");
			br.addError(error);
			return "admin/formularioEditarUsuario";
		} 

		if (!br.hasErrors()) {
			
			if(servicioUsuarios.comprobarEmail(usuario.getEmail())) {
				FieldError error = new FieldError("usuario", "email",
						"Existe una cuenta con ese email");
				br.addError(error);
				return "admin/formularioEditarUsuario";
			}

			//Comprobamos si la fecha es anterior o actual a la fecha en la que estamos
			LocalDate localDate = LocalDate.now();
			Date dateActual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			LocalDate localDateCreacion = usuario.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate localDateActual = dateActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (!localDateCreacion.isBefore(localDateActual) && !localDateCreacion.isEqual(localDateActual)) {
				FieldError error = new FieldError("usuario", "fechaCreacion",
						"La fecha tiene que ser anterior o actual");
				br.addError(error);
				return "admin/formularioEditarUsuario";
			}
			//Comprobamos el tamaño de la imagen
			if(usuario.getImagen().getSize() == 0 || usuario.getImagen().isEmpty()) {
				FieldError error = new FieldError("nuevoUsuario", "imagen",
						"Se tiene que introducir una imagen");
				br.addError(error);
				return "admin/formularioEditarUsuario";
			}

			// Eliminamos la hora del guardado de fecha
			calendar.setTime(usuario.getFechaCreacion());
			usuario.setFechaCreacion(calendar.getTime());
			
			String rutaRealDelProyecto = request.getServletContext().getRealPath("");
			GestorArchivos.guardarFotoUsuarioAdmin(usuario, rutaRealDelProyecto);
			servicioUsuarios.guardarCambiosUsuario(usuario);
			return listarUsuarios("", 0, model);

		} else {
			return "admin/formularioEditarUsuario";
		}

	}

	@RequestMapping("editarUsuario")
	public String editarUsuario(String id, Model model) {
		Usuario u = servicioUsuarios.obtenerUsuario(Long.parseLong(id));
		model.addAttribute("usuario", u);
		return "admin/formularioEditarUsuario";

	}

	@RequestMapping("borrarUsuario")
	public String borrarUsuario(String id, Model model) {

		// Eliminar follows por usuario
		servicioFollows.eliminarFollowsPorUsuario(Long.parseLong(id));
		
		// Eliminar valoraciones por usuario
		servicioValoraciones.eliminaValoracionesPorUsuario(Long.parseLong(id));

		// Eliminar comentarios del usuario
		servicioComentarios.borrarComentariosPorIdUsuario(Long.parseLong(id));


		// Obtener posts del usuario
		List<Map<String, Object>> postUsuario = servicioPosts.obtenerPostsPorIdUsuario(Long.parseLong(id));

		// Eliminar las valoraciones y comentarios de los posts del usuario
		if (postUsuario.size() != 0) {
			for (int i = 0; i < postUsuario.size(); i++) {
				long postId = Long.parseLong(String.valueOf(postUsuario.get(i).get("id")));
				servicioValoraciones.eliminaValoracionesPorPost(postId);
				servicioComentarios.borrarComentariosPoridPost(postId);
			}
		}

		// Eliminar posts del usuario
		servicioPosts.eliminarPostUsuarios(Long.parseLong(id));

		// Eliminar el usuario finalmente
		servicioUsuarios.eliminarUsuario(Long.parseLong(id));

		return listarUsuarios("", null, model);

	}

}
