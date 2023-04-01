package validaciones;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import modelo.Comentario;
import modelo.Follow;
import modelo.Foro;
import modelo.Post;
import modelo.Usuario;
import modelo.Valoracion;

public class ValidacionesImpl {

	public static Map<Boolean, String> validarUsuario(@Valid Usuario usuario, BeanPropertyBindingResult  bp) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (bp.hasFieldErrors()) {
			for (int i = 0; i < bp.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + bp.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

	public static Map<Boolean, String> validarForo(@Valid Foro foro, BindingResult br) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (br.hasFieldErrors()) {
			for (int i = 0; i < br.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + br.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

	public static Map<Boolean, String> validarPost(@Valid Post post, BindingResult br) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (br.hasFieldErrors()) {
			for (int i = 0; i < br.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + br.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

	public static Map<Boolean, String> validarComentario(@Valid Comentario comentario, BindingResult br) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (br.hasFieldErrors()) {
			for (int i = 0; i < br.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + br.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

	public Map<Boolean, String> validarFollow(@Valid Follow follow, BindingResult br) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (br.hasFieldErrors()) {
			for (int i = 0; i < br.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + br.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

	public static Map<Boolean, String> validarValoracion(@Valid Valoracion valoracion, BindingResult br) {

		Map<Boolean, String> validacion = new HashMap<Boolean, String>();
		String respuesta = null;

		if (br.hasFieldErrors()) {
			for (int i = 0; i < br.getFieldErrors().size(); i++) {
				respuesta = respuesta + System.lineSeparator() + br.getFieldErrors().get(i);
			}
			validacion.put(false, respuesta);
			return validacion;
		}
		validacion.put(true, "ok");
		return validacion;
	}

}
