package serviciosImpl;

import java.util.HashMap;
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
		
		
		Foro f = (Foro) sessionFactory.getCurrentSession().get(Foro.class,p.getIdForo());
		p.setForo(f);
		
		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class,p.getIdUsuario());
		p.setUsuario(u);
		
		sessionFactory.getCurrentSession().save(p);
	}
	
	public List<Map<String, Object>> obtenerIdPostPorForoId(long id) {
		
		/*
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_POST_CON_FORO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		*/
		
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_POST_CON_FORO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		
		
		
		return res;
	}

	@Override
	public Post obtenerPostsPorId(long id) {
		return (Post) sessionFactory.getCurrentSession().get(Post.class, id);
	}
	
	

	@Override
	public void eliminarPosts(long id) {
		System.out.println("Borrar Post");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_POST);		
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	@Override
	public void eliminarPostsDeForo(long id) {
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_POSTS_DE_FORO);		
		query.setParameter("id", id);
		query.executeUpdate();
	}
	

	@Override
	public void guardarCambiosPosts(Post p) {
		
		Foro f = (Foro) sessionFactory.getCurrentSession().get(Foro.class,p.getIdForo());
		p.setForo(f);
		
		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class,p.getIdUsuario());
		p.setUsuario(u);
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.ACTUALIZAR_POST);		
		query.setParameter("id", p.getId());
		query.setParameter("descripcion", p.getDescripcion());
		query.setParameter("fechaCreacion", p.getFechaCreacion());
		query.setParameter("nombre", p.getNombre());
		query.setParameter("likes", p.getLikes());
		query.setParameter("foro", p.getForo());
		query.setParameter("usuario", p.getUsuario());
		query.executeUpdate();
		
		//sessionFactory.getCurrentSession().merge(p);
		
	}
	@Override
	public Map<String, String> obtenerPostsParaDesplegable() {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				ConstantesSQL.SQL_OBTENER_POSTS_PARA_DESPLEGABLE);

		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		List <Map<String, Object>> res =query.list();
		Map<String, String> valoresDesplegable = new HashMap<String, String>();
		
		for (Map<String, Object> map : res) {
			System.out.println("id: "+map.get("id") + " nombre" +map.get("nombre"));
			valoresDesplegable.put(map.get("id").toString(), map.get("nombre").toString());
		}
		return valoresDesplegable;
	}
	@Override
	public void eliminarPostUsuarios(long id) {
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_POSTS_DE_USUARIO);		
		query.setParameter("id", id);
		query.executeUpdate();
		
	}
		
}
