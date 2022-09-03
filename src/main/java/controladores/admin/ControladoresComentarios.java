package controladores.admin;




import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import modelo.Comentario;
import modelo.Foro;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioForos;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;



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
	public String listarComentarios(@RequestParam(defaultValue = "")String nombre, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioComentarios.obtenerComentarios(nombre, comienzo_int));
		//model.addAttribute("fecha_hora_actual", new Date().getTime());
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioComentarios.obtenerTotalDeComentarios(nombre));
		model.addAttribute("nombre", nombre);
		
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
		servicioComentarios.guardarCambiosComentario(comentario);
		
		if(!br.hasErrors()) {
			
			
			return listarComentarios("",0,model);
		}else {
			
			
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("comentario",comentario);
			return "admin/formularioEditarComentario ";
		}		
		
	}
	@RequestMapping("editarComentario")
	public String editarComentario(String id, Model model) {
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
