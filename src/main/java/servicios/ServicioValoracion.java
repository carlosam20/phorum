package servicios;

import java.util.List;
import java.util.Map;

import modelo.Valoracion;

public interface ServicioValoracion {
	List<Map<String, Object>> obtenerValoracionParaListado();
	void registrarValoracion(Valoracion v);
	Valoracion obtenerValoracion(long id);
	List<Valoracion> obtenerValoraciones(long id, int comienzo);
	int obtenerTotalValoraciones(long id);
	void eliminaValoraciones(long id);
	void guardarCambiosValoraciones(Valoracion v);
	Map<String, String >obtenerValoracionesParaDesplegable();
	Map<String, Object> obtenerValoracionesPorId(long id);
}
