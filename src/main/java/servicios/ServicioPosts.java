package servicios;

import java.util.List;

import java.util.Map;

import modelo.Post;

public interface ServicioPosts {

int obtenerTotalDePostsDeUsuario(long id);
int obtenerTotalDePosts(String marca);
List<Post> obtenerPosts(String marca, int comienzo);

List<Map<String, Object>>  obtenerPostsConMasValoraciones();
List<Map<String, Object>> obtenerPostsParaListadoAleatorio();
List<Map<String, Object>> obtenerPostsPorId(long id);
List<Map<String, Object>> obtenerPostsPorIdUsuario(long id);
List<Map<String, Object>> obtenerPostPorForoId(long id);

void registrarPost(Post p);
void eliminarPosts(long id);
void guardarCambiosPosts(Post p);

void eliminarPostsDeForo (long id);
void eliminarPostUsuarios(long id);
Post obtenerPostPorId(long id);

Map<String, String> obtenerPostsParaDesplegable();
Map<String, Object> obtenerPostPorIdEnMap(long id);

//funciones de ajax
List<Map<String, Object>> obtenerPostsParaListado();








}
