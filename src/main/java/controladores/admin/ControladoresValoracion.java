package controladores.admin;

import java.util.HashMap;

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

import modelo.Valoracion;
import servicios.ServicioPosts;
import servicios.ServicioValoracion;
import servicios.ServicioUsuarios;



@Controller
@RequestMapping("admin/")
public class ControladoresValoracion {
	
	
	@Autowired
	private ServicioValoracion servicioValoracion;
	@Autowired
	private ServicioPosts servicioPost;
	@Autowired
	private ServicioUsuarios servicioUsuarios;

	
	@RequestMapping("listarValoraciones")
	public String listarValoraciones(@RequestParam(defaultValue = "0")String id, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		model.addAttribute("info", servicioValoracion.obtenerValoraciones(Long.parseLong(id), comienzo_int));
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioValoracion.obtenerTotalValoraciones());
		model.addAttribute("id", id);
		
		return "admin/valoraciones";
	}
	
	@RequestMapping("registrarValoracion")
	public String registrarValoracion(Model model) {
		Valoracion nuevo = new Valoracion();
		
		Map<String, String> mapPosts = servicioPost.obtenerPostsParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		Map<Boolean, String> mapValoraciones = new HashMap<Boolean, String>();
		mapValoraciones.put(true, "Like");
		mapValoraciones.put(false ,"Dislike");
		
		model.addAttribute("posts", mapPosts);
		model.addAttribute("usuarios", mapUsuarios);
		model.addAttribute("valoraciones", mapValoraciones);
		model.addAttribute("nuevoValoracion", nuevo);
		
		return "admin/formularioRegistroValoracion";
	}
	@RequestMapping("guardarNuevoValoracion")
	public String guardarNuevoValoracion(@ModelAttribute("nuevoValoracion") @Valid Valoracion nuevoValoracion, BindingResult br, Model model,
			HttpServletRequest request) { 
		
		
		//Nos da un array de boolean el primer elemento indica si la valoración existe o no
		if(servicioValoracion.comprobarExisteValoracion(nuevoValoracion.getIdPost(), nuevoValoracion.getIdUsuario())[0]) {
			FieldError error = new FieldError("nuevoValoracion", "valor", "Ya existe una valoración con ese post y ese usuario");
			br.addError(error);
		}
		
		if (!br.hasErrors()) {
			servicioValoracion.registrarValoracion(nuevoValoracion);
			return "admin/registroValoracionOk";
			
		} else {
			
			Map<String, String> mapPosts = servicioPost.obtenerPostsParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			Map<Boolean, String> mapValoraciones = new HashMap<Boolean, String>();
			mapValoraciones.put(true, "Like");
			mapValoraciones.put(false ,"Dislike");
			
			model.addAttribute("posts", mapPosts);
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("valoraciones", mapValoraciones);		
			model.addAttribute("nuevoValoracion", nuevoValoracion);
			return "admin/formularioRegistroValoracion";
		}
		
	}
	@RequestMapping("guardarCambiosValoracion")
	public String guardarCambiosValoracion(@ModelAttribute("valoracion") @Valid Valoracion valoracion, BindingResult br,  Model model,
			HttpServletRequest request) {
		
		
		if(!br.hasErrors()) {
			servicioValoracion.guardarCambiosValoraciones(valoracion);
			return listarValoraciones("0",0,model);
			
		}else {
			Map<Boolean, String> mapValoraciones = new HashMap<Boolean, String>();
			mapValoraciones.put(true, "Like");
			mapValoraciones.put(false ,"Dislike");
			
			model.addAttribute("valoraciones", mapValoraciones);		
			model.addAttribute("nuevoValoracion", valoracion);
			return "admin/formularioEditarValoracion";
		}		
	
		
		
	}
	@RequestMapping("editarValoracion")
	public String editarValoracion(String id, Model model) {

		
		Map<Boolean, String> mapValoraciones = new HashMap<Boolean, String>();
		mapValoraciones.put(true,"Like" );
		mapValoraciones.put(false,"Dislike");
		model.addAttribute("valoraciones", mapValoraciones);
		
		Valoracion v =  servicioValoracion.obtenerValoracion(Long.parseLong(id));
		model.addAttribute("valoracion",v);
		
		return "admin/formularioEditarValoracion";
			
	}
	@RequestMapping("borrarValoracion")
	public String borrarValoracion(String id, Model model) {
		servicioValoracion.eliminaValoraciones(Long.parseLong(id));
		return listarValoraciones("0", null, model);
			
	}
	

}
