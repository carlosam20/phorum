package constantesSQL;

public class ConstantesSQL {

	//LISTADOS
	public static final String OBTENER_USUARIOS_PARA_LISTADO="select *"
			+ "from usuario"
			+ "order by id desc";
	public final static String SQL_OBTENER_FOROS_PARA_LISTADO="select * "
			+ "from foro " 
			+ "order by id desc";
	public final static String SQL_OBTENER_FOROS_PARA_LISTADO_RANDOM="select id, nombre, descripcion, fechaCreacion  "
			+ "from foro " 
			+ "order by rand(id)";
	public final static String SQL_OBTENER_FOROS_POR_NOMBRE_LISTADO="select id, nombre, descripcion, fechaCreacion  "
			+ "from foro where nombre like :nombreForo "
			+ "order by nombre desc";
	
	public static final String OBTENER_VALORACION_PARA_LISTADO="select *"
			+ "from valoracion"
			+ "order by id desc";
	
	public static final String OBTENER_FOLLOW_PARA_LISTADO="select *"
			+ "from usuario"
			+ "order by id desc";
	
	
	public final static String SQL_OBTENER_POSTS_PARA_LISTADO="select *"
			+ "from post " 
			+ "order by id desc";
	
	public final static String SQL_OBTENER_POSTS_PARA_LISTADO_RANDOM="select * from post order by rand(id) ";
			
	public final static String SQL_OBTENER_TOP3_VALORACIONES_DE_POST="select * from valoracion group by post order by sum(post) asc LIMIT 3";
	
	public final static String SQL_OBTENER_COMENTARIOS_PARA_LISTADO="select * "
			+ "from comentario " 
			+ "order by id desc";
	
	public final static String SQL_OBTENER_POST_REALIZADOR_POR_USUARIO="select * from post where usuario = :id";
	
	
	//DESPLEGABLES
	public final static String SQL_OBTENER_FOROS_PARA_DESPLEGABLE = "select id, nombre from foro order by id asc";
	public final static String SQL_OBTENER_POSTS_PARA_DESPLEGABLE = "select id, nombre from post order by id asc";
	public final static String SQL_OBTENER_USUARIOS_PARA_DESPLEGABLE = "select id, nombre from usuario order by id asc";
	public final static String SQL_OBTENER_VALORACIONES_PARA_DESPLEGABLE = "select id, nombre from valoracion order by id asc";
	public final static String SQL_OBTENER_FOLLOWS_PARA_DESPLEGABLE = "select id, nombre from follow order by id asc";
	
	//TOTALES
	public static final String OBTENER_TOTAL_USUARIOS = "select count(id) from usuario where nombre like :nombre ";
	public static final String OBTENER_TOTAL_FOROS = "select count(id) from foro where nombre like :nombre ";
	public static final String OBTENER_TOTAL_POSTS = "select count(id) from post where nombre like :nombre";
	public static final String OBTENER_TOTAL_COMENTARIOS= "select count(id) from comentario where textoComentario like :textoComentario ";
	public static final String OBTENER_TOTAL_COMENTARIOS_DE_POST= "select count(id) from comentario where postComentario like :postComentario";
	
	public static final String OBTENER_TOTAL_VALORACIONES = "select count(id) from valoracion where id like :id";
	public static final String OBTENER_TOTAL_FOLLOWS = "select count(id) from follow where id like :id";
	public static final String OBTENER_TOTAL_FOLLOWS_DE_USUARIO = "select count(id) from follow where usuario like :id";
	
	public static final String OBTENER_TOTAL_VALORACIONES_POST_LIKE = "select count(id) from valoracion where post =:idPost AND valor = 1";
	public static final String OBTENER_TOTAL_VALORACIONES_POST_DISLIKE = "select count(id) from valoracion where post =:idPost AND valor = 0";
	
