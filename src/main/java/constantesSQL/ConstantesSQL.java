package constantesSQL;

public class ConstantesSQL {

	//Listar
	public static final String OBTENER_USUARIOS_PARA_LISTADO="SELECT u.id, u.email, u.pass"
			+ "from usuario as u"
			+ "order by id desc";
	
	//OBTENER LISTADOS
	public final static String SQL_OBTENER_FOROS_PARA_LISTADO="select id, nombre, descripcion, fechaCreacion "
			+ "from foro " 
			+ "order by id desc";
	
	public final static String SQL_OBTENER_POSTS_PARA_LISTADO="select id, nombre, descripcion, fechaCreacion "
			+ "from post " 
			+ "order by id desc";
	
	public final static String SQL_OBTENER_COMENTARIOS_PARA_LISTADO="select id, nombre, descripcion, fechaCreacion "
			+ "from post " 
			+ "order by id desc";
	
	//Desplegable
	public final static String SQL_OBTENER_FOROS_PARA_DESPLEGABLE = "select id, nombre from foro order by id asc";
	
	public final static String SQL_OBTENER_USUARIOS_PARA_DESPLEGABLE = "select id, nombre from usuario order by id asc";
	
	//TOTALES
	public static final String OBTENER_TOTAL_USUARIOS = "select count(id) from usuario where nombre like :nombre ";
	public static final String OBTENER_TOTAL_FOROS = "select count(id) from foro where nombre like :nombre ";
	public static final String OBTENER_TOTAL_POSTS = "select count(id) from post where nombre like :nombre";
	public static final String OBTENER_TOTAL_COMENTARIOS= "select count(id) from comentario where textoComentario like :textoComentario ";
	public static final String OBTENER_POST_POR_ID_USUARIO = "select count(p.id) from post as p  where usuario = :id";
	
	
	//DELETE
	public final static String SQL_BORRAR_USUARIO="delete from usuario where id = :id";
	public final static String SQL_BORRAR_FORO="delete from foro where id = :id";
	public final static String SQL_BORRAR_POST="delete from post where  id = :id";
	public final static String SQL_BORRAR_COMENTARIO="delete from comentario where  id = :id";
	public final static String SQL_BORRAR_POSTS_DE_FORO="delete from post where foro = :id";
	public final static String SQL_BORRAR_COMENTARIOS_DE_POST="delete from comentario where post = :id";

		
		
	//OBTENER POR ID
	public static final String OBTENER_USUARIO_POR_ID = "select * from usuario where id = :id";
	public static final String OBTENER_COMENTARIOS_POR_ID_USUARIO = "select count(c.id) from foro as c  where usuario = :id";
	public static final String SQL_OBTENER_DATOS_COMENTARIO = "select * from comentario where id = :id"; 
	public static final String SQL_OBTENER_DATOS_FORO = "select * from foro where id = :id"; 
	public static final String SQL_OBTENER_DATOS_POST = "select * from post where id = :id"; 
			
	
	
	public final static String SQL_OBTENER_DATOS_USUARIO="SELECT u.id, u.nombre, u.email, u.pass, u.descripcion, u.fechaCreacion "
			+ "from usuario as u "
			+ "where u.id = :id ";
	
	//OBTENER ID A PARTIR DE ID
	public final static String OBTENER_POST_CON_FORO="select * from post where foro = :id";
	public final static String OBTENER_ID_COMENTARIO_CON_POSTS="select id from comentario where post = :id";
		
	

}
