package servicios;

import java.util.List;
import java.util.Map;
import modelo.Foro;
import modelo.Post;

public interface ServicioPosts {

public int obtenerTotalDePosts(String marca);
List<Post> obtenerPosts(String marca, int comienzo);
Post obtenerPostsPorId(long id);
void registrarPosts(Post p);
void eliminarPosts(long id);
void guardarCambiosPosts(Post p);
List<Long> obtenerIdPostPorForoId(long id);
void eliminarPostsDeForo(long id);
Map<String, Object> obtenerPosts(long id); 
//funciones de ajax
List<Map<String, Object>> obtenerPostsParaListado();







}
