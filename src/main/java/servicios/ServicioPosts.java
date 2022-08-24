package servicios;

import java.util.List;

import java.util.Map;

import modelo.Post;

public interface ServicioPosts {

public int obtenerTotalDePosts(String marca);
List<Post> obtenerPosts(String marca, int comienzo);
Post obtenerPostsPorId(long id);
void registrarPost(Post p);
void eliminarPosts(long id);
void guardarCambiosPosts(Post p);
List<Map<String, Object>> obtenerIdPostPorForoId(long id);
void eliminarPostsDeForo(long id);

//funciones de ajax
List<Map<String, Object>> obtenerPostsParaListado();







}
