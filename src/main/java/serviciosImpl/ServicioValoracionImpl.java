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
import modelo.Valoracion;
import servicios.ServicioValoracion;

@Service
@Transactional
public class ServicioValoracionImpl implements ServicioValoracion {

	
		@Autowired
		private SessionFactory sessionFactory;
		
		@Override
		public void registrarValoracion(Valoracion v) {
			// Esto ha estado comentado
			sessionFactory.getCurrentSession().save(v);

		}

		public Map<String, String> obtenerValoracionesParaDesplegable() {
			SQLQuery query = sessionFactory.getCurrentSession()
					.createSQLQuery(ConstantesSQL.SQL_OBTENER_USUARIOS_PARA_DESPLEGABLE);
					//TODO cambiar query y queda pensar como habria que hacerlo
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
		public int obtenerTotalValoraciones(long id) {
			
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_TOTAL_VALORACIONES);
			query.setParameter("id", id);
			return Integer.parseInt(query.list().get(0).toString());
		}
		@Override
		public List<Valoracion> obtenerValoraciones(long id, int comienzo) {

			Criteria c = sessionFactory.getCurrentSession().createCriteria(Valoracion.class);
			c.add(Restrictions.like("id", id));
			c.addOrder(Order.desc("id"));
			c.setFirstResult(comienzo);
			c.setMaxResults(10);
			return c.list();
		}

		@Override
		public List<Map<String, Object>> obtenerValoracionParaListado() {
		
			return null;
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
			sessionFactory.getCurrentSession().merge(v);
			
		}

		@Override
		public void eliminaValoraciones(long id) {
			System.out.println("Eliminando valoracion");
			
			Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_VALORACION);
			query.setParameter("id", id);
			query.executeUpdate();

		}

		@Override
		public Valoracion obtenerValoracion(long id) {
		
			return (Valoracion) sessionFactory.getCurrentSession().get(Valoracion.class, id);
		}
}
