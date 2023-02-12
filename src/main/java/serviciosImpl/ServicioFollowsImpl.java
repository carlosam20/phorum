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
import modelo.Follow;
import modelo.Foro;
import modelo.Usuario;
import servicios.ServicioFollow;

@Service
@Transactional
public class ServicioFollowsImpl implements ServicioFollow {

	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Map<String, Object>> obtenerFollowParaListado() {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.SQL_OBTENER_FOLLOWS_PARA_DESPLEGABLE);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		return res;
	}

	@Override
	public void registrarFollow(Follow fl) {
		
		Foro f = (Foro) sessionFactory.getCurrentSession().get(Foro.class, fl.getIdForo());
		fl.setForo(f);

		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, fl.getIdUsuario());
		fl.setUsuario(u);
		
		sessionFactory.getCurrentSession().save(fl);
		
	}

	@Override
	public Follow obtenerFollow(long id) {
		
		return (Follow) sessionFactory.getCurrentSession().get(Follow.class, id);
	}

	@Override
	public List<Follow> obtenerFollows(long id, int comienzo) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Follow.class);	
		if(id != 0){
			c.add(Restrictions.like("id", id));
			c.addOrder(Order.desc("id"));
		}
		
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public int obtenerTotalDeFollows(long id) {
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_TOTAL_FOLLOWS);
		query.setParameter("id", id);
		return Integer.parseInt(query.list().get(0).toString());
	}

	@Override
	public void eliminarFollow(long id) {
		System.out.println("Eliminando follow");
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_FOLLOW);
		query.setParameter("id", id);
		query.executeUpdate();

		
	}

	@Override
	public void guardarCambiosFollow(Follow fl) {
		
		Foro f = (Foro) sessionFactory.getCurrentSession().get(Foro.class, fl.getIdForo());
		fl.setForo(f);

		Usuario u = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, fl.getIdUsuario());
		fl.setUsuario(u);
		
		sessionFactory.getCurrentSession().merge(fl);
		
	}

	@Override
	public Map<String, String> obtenerFollowsParaDesplegable() {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.SQL_OBTENER_FOLLOWS_PARA_DESPLEGABLE);
				//TODO queda pensar como habria que hacerlo
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		List<Map<String, Object>> res = query.list();
		Map<String, String> valoresDesplegable = new HashMap<String, String>();

		for (Map<String, Object> map : res) {
			System.out.println("id: " + map.get("id") + " usuario" + map.get("usuario") + " foro" + map.get("foro"));
			valoresDesplegable.put(map.get("id").toString(), map.get("usuario").toString() + " usuario" + map.get("usuario"));
		}
		
		return valoresDesplegable;
	}

	@Override
	public Map<String, Object> obtenerFollowsPorId(long id) {
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_FOLLOW_POR_ID);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>) query.uniqueResult();
	}
	
}
