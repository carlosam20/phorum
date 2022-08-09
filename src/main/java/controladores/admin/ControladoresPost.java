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
	
	
	@RequestMapping("listarPosts")
	public String listarPosts(@RequestParam(defaultValue = "")String nombre, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioPosts.obtenerPosts(nombre, comienzo_int));
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
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
			HttpServletRequest request) {
		if (!br.hasErrors()) {		
			servicioPosts.registrarPost(nuevoPost);
			
			String rutaRealDelProyecto =
			request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenPost(nuevoPost, rutaRealDelProyecto, null);
			return "admin/registroPostOk";
			
		} else {
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			
			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);		
			model.addAttribute("nuevoPost", nuevoPost);
			return "admin/formularioRegistroPost";
		}
		
	}
	
	@RequestMapping("guardarCambiosPost")
	public String guardarCambiosPost(@ModelAttribute("post") @Valid Post post, BindingResult br,  Model model,
			HttpServletRequest request) {
		servicioPosts.guardarCambiosPosts(post);
		
		if(!br.hasErrors()) {
			
			String rutaRealDelProyecto = 
					request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenPost(post, rutaRealDelProyecto, null);
			
			return listarPosts("",0,model);
		}else {
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("post",post);
			return "admin/formularioEditarPost";
		}		
		
	}
	@RequestMapping("editarPost")
	public String editarPost(String id, Model model) {
		Post p = servicioPosts.obtenerPostsPorId(Long.parseLong(id));
		model.addAttribute("post",p);
		return "admin/formularioEditarPost";
			
	}
	@RequestMapping("borrarPost")
	public String borrarPost(String id, Model model) {
		servicioPosts.eliminarPosts(Long.parseLong(id));
		
		return listarPosts("",0,model);
	}
	
	
}
