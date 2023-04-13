package servicios;

import java.util.List;

import java.util.Map;

import modelo.Comentario;

public interface ServicioComentarios {

	int obtenerTotalDeComentariosDeUsuario(long id);

	int obtenerTotalDeComentarios(String textoComentario);

	int obtenerTotalComentariosDePost(long postComentario);

	List<Comentario> obtenerComentarios(String textoComentario, int comienzo);
	
	Comentario obtenerComentariosPorId(long id);

	List<Map<String, Object>> obtenerComentariosPost(long id);

	void registrarComentario(Comentario c);

	void borrarComentario(long id);

	void borrarComentariosPoridPost(long id);

	void borrarComentariosPorIdUsuario(long id);

	void guardarCambiosComentario(Comentario c);

	Map<String, Object> obtenerComentario(long id);

}
