package servicios;

import java.util.List;

import java.util.Map;

import modelo.Post;

public interface ServicioPosts {

int obtenerTotalDePostsDeUsuario(long id);
int obtenerTotalDePosts(String marca);
List<Post> obtenerPosts(String marca, int comienzo);
List<Map<String, Object>> obtenerPostsParaListadoAleatorio();
List<Map<String, Object>> obtenerPostsPorId(long id);
List<Map<String, Object>> obtenerPostsPorIdUsuario(long id);
void registrarPost(Post p);
void eliminarPosts(long id);
void guardarCambiosPosts(Post p);
List<Map<String, Object>> obtenerIdPostPorForoId(long id);
void eliminarPostsDeForo (long id);
void eliminarPostUsuarios(long id);
Post obtenerPostPorId(long id);
Map<String, String> obtenerPostsParaDesplegable();

//funciones de ajax
List<Map<String, Object>> obtenerPostsParaListado();








}
