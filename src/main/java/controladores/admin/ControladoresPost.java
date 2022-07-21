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


import modelo.Post;
import servicios.ServicioForos;
import servicios.ServicioPosts;



@Controller
@RequestMapping("admin/")
public class ControladoresPost {

	@Autowired
	private ServicioPosts servicioPosts;
	
	@Autowired
	private ServicioForos servicioForos;
	
	@RequestMapping("listarPosts")
	public String listarPosts(@RequestParam(defaultValue = "")String nombre, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioPosts.obtenerPosts(nombre, comienzo_int));
		//model.addAttribute("fecha_hora_actual", new Date().getTime());
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioPosts.obtenerTotalDePosts(nombre));
		model.addAttribute("nombre", nombre);
		
		return "admin/posts";
	}
	
	@RequestMapping("registrarPost")
	public String registrarPost(Model model) {
		Post nuevo = new Post();
		model.addAttribute("nuevoPost", nuevo);
		
		Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
		model.addAttribute("foros", mapForos);
		
		return "admin/formularioRegistroPost";
	}
	@RequestMapping("guardarNuevoPost")
	public String guardarNuevoPost(@ModelAttribute("nuevoForo") @Valid Post nuevoPost, BindingResult br, Model model,
			HttpServletRequest request) {
		if (!br.hasErrors()) {		
			servicioPosts.registrarPosts(nuevoPost);
		
			return "admin/registroPostOk";
			
		} else {
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			model.addAttribute("nuevoForo", nuevoPost);
			return "admin/formularioPost";
		}
		
	}
	@RequestMapping("guardarCambiosPost")
	public String guardarCambiosPost(@ModelAttribute("foro") @Valid Post post, BindingResult br,  Model model,
			HttpServletRequest request) {
		servicioPosts.guardarCambiosPosts(post);
		if(!br.hasErrors()) {
			servicioPosts.registrarPosts(post);
			return listarPosts("",0,model);
		}else {
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			model.addAttribute("post",post);
			return "admin/formularioEditarPost";
		}		
		
	}
	@RequestMapping("editarPost")
	public String editarForo(String id, Model model) {
		Post p = servicioPosts.obtenerPostsPorId(Long.parseLong(id));
		model.addAttribute("post",p);
		return "admin/formularioEditarPost";
			
	}
	@RequestMapping("borrarPost")
	public String borrarOrdenador(String id, Model model) {
		servicioPosts.eliminarPosts(Long.parseLong(id));
		return listarPosts("",0,model);
	}
	
	
}
