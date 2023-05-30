package controladores.admin;




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

import modelo.Comentario;


import servicios.ServicioComentarios;

import servicios.ServicioPosts;
import servicios.ServicioUsuarios;




@Controller
@RequestMapping("admin/")
public class ControladoresComentarios {
	
	@Autowired
	private ServicioComentarios servicioComentarios;
	
	@Autowired
	private ServicioPosts servicioPosts;
	
	@Autowired
	private ServicioUsuarios servicioUsuarios;
	
	
	
	@RequestMapping("listarComentarios")
	public String listarComentarios(@RequestParam(defaultValue = "")String comentario, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioComentarios.obtenerComentarios(comentario, comienzo_int));
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioComentarios.obtenerTotalDeComentarios(comentario));
		model.addAttribute("nombre", comentario);
		
		return "admin/comentarios";
	}
	
	@RequestMapping("registrarComentario")
	public String registrarComentario(Model model) {
		Comentario nuevo = new Comentario();
		
		Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		
		model.addAttribute("posts", mapPosts);
		model.addAttribute("usuarios", mapUsuarios);	
		model.addAttribute("nuevoComentario", nuevo);
		
		return "admin/formularioRegistroComentario";
	}
	@RequestMapping("guardarNuevoComentario")
	public String guardarNuevoComentario(@ModelAttribute("nuevoComentario") @Valid Comentario nuevoComentario, BindingResult br, Model model,
			HttpServletRequest request) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		
		//Comprobamos que la fecha no sea nula
		if (nuevoComentario.getFechaCreacion() == null) {

			FieldError error = new FieldError("nuevoComentario", "fechaCreacion", "Se tiene que introducir una fecha");
			br.addError(error);
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("nuevoComentario", nuevoComentario);
			return "admin/formularioRegistroComentario";
		}
		
		//Comprobamos si la fecha es anterior o actual a la fecha en la que estamos
		LocalDate localDate = LocalDate.now();
		Date dateActual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		LocalDate localDateCreacion = nuevoComentario.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate localDateActual = dateActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (!localDateCreacion.isBefore(localDateActual) && !localDateCreacion.isEqual(localDateActual)) {
			FieldError error = new FieldError("nuevoComentario", "fechaCreacion",
					"La fecha tiene que ser anterior o actual");
			br.addError(error);
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("nuevoComentario", nuevoComentario);
			return "admin/formularioRegistroComentario";
		}
	    
		if (!br.hasErrors()) {		
			servicioComentarios.registrarComentario(nuevoComentario);

			return "admin/registroComentarioOk";
			
		} else {
			
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("nuevoComentario", nuevoComentario);
			return "admin/formularioRegistroComentario";
		}
		
	}
	
	@RequestMapping("guardarCambiosComentario")
	public String guardarCambiosComentario(@ModelAttribute("comentario") @Valid Comentario comentario, BindingResult br,  Model model,
			HttpServletRequest request) {
		
		//Eliminamos la hora del guardado de fecha
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(comentario.getFechaCreacion());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    comentario.setFechaCreacion(calendar.getTime()); 
		
		
		if(!br.hasErrors()) {
			servicioComentarios.guardarCambiosComentario(comentario);
			return listarComentarios("",0,model);
		}else {
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("comentario",comentario);
			return "admin/formularioEditarComentario";
		}		
		
	}
	@RequestMapping("editarComentario")
	public String editarComentario(String id, Model model) {
		
		Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
		model.addAttribute("posts", mapPosts);
		
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		model.addAttribute("usuarios", mapUsuarios);
		
		Comentario c = servicioComentarios.obtenerComentariosPorId(Long.parseLong(id));
		model.addAttribute("comentario",c);
		return "admin/formularioEditarComentario";
			
	}
	@RequestMapping("borrarComentario")
	public String borrarComentario(String id, Model model) {
		servicioComentarios.borrarComentario(Long.parseLong(id));
		
		return listarComentarios("",null,model);
	}
	
	
}