	public static final String OBTENER_TOTAL_COMENTARIOS_USUARIO= "select count(id) from comentario where usuario like :id ";
	public static final String OBTENER_TOTAL_POST_USUARIO= "select count(id) from comentario where usuario like :id ";
	public static final String OBTENER_TOTAL_FORO_USUARIO= "select count(id) from comentario where usuario like :id ";
	
	public static final String OBTENER_POST_POR_ID_USUARIO = "select count(p.id) from post as p  where usuario = :id";
	
	
	//DELETE
	public final static String SQL_BORRAR_USUARIO="delete from usuario where id = :id";
	public final static String SQL_BORRAR_FORO="delete from foro where id = :id";
	public final static String SQL_BORRAR_POST="delete from post where  id = :id";
	public final static String SQL_BORRAR_COMENTARIO="delete from comentario where  id = :id";
	public final static String SQL_BORRAR_FOLLOW="delete from follow where id = :id";
	public final static String SQL_BORRAR_FOLLOWS_DE_USUARIO="delete from follow where usuario = :id";
	public final static String SQL_BORRAR_VALORACION="delete from valoracion where id = :id";
	public final static String SQL_BORRAR_VALORACIONES_DE_USUARIO="delete from valoracion where usuario = :id";
	public final static String SQL_BORRAR_POSTS_DE_FORO="delete from post where foro = :id";
	public final static String SQL_BORRAR_POSTS_DE_USUARIO="delete from post where usuario = :id";
	public final static String SQL_BORRAR_COMENTARIOS_DE_POST="delete from comentario where postComentario = :id";

	
	//UPDATES
	public static final String ACTUALIZAR_POST = "update post SET id= :id, descripcion= :descripcion, fechaCreacion = :fechaCreacion, nombre = :nombre, foro = :foro , usuario = :usuario WHERE id = :id";
	public static final String ACTUALIZAR_USUARIO = "update usuario SET id = :id, descripcion= :descripcion, email = :email, fechaCreacion= :fechaCreacion, nombre = :nombre, pass = :pass,  WHERE id = :id";
	
	
		
	//OBTENER POR ID
	public static final String OBTENER_USUARIO_POR_ID = "select * from usuario where id = :id";
	public static final String OBTENER_VALORACION_POR_ID = "select * from valoracion where id = :id";
	public static final String OBTENER_FOLLOW_POR_ID = "select * from follow where id = :id";
	public static final String OBTENER_COMENTARIOS_POR_ID_USUARIO = "select count(c.id) from foro as c  where usuario = :id";
	public final static String OBTENER_COMENTARIOS_CON_ID_POST="select * from comentario where postComentario = :id";
	public final static String OBTENER_VALORACION_POR_ID_POST ="select * from valoracion where post = :id";
	
	public final static String OBTENER_FOLLOW_POR_USUARIO_Y_FORO = "select * from follow where usuario = :idUsuario AND foro = :idForo";
	
	public final static String OBTENER_VALORACION_POR_ID_POST_Y_POR_ID_USUARIO ="select * from valoracion where post = :idPost AND usuario = :id";
	public final static String COMPROBAR_EXISTE_VALORACION = "select count(*) > 0 FROM valoracion WHERE usuario = :idUsuario AND post = :idPost";
	public final static String COMPROBAR_EXISTE_FOLLOW = "select count(*) > 0 FROM follow WHERE usuario = :idUsuario AND foro = :idForo";
	
	
	public static final String SQL_OBTENER_DATOS_COMENTARIO = "select * from comentario where id = :id"; 
	public static final String SQL_OBTENER_DATOS_FORO = "select * from foro where id = :id"; 
	public static final String SQL_OBTENER_DATOS_POST = "select * from post where id = :id"; 
	public static final String SQL_OBTENER_DATOS_USUARIO="select * from usuario where id = :id ";
			
	
	//OBTENER ID A PARTIR DE ID
	public final static String OBTENER_POST_CON_FORO="select * from post where foro = :id";
	
		
	

}
