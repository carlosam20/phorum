package controladores.admin;

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

import modelo.Follow;


import servicios.ServicioForos;
import servicios.ServicioFollow;
import servicios.ServicioUsuarios;


@Controller
@RequestMapping("admin/")
public class ControladoresFollow {
	
	
	@Autowired
	private ServicioFollow servicioFollow;
	@Autowired
	private ServicioForos servicioForos;
	@Autowired
	private ServicioUsuarios servicioUsuarios;

	
	@RequestMapping("listarFollows")
	public String listarFollows(@RequestParam(defaultValue = "0")String id, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		
		if(id == null || id.isEmpty()) {
			id = String.valueOf(0);
		}
		
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		model.addAttribute("info", servicioFollow.obtenerFollows(Long.parseLong(id), comienzo_int));
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
		model.addAttribute("total", servicioFollow.obtenerTotalDeFollows());
		model.addAttribute("id", id);
		
		return "admin/follows";
	}
	
	@RequestMapping("registrarFollow")
	public String registrarFollow(Model model) {
		Follow nuevo = new Follow();
		
		Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		model.addAttribute("foros", mapForos);
		model.addAttribute("usuarios", mapUsuarios);
		model.addAttribute("nuevoFollow", nuevo);
		
		return "admin/formularioRegistroFollow";
	}
	@RequestMapping("guardarNuevoFollow")
	public String guardarNuevoFollow(@ModelAttribute("nuevoFollow") @Valid Follow nuevoFollow, BindingResult br, Model model,
			HttpServletRequest request) {
		
		if(nuevoFollow.getIdUsuario() == 0 ||  nuevoFollow.getIdForo() == 0) {
			FieldError error = new FieldError("nuevoFollow", "valor", "No se ha creado ningún usuario o foro antes");
			br.addError(error);
		}
		
		if(servicioFollow.comprobarExisteFollow(nuevoFollow.getIdForo(), nuevoFollow.getIdUsuario())) {
			FieldError error = new FieldError("nuevoFollow", "idUsuario", "Ya un follow con este usuario y foro");
			br.addError(error);
		}
		
		
		if (!br.hasErrors()) {		
			servicioFollow.registrarFollow(nuevoFollow);
			return "admin/registroFollowOk";
			
		} else {
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			
			model.addAttribute("foros", mapForos);
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("nuevoFollow", nuevoFollow);
			return "admin/formularioRegistroFollow";
		}
		
	}
	@RequestMapping("guardarCambiosFollow")
	public String guardarCambiosFollow(@ModelAttribute("follow") @Valid Follow follow, BindingResult br,  Model model,
			HttpServletRequest request) {
		
		if(servicioFollow.comprobarExisteFollow(follow.getIdForo(), follow.getIdUsuario())) {
			FieldError error = new FieldError("nuevoFollow", "idUsuario", "Ya un follow con este usuario y foro");
			br.addError(error);
		}
	
		if(!br.hasErrors()) {

			servicioFollow.guardarCambiosFollow(follow);
			return listarFollows("0",0,model);
			
		}else {
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			model.addAttribute("follow",follow);
			return "admin/formularioEditarFollow";
		}		
	
		
		
	}
	@RequestMapping("editarFollow")
	public String editarFollow(String id, Model model) {

		Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
		Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
		model.addAttribute("foros", mapForos);
		model.addAttribute("usuarios", mapUsuarios);
		
		Follow f =  servicioFollow.obtenerFollow(Long.parseLong(id));
		model.addAttribute("follow",f);
		return "admin/formularioEditarFollow";
			
	}
	@RequestMapping("borrarFollow")
	public String borrarFollow(String id, Model model) {
		servicioFollow.eliminarFollow(Long.parseLong(id));
		return listarFollows("0", null, model);
			
	}
	

}
