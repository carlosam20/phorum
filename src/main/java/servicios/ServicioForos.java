package servicios;

import java.util.List;
import java.util.Map;
import modelo.Foro;

public interface ServicioForos {

public int obtenerTotalDeForos(String nombre);
List<Foro> obtenerForos(String nombre, int comienzo);
Foro obtenerForosPorId(long id);
List<Long>  obtenerIdPostDeForo(long id);
void registrarForo(Foro o);
void borrarForo(long id);
void guardarCambiosForo(Foro l);
Map<String, Object> obtenerForo(long id); 
List<Map<String, Object>> obtenerForosParaListado();
List<Map<String, Object>> obtenerForosParaListadoAleatorios();
List<Map<String, Object>> obtenerForosParaListadoBusquedaForo(String nombre);
Map<String, String> obtenerForosParaDesplegable();








}
