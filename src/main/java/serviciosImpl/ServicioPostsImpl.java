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
import modelo.Foro;
import modelo.Post;
import modelo.Usuario;

import servicios.ServicioPosts;
@Service
@Transactional
public class ServicioPostsImpl implements ServicioPosts{

	@Autowired
	private SessionFactory sessionFactory; // bean del hibernate-context
	
	
		
	public List<Map<String, Object>> obtenerPostsParaListado() {
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_OBTENER_POSTS_PARA_LISTADO);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		return res;
	}
	@Override
	public int obtenerTotalDePosts(String nombre) {
		SQLQuery query = sessionFactory.getCurrentSession().
				createSQLQuery(ConstantesSQL.OBTENER_TOTAL_POSTS);
		query.setParameter("nombre","%"+nombre+"%");
		return Integer.parseInt(query.list().get(0).toString());
	}
	
	@Override
	public List<Post> obtenerPosts(String nombre, int comienzo) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Post.class);
		c.add(Restrictions.like("nombre", "%"+nombre+"%"));
		c.addOrder(Order.desc("id"));
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public void registrarPost(Post p) {
		
		
		Foro f = (Foro) sessionFactory.getCurrentSession().get(Foro.class,p.getForo().getId());
		p.setForo(f);
		
		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class,p.getUsuario().getId());
		p.setUsuario(u);
		
		sessionFactory.getCurrentSession().save(p);
	}
	
	public List<Long> obtenerIdPostPorForoId(long id) {
		
		SQLQuery query = sessionFactory.getCurrentSession().
				createSQLQuery(ConstantesSQL.OBTENER_ID_POST_CON_FORO);
		query.setParameter("id", id);
		
		List<Long> obtenerLista = query.list();
		
		return obtenerLista;
	}

	@Override
	public Post obtenerPostsPorId(long id) {
		return (Post) sessionFactory.getCurrentSession().get(Post.class, id);
	}
	
	

	@Override
	public void eliminarPosts(long id) {
		System.out.println("Borrar Post");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_FORO);		
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	@Override
	public void eliminarPostsDeForo(long id) {
		System.out.println("Borrar Post");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_POSTS_DE_FORO);		
		query.setParameter("id", id);
		query.executeUpdate();
	}
	@Override
	public Map<String, Object> obtenerPosts(long id) {
		SQLQuery query = 
				sessionFactory.getCurrentSession().
					createSQLQuery(ConstantesSQL.SQL_OBTENER_DATOS_POST);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>)query.uniqueResult();
	}
	

	@Override
	public void guardarCambiosPosts(Post p) {
		
		//f.setCategoria(c);
		sessionFactory.getCurrentSession().merge(p);
		
	}
		
}
