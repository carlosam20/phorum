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

import modelo.Foro;
import modelo.Usuario;
import servicios.ServicioForos;
import utilidadesArchivos.GestorArchivos;



@Controller
@RequestMapping("admin/")
public class ControladoresForos {

	@Autowired
	private ServicioForos servicioForos;
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
		if (!br.hasErrors()) {		
			servicioForos.registrarForo(nuevoForo);
			String rutaRealDelProyecto =
			request.getServletContext().getRealPath("");
			GestorArchivos.guardarImagenForoAdmin(nuevoForo, rutaRealDelProyecto);
			return "admin/registroForoOk";
			
		} else {
			
			Map<String, String> mapForos = servicioForos.obtenerForosParaDesplegable();
			model.addAttribute("foros", mapForos);
			
			model.addAttribute("nuevoForo", nuevoForo);
			return "admin/formularioRegistroForo";
		}
		
	}
	
	@RequestMapping("guardarCambiosForo")
	public String guardarCambiosForo(@ModelAttribute("foro") @Valid Foro foro, BindingResult br,  Model model,
			HttpServletRequest request) {
		
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
		servicioForos.borrarForo(Long.parseLong(id));
		
		return listarForos("",null,model);
	}
	
	
}
