package servicios;

import java.util.List;
import java.util.Map;

import modelo.Follow;
import modelo.Valoracion;

public interface ServicioFollow {
	List<Map<String, Object>> obtenerFollowParaListado();
	void registrarFollow(Follow s);
	Follow obtenerFollow(long id);
	List<Follow> obtenerFollows(long id, int comienzo);
	int obtenerTotalDeFollows(long id);
	void eliminarFollow(long id);
	void guardarCambiosFollow(Follow s);
	Map<String, String >obtenerFollowsParaDesplegable();
	Map<String, Object> obtenerFollowsPorId(long id);
	Map<String, Object> obtenerFollowPorUsuarioIdYPorForoId(long idUsuario, long idForo);
	boolean comprobarExisteFollow(long idForo, long idUsuario);
}
