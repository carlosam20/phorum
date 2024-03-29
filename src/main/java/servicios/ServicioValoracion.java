package servicios;

import java.util.List;
import java.util.Map;

import modelo.Valoracion;

public interface ServicioValoracion {
	void registrarValoracion(Valoracion v);

	Valoracion obtenerValoracion(long id);

	List<Valoracion> obtenerValoraciones(long id, int comienzo);

	int obtenerTotalValoraciones();

	int obtenerTotalValoracionesPostLike(long idPost);

	int obtenerTotalValoracionesPostDislike(long idPost);

	void eliminaValoracionesPorUsuario(long id);

	void eliminaValoraciones(long id);
	

	
	void eliminaValoracionesPorPost(long id);

	void guardarCambiosValoraciones(Valoracion v);

	Map<String, String> obtenerValoracionesParaDesplegable();

	Map<String, Object> obtenerValoracionesPorId(long id);

	List<Map<String, Object>> obtenerValoracionPorPostId(long id);

	Map<String, Object> obtenerValoracionPorPostIdYPorUsuarioId(long id, long idPost);

	boolean[] comprobarExisteValoracion(long idPost, long idUsuario);
}
