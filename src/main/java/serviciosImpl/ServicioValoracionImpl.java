package serviciosImpl;

import java.math.BigInteger;
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
import modelo.Post;
import modelo.Usuario;
import modelo.Valoracion;
import servicios.ServicioValoracion;

@Service
@Transactional
public class ServicioValoracionImpl implements ServicioValoracion {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registrarValoracion(Valoracion v) {
	

		Post p = (Post) sessionFactory.getCurrentSession().get(Post.class, v.getIdPost());
		v.setPost(p);

		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, v.getIdUsuario());
		v.setUsuario(u);
		
		sessionFactory.getCurrentSession().save(v);

	}

	public Map<String, String> obtenerValoracionesParaDesplegable() {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.SQL_OBTENER_USUARIOS_PARA_DESPLEGABLE);
		// TODO Sacar el valor previamente seleccionado de alguna forma puede que se haga en el controlador
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
		List<Map<String, Object>> res = query.list();
		Map<String, String> valoresDesplegable = new HashMap<String, String>();

		for (Map<String, Object> map : res) {
			System.out.println("id: " + map.get("id") + " nombre" + map.get("nombre"));
			valoresDesplegable.put(map.get("id").toString(), map.get("nombre").toString());
		}
		return valoresDesplegable;
	}

	@Override
	public int obtenerTotalValoraciones() {

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_TOTAL_VALORACIONES);
		return Integer.parseInt(query.list().get(0).toString());
	}

	@Override
	public int obtenerTotalValoracionesPostLike(long idPost) {

		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.OBTENER_TOTAL_VALORACIONES_POST_LIKE);
		query.setParameter("idPost", idPost);
		return Integer.parseInt(query.list().get(0).toString());
	}

	@Override
	public int obtenerTotalValoracionesPostDislike(long idPost) {

		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.OBTENER_TOTAL_VALORACIONES_POST_DISLIKE);
		query.setParameter("idPost", idPost);
		return Integer.parseInt(query.list().get(0).toString());
	}

	@Override
	public List<Valoracion> obtenerValoraciones(long id, int comienzo) {

		Criteria c = sessionFactory.getCurrentSession().createCriteria(Valoracion.class);
		if (id != 0) {
			c.add(Restrictions.like("id", id));
			c.addOrder(Order.desc("id"));
		}
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public Map<String, Object> obtenerValoracionesPorId(long id) {

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_VALORACION_POR_ID);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>) query.uniqueResult();

	}

	@Override
	public void guardarCambiosValoraciones(Valoracion v) {

		Post p = (Post) sessionFactory.getCurrentSession().get(Post.class, v.getIdPost());
		v.setPost(p);

		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, v.getIdUsuario());
		v.setUsuario(u);

		sessionFactory.getCurrentSession().merge(v);

	}

	@Override
	public void eliminaValoraciones(long id) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_VALORACION);
		query.setParameter("id", id);
		query.executeUpdate();

	}
	
	@Override
	public void eliminaValoracionesPorUsuario(long id) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_VALORACIONES_DE_USUARIO);
		query.setParameter("id", id);
		query.executeUpdate();

	}
	

	@Override
	public void eliminaValoracionesPorPost(long id) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_VALORACIONES_DE_POST);
		query.setParameter("id", id);
		query.executeUpdate();

	}

	@Override
	public Valoracion obtenerValoracion(long id) {
		return (Valoracion) sessionFactory.getCurrentSession().get(Valoracion.class, id);
	}

	@Override
	public List<Map<String, Object>> obtenerValoracionPorPostId(long id) {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.OBTENER_VALORACION_POR_ID_POST);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		return res;
	}
	
	@Override
	public Map<String, Object>obtenerValoracionPorPostIdYPorUsuarioId(long id, long idPost) {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.OBTENER_VALORACION_POR_ID_POST_Y_POR_ID_USUARIO);
		query.setParameter("id", id);
		query.setParameter("idPost", idPost);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>) query.uniqueResult();
	}
	
	

	@Override
	public boolean[] comprobarExisteValoracion(long idPost, long idUsuario) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.COMPROBAR_EXISTE_VALORACION);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idPost", idPost);
		Object result = query.uniqueResult();	
		
		boolean[]existeYValor = new boolean[2];
		
		if (result != null) {
		    if (result instanceof BigInteger) {
		    	existeYValor[0] = ((BigInteger) result).intValue() > 0;
		    } else if (result instanceof Integer) {
		    	existeYValor[0] = ((Integer) result).intValue() > 0;
		    } else {
		        throw new IllegalStateException("fallo en query " + result.getClass().getName());
		    }
		}
		
		if(existeYValor[0]) {
			SQLQuery queryValor = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_VALORACION_POR_ID_POST_Y_POR_ID_USUARIO);
			queryValor.setParameter("id", idUsuario);
			queryValor.setParameter("idPost", idPost);
			queryValor.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			Map<String, Object> valorMap = (Map<String, Object>) queryValor.uniqueResult();
			existeYValor[1] = (boolean) valorMap.get("valor");
			
			System.out.println((boolean) valorMap.get("valor"));
		}

		return existeYValor;
	}
}
