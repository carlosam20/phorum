package validaciones;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.validation.BeanPropertyBindingResult;
import modelo.Comentario;
import modelo.Follow;
import modelo.Foro;
import modelo.Post;
import modelo.Usuario;
import modelo.Valoracion;
import validacionObjetos.ParValidacion;

public class ValidacionesImpl {

	public static ParValidacion validarUsuario(@Valid Usuario usuario, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Usuario> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

	public static ParValidacion validarForo(@Valid Foro foro, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Foro>> violations = validator.validate(foro);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Foro> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

	public static ParValidacion validarPost(@Valid Post post, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Post>> violations = validator.validate(post);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Post> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

	public static ParValidacion validarComentario(@Valid Comentario comentario, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Comentario>> violations = validator.validate(comentario);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Comentario> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

	public static ParValidacion validarFollow(@Valid Follow follow, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Follow>> violations = validator.validate(follow);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Follow> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

	public static ParValidacion validarValoracion(@Valid Valoracion valoracion, BeanPropertyBindingResult bp) {

		// Perform validation and error checks
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Valoracion>> violations = validator.validate(valoracion);
		ParValidacion validacion = new ParValidacion(false, null);
		String respuesta = "";

		for (ConstraintViolation<Valoracion> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String mensaje = violation.getMessage();
			bp.rejectValue(propertyPath, "", mensaje);
			respuesta += mensaje + System.lineSeparator() + System.lineSeparator();
		}

		if (bp.hasErrors()) {
			validacion.setResultado(false);
			validacion.setRespuesta(respuesta);
			return validacion;
		}

		validacion.setResultado(true);
		validacion.setRespuesta("ok");
		return validacion;
	}

}
