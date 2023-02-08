package serviciosImpl;



import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import constantesSQL.ConstantesSQL;
import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;
import servicios.ServicioComentarios;

@Service
@Transactional
public class ServicioComentariosImpl implements ServicioComentarios{

	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Map<String, Object>> obtenerComentariosParaListado() {
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_OBTENER_COMENTARIOS_PARA_LISTADO);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		
		return res;
	}

	@Override
	public int obtenerTotalDeComentarios(String textoComentario) {
		SQLQuery query = sessionFactory.getCurrentSession().
		createSQLQuery(ConstantesSQL.OBTENER_TOTAL_COMENTARIOS);
		query.setParameter("textoComentario","%"+textoComentario+"%");
		
		return Integer.parseInt(query.list().get(0).toString());
		
	}
	
	public int obtenerTotalDeComentariosDeUsuario(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().
		createSQLQuery(ConstantesSQL.OBTENER_TOTAL_COMENTARIOS_USUARIO);
		query.setParameter("id", id);
		
		return Integer.parseInt(query.list().get(0).toString());
		
	}

	@Override
	public List<Comentario> obtenerComentarios(String textoComentario, int comienzo) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Comentario.class);
		c.add(Restrictions.like("textoComentario", "%"+textoComentario+"%"));
		c.addOrder(Order.desc("id"));
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public Comentario obtenerComentariosPorId(long id) {
		return (Comentario) sessionFactory.getCurrentSession().get(Comentario.class, id);
	}

	@Override
	public List<Long> obtenerIdComentariosDePost(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_POST_CON_FORO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return query.list();
	}

	@Override
	public void registrarComentario(Comentario c) {
		
		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, c.getIdUsuario()); 
		c.setUsuario(u);
		
		Post p = (Post) sessionFactory.getCurrentSession().get(Post.class, c.getIdPostComentario());
		c.setPostComentario(p);
		
		
		sessionFactory.getCurrentSession().save(c);
		
	}

	@Override
	public void borrarComentario(long id) {
		System.out.println("Borrar Comentario");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_COMENTARIO);		
		query.setParameter("id", id);
		query.executeUpdate();
		
	}

	@Override
	public void guardarCambiosComentario(Comentario c) {
		
		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, c.getIdUsuario()); 
		c.setUsuario(u);
		
		Post p = (Post) sessionFactory.getCurrentSession().get(Post.class, c.getIdPostComentario());
		c.setPostComentario(p);
		
		sessionFactory.getCurrentSession().merge(c);
		
	}

	@Override
	public Map<String, Object> obtenerComentario(long id) {
		SQLQuery query = 
				sessionFactory.getCurrentSession().
					createSQLQuery(ConstantesSQL.SQL_OBTENER_DATOS_COMENTARIO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>)query.uniqueResult();
	}

	@Override
	public void borrarComentariosPorIdUsuario(long id) {
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_COMENTARIO);		
		query.setParameter("id", id);
		query.executeUpdate();
		System.out.println("Borrar Comentario por ID usuario");
		
	}

	@Override
	public void borrarComentariosPoridPost(long id) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_COMENTARIOS_DE_POST);		
		query.setParameter("id", id);
		query.executeUpdate();
		System.out.println("Borrar comentarios Id post");
	}
	
	@Override
	public List<Map<String, Object>> obtenerComentariosPost(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_COMENTARIOS_CON_ID_POST);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		
		return res;
	}

	
	
	
}










