package controladores.admin;




import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

import modelo.Foro;
import servicios.ServicioComentarios;
import servicios.ServicioFollow;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioValoracion;
import utilidadesArchivos.GestorArchivos;



@Controller
@RequestMapping("admin/")
public class ControladoresForos {

	@Autowired
	private ServicioForos servicioForos;
	
	
	@Autowired
	private ServicioPosts servicioPosts;
	
	@Autowired
	private ServicioFollow servicioFollow;
	
	@Autowired
	private ServicioValoracion servicioValoracion;
	
	@Autowired
	private ServicioComentarios servicioComentarios;
	
	@RequestMapping("listarForos")
	public String listarForos(@RequestParam(defaultValue = "")String nombre, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioForos.obtenerForos(nombre, comienzo_int));
		
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioForos.obtenerTotalDeForos(nombre));
		model.addAttribute("nombre", nombre);
		
		return "admin/foros";
	}
	
	@RequestMapping("registrarForo")
	public String registrarForo(Model model) {
		Foro nuevo = new Foro();
		model.addAttribute("nuevoForo", nuevo);
		
		return "admin/formularioRegistroForo";
	}
	
	@RequestMapping("guardarNuevoForo")
	public String guardarNuevoForo(@ModelAttribute("nuevoForo") @Valid Foro nuevoForo, BindingResult br, Model model,
			HttpServletRequest request) {
		
		//Eliminamos la hora del guardado de fecha
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	   
	    
	    
	    //Comprobamos que la fecha no sea nula
		if (nuevoForo.getFechaCreacion() == null) {

			FieldError error = new FieldError("nuevoForo", "fechaCreacion", "Se tiene que introducir una fecha");
			br.addError(error);
			return "admin/formularioRegistroForo";
		} 
		
		
		if (!br.hasErrors()) {
			
			//Comprobamos si la fecha es anterior o actual a la fecha en la que estamos
			LocalDate localDate = LocalDate.now();
			Date dateActual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			LocalDate localDateCreacion = nuevoForo.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate localDateActual = dateActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (!localDateCreacion.isBefore(localDateActual) && !localDateCreacion.isEqual(localDateActual)) {
				FieldError error = new FieldError("nuevoForo", "fechaCreacion",
						"La fecha tiene que ser anterior o actual");
				br.addError(error);
				return "admin/formularioRegistroForo";
			}
			//Comprobamos que la im�gen no este vac�a
			if(nuevoForo.getImagen().getSize() == 0 || nuevoForo.getImagen().isEmpty()) {
				FieldError error = new FieldError("nuevoForo", "imagen",
						"Se tiene que introducir una imagen");
				br.addError(error);
				return "admin/formularioRegistroForo";
			}
			
			calendar.setTime(nuevoForo.getFechaCreacion());
			 nuevoForo.setFechaCreacion(calendar.getTime());
			servicioForos.registrarForo(nuevoForo);
			String rutaRealDelProyecto =
			request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenForoAdmin(nuevoForo, rutaRealDelProyecto);
			return "admin/foros";
			
		} else {
			
			for (int i = 0; i < br.getAllErrors().size(); i++) {
				System.out.println("Error:"+br.getAllErrors().get(i));
			}
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			
			model.addAttribute("nuevoForo", nuevoForo);
			return "admin/formularioRegistroForo";
		}
		
	}
	
	@RequestMapping("guardarCambiosForo")
	public String guardarCambiosForo(@ModelAttribute("foro") @Valid Foro foro, BindingResult br,  Model model,
			HttpServletRequest request) {
		//Eliminamos la hora del guardado de fecha
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(foro.getFechaCreacion());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    foro.setFechaCreacion(calendar.getTime());
	    
		if(!br.hasErrors()) {
			servicioForos.guardarCambiosForo(foro);
			String rutaRealDelProyecto = 
					request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenForoAdmin(foro, rutaRealDelProyecto);
			
			return listarForos("",0,model);
		}else {
			model.addAttribute("foro",foro);
			return "admin/formularioEditarForo";
		}		
		
	}
	@RequestMapping("editarForo")
	public String editarForo(String id, Model model) {
		Foro f = servicioForos.obtenerForosPorId(Long.parseLong(id));
		model.addAttribute("foro",f);
		return "admin/formularioEditarForo";
			
	}
	@RequestMapping("borrarForo")
	public String borrarForo(String id, Model model) {
		
		servicioFollow.eliminarFollowsPorForo(Long.parseLong(id));
		
		
		
		List<Map<String, Object>> posts = servicioPosts.obtenerPostPorForoId(Long.parseLong(id));
			
		//Comentarios de los post y valoraciones
		for (Map<String, Object> post : posts) {
			servicioComentarios.borrarComentariosPoridPost(Long.parseLong(String.valueOf(post.get("id"))));
			
			servicioValoracion.eliminaValoracionesPorPost(Long.parseLong(String.valueOf(post.get("id"))));
		}
				
		
		servicioPosts.eliminarPostsDeForo(Long.parseLong(id));
		
		servicioForos.borrarForo(Long.parseLong(id));
		
		return listarForos("",null,model);
	}
	
	
}
