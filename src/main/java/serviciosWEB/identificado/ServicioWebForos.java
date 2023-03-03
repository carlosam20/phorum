package serviciosWEB.identificado;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import modelo.Foro;

import modelo.Usuario;
import servicios.ServicioFollow;
import servicios.ServicioForos;
import servicios.ServicioPosts;

import utilidadesArchivos.GestorArchivos;

@Controller("servicioWebForosIdentificado")
@RequestMapping("identificado/servicioWebForos/")

public class ServicioWebForos {

	@Autowired
	private ServicioForos servicioForos;

	@Autowired
	private ServicioPosts servicioPosts;

	@Autowired
	private ServicioFollow servicioFollow;

	@RequestMapping("obtenerForoPorId")
	public ResponseEntity<String> obtenerForoPorId(HttpServletRequest request) {
		Foro f = (Foro) request.getSession().getAttribute("foro");
		System.out.println("----El id----" + f.getId());
		String json = new Gson().toJson(servicioForos.obtenerForosPorId(f.getId()));
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping("registroForo")
	public ResponseEntity<String> registroPost(@RequestParam Map<String, Object> formData,
			@RequestParam("foto") CommonsMultipartFile foto, HttpServletRequest request) {

		String respuesta = "";
		System.out.println("--------" + formData);

		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);

		System.out.println("--------" + json);
		Foro f = gson.fromJson(json, Foro.class);
		System.out.println("Foro a registrar " + f.toString());
		servicioForos.registrarForo(f);

		// tras hacer un registro con hibernate, hibernate asigna a este foro la id del
		// registro en la tabla de la base de datos

		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		GestorArchivos.guardarImagenForo(f, rutaRealDelProyecto, foto);
		respuesta = "ok";

		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("borrarForoPorId")
	public ResponseEntity<String> borrarUsuarioPorId(HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		String respuesta = "";

		for (int i = 0; i < servicioForos.obtenerIdPostDeForo(u.getId()).size(); i++) {

			long z = servicioForos.obtenerIdPostDeForo(u.getId()).get(i);
			servicioPosts.eliminarPostsDeForo(z);
		}

		servicioForos.borrarForo(u.getId());

		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("obtenerForos")
	public ResponseEntity<String> obtenerForos(HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		List<Map<String, Object>> foros = servicioForos.obtenerForosParaListado();
		
	
		for (Map<String, Object> foro : foros) {
			
			boolean followExiste = servicioFollow.comprobarExisteFollow(Long.parseLong(String.valueOf(foro.get("id"))), u.getId());

			if (followExiste) {
				Map<String, Object> follow = servicioFollow.obtenerFollowPorUsuarioIdYPorForoId(u.getId(),
						Long.parseLong(String.valueOf(foro.get("id"))));
				foro.put("idFollow", follow.get("id"));
			}
		}
		
		
		String json = new Gson().toJson(foros);
		System.out.println("WebForos"+json);
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}
	@RequestMapping("obtenerForosPerfil")
	public ResponseEntity<String> obtenerForosPerfil(HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		List<Map<String, Object>> foros = servicioForos.obtenerForosParaListado();
		List<Map<String, Object>> perfilForos = new ArrayList<Map<String,Object>>();
		
	
		//Iteramos en los foro del list e introducimos el idFollow además de introducirlos al listado que pasaremos
		for (Map<String, Object> foro : foros) {
			
			boolean followExiste = servicioFollow.comprobarExisteFollow(Long.parseLong(String.valueOf(foro.get("id"))), u.getId());

			if (followExiste) {
				Map<String, Object> follow = servicioFollow.obtenerFollowPorUsuarioIdYPorForoId(u.getId(),
						Long.parseLong(String.valueOf(foro.get("id"))));
				foro.put("idFollow", follow.get("id"));
				perfilForos.add(foro);
			}
		}
		
		
		String json = new Gson().toJson(perfilForos);
		System.out.println("WebForos"+json);
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}
	@RequestMapping("obtenerForosDeNombreIntroducidoPerfil")
	public ResponseEntity<String> obtenerForosDeNombreIntroducidoPerfil(String nombreForo, HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");
		
		List<Map<String, Object>> foros = servicioForos.obtenerForosParaListadoBusquedaForo(nombreForo);
		List<Map<String, Object>> perfilForosBuscados = new ArrayList<Map<String,Object>>();
		
		
		//Iteramos en los foro del list e introducimos el idFollow además de introducirlos al listado que pasaremos
		for (Map<String, Object> foro : foros) {
			
			boolean followExiste = servicioFollow.comprobarExisteFollow(Long.parseLong(String.valueOf(foro.get("id"))), u.getId());

			if (followExiste) {
				Map<String, Object> follow = servicioFollow.obtenerFollowPorUsuarioIdYPorForoId(u.getId(),
						Long.parseLong(String.valueOf(foro.get("id"))));
				foro.put("idFollow", follow.get("id"));
				perfilForosBuscados.add(foro);
			}
		}
		
		String json = new Gson().toJson(perfilForosBuscados);
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}
	

}
