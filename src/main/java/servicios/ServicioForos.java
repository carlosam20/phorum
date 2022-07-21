package servicios;

import java.util.List;
import java.util.Map;
import modelo.Foro;

public interface ServicioForos {

public int obtenerTotalDeForos(String marca);
List<Foro> obtenerForos(String marca, int comienzo);
Foro obtenerForosPorId(long id);
List<Long>  obtenerIdPostDeForo(long id);
void registrarForo(Foro o);
void borrarForo(long id);
void guardarCambiosForo(Foro l);
Map<String, Object> obtenerForo(long id); 
List<Map<String, Object>> obtenerForosParaListado();
Map<String, String> obtenerForosParaDesplegable();








}
