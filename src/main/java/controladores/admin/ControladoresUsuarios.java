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


import modelo.Usuario;
import servicios.ServicioUsuarios;
import utilidadesArchivos.GestorArchivos;


@Controller
@RequestMapping("admin/")
public class ControladoresUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;
	@RequestMapping("listarUsuarios")
	public String listarUsuarios(@RequestParam(defaultValue = "")String nombre, Integer comienzo, Model model) {
		
		int comienzo_int = 0;
		if (comienzo != null) {
			comienzo_int = comienzo.intValue();
		}
		
		model.addAttribute("info", servicioUsuarios.obtenerUsuarios(nombre, comienzo_int));
		//model.addAttribute("fecha_hora_actual", new Date().getTime());
		
		model.addAttribute("siguiente", comienzo_int+10);
		model.addAttribute("anterior", comienzo_int-10);
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
	public String guardarNuevoUsuario(@ModelAttribute("nuevoUsuario") @Valid Usuario nuevoUsuario, BindingResult br, Model model,
			HttpServletRequest request) {
		if (!br.hasErrors()) {		
			servicioUsuarios.registrarUsuario(nuevoUsuario);
			String rutaRealDelProyecto =
					request.getServletContext().getRealPath("");
				GestorArchivos.guardarFotoUsuario(nuevoUsuario, rutaRealDelProyecto, null);
			return "admin/registroUsuarioOk";
			
		} else {
			Map<String, String> mapUsuarios = servicioUsuarios.obtenerUsuariosParaDesplegable();
			model.addAttribute("usuarios", mapUsuarios);
			
			model.addAttribute("nuevoUsuario", nuevoUsuario);
			return "admin/formularioRegistroUsuario";
		}
		
	}
	@RequestMapping("guardarCambiosUsuario")
	public String guardarCambiosUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult br,  Model model,
			HttpServletRequest request) {
	
		if(!br.hasErrors()) {
			servicioUsuarios.guardarCambiosUsuario(usuario);
			return listarUsuarios("",0,model);
		}else {
			return "admin/formularioEditarUsuario";	
		}
	
		
		
	}
	@RequestMapping("editarUsuario")
	public String editarUsuario(String id, Model model) {
		Usuario u = servicioUsuarios.obtenerUsuario(Long.parseLong(id));
		model.addAttribute("usuario",u);
		return "admin/formularioEditarUsuario";
			
	}
	@RequestMapping("borrarUsuario")
	public String borrarUsuario(String id, Model model) {
		servicioUsuarios.eliminarUsuario(Long.parseLong(id));
		
		return listarUsuarios("", null, model);
			
	}
	
	
	
}
