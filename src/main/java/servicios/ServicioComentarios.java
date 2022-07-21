package servicios;

import java.util.List;

import java.util.Map;

import modelo.Comentario;


public interface ServicioComentarios {

List<Map<String, Object>> obtenerComentariosParaListado();
Map<String, Object> obtenerComentariosPorId(long id);
void registrarComentario(Comentario c);
Comentario obtenerComentarioPorId(long id);
List<Comentario> obtenerComentarios(String nombre, int comienzo);
int obtenerTotalDeComentarios(String nombre);
void eliminarComentario(long id);
public void eliminarComentariosPost(long id);
void guardarCambiosComentario(Comentario c);
	
}
