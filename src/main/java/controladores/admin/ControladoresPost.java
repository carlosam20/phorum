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
import modelo.Usuario;
import servicios.ServicioPosts;
import utilidadesArchivos.GestorArchivos;



@Controller
@RequestMapping("admin/")
public class ControladoresPost {

	@Autowired
	private ServicioPosts servicioPosts;
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
		
		return "admin/Posts";
	}
	
	@RequestMapping("registrarPost")
	public String registrarPost(Model model) {
		Post nuevo = new Post();
		model.addAttribute("nuevoPost", nuevo);
		
		return "admin/formularioRegistroPost";
	}
	@RequestMapping("guardarNuevoPost")
	public String guardarNuevoPost(@ModelAttribute("nuevoPost") @Valid Post nuevoPost, BindingResult br, Model model,
			HttpServletRequest request) {
		if (!br.hasErrors()) {		
			servicioPosts.registrarPost(nuevoPost);
			String rutaRealDelProyecto =
			request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenPost(nuevoPost, rutaRealDelProyecto, null);
			return "admin/registroPostOk";
			
		} else {
			
			Map<String, String> mapPosts = servicioPosts.obtenerPostsParaDesplegable();
			model.addAttribute("posts", mapPosts);
			
			model.addAttribute("nuevoPost", nuevoPost);
			return "admin/formularioRegistroPost";
		}
		
	}
	
	@RequestMapping("guardarCambiosPost")
	public String guardarCambiosPost(@ModelAttribute("Post") @Valid Post Post, BindingResult br,  Model model,
			HttpServletRequest request) {
		servicioPosts.guardarCambiosPost(Post);
		
		if(!br.hasErrors()) {
			
			String rutaRealDelProyecto = 
					request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenPost(Post, rutaRealDelProyecto, null);
			
			return listarPosts("",0,model);
		}else {
			model.addAttribute("Post",Post);
			return "admin/formularioEditarPost";
		}		
		
	}
	@RequestMapping("editarPost")
	public String editarPost(String id, Model model) {
		Post f = servicioPosts.obtenerPostsPorId(Long.parseLong(id));
		model.addAttribute("Post",f);
		return "admin/formularioEditarPost";
			
	}
	@RequestMapping("borrarPost")
	public String borrarPost(String id, Model model) {
		servicioPosts.borrarPost(Long.parseLong(id));
		
		return listarPosts("",null,model);
	}
	
	
}
