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
import servicios.ServicioForos;
@Service
@Transactional
public class ServicioForosImpl implements ServicioForos{

	@Autowired
	private SessionFactory sessionFactory; // bean del hibernate-context
	
	
		
	public List<Map<String, Object>> obtenerForosParaListado() {
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_OBTENER_FOROS_PARA_LISTADO);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> res = query.list();
		return res;
	}
	

	@Override
	public Map<String, String> obtenerForosParaDesplegable() {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				ConstantesSQL.SQL_OBTENER_FOROS_PARA_DESPLEGABLE);

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
	public int obtenerTotalDeForos(String nombre) {
		SQLQuery query = sessionFactory.getCurrentSession().
				createSQLQuery(ConstantesSQL.OBTENER_TOTAL_FOROS);
		query.setParameter("nombre","%"+nombre+"%");
		return Integer.parseInt(query.list().get(0).toString());
	}
	
	@Override
	public List<Foro> obtenerForos(String nombre, int comienzo) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Foro.class);
		c.add(Restrictions.like("nombre", "%"+nombre+"%"));
		c.addOrder(Order.desc("id"));
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public void registrarForo(Foro o) {
		//SET FOREIGN_KEY_CHECKS = 0;
		sessionFactory.getCurrentSession().save(o);
	}

	@Override
	public Foro obtenerForosPorId(long id) {
		return (Foro) sessionFactory.getCurrentSession().get(Foro.class, id);
	}

	@Override
	public void borrarForo(long id) {
		System.out.println("Borrar Foro");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_FORO);		
		query.setParameter("id", id);
		query.executeUpdate();
		
		
	}
	@Override
	public Map<String, Object> obtenerForo(long id) {
		SQLQuery query = 
				sessionFactory.getCurrentSession().
					createSQLQuery(ConstantesSQL.SQL_OBTENER_DATOS_FORO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>)query.uniqueResult();
	}
	

	@Override
	public void guardarCambiosForo(Foro f) {
		sessionFactory.getCurrentSession().merge(f);
		
	}
	@Override
	public List<Long> obtenerIdPostDeForo(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_ID_POST_CON_FORO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return query.list();
	}
		
}
