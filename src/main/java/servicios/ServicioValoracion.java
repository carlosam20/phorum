package servicios;

import java.util.List;
import java.util.Map;

import modelo.Valoracion;

public interface ServicioValoracion {
	void registrarValoracion(Valoracion v);
	Valoracion obtenerValoracion(long id);
	List<Valoracion> obtenerValoraciones(long id, int comienzo);
	int obtenerTotalValoraciones(long id);
	int obtenerTotalValoracionesPostLike(long idPost);
	int obtenerTotalValoracionesPostDislike(long idPost);
	void eliminaValoraciones(long id);
	void guardarCambiosValoraciones(Valoracion v);
	Map<String, String >obtenerValoracionesParaDesplegable();
	Map<String, Object> obtenerValoracionesPorId(long id);
	List<Map<String,Object>> obtenerValoracionPorPostId(long id);
}
