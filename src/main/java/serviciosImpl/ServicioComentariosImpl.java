package serviciosImpl;





import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import constantesSQL.ConstantesSQL;
import modelo.Comentario;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioUsuarios;

@Service
@Transactional
public class ServicioComentariosImpl implements ServicioComentarios{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void registrarComentario(Comentario c) {
		try {
			c.setFechaCreacion(LocalDate.now().getDayOfMonth()+"/"+LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear());
			sessionFactory.getCurrentSession().save(c);
		}
		catch (Exception e) {
		
		}
		
	}

	@Override
	public int obtenerTotalDeComentarios(String nombre) {
		SQLQuery query = sessionFactory.getCurrentSession().
				createSQLQuery(ConstantesSQL.OBTENER_TOTAL_USUARIOS);
		query.setParameter("nombre","%"+nombre+"%");
		return Integer.parseInt(query.list().get(0).toString());
	}
	
	
	@Override
	public List<Comentario> obtenerComentarios(String nombre, int comienzo) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
		c.add(Restrictions.like("nombre", "%"+nombre+"%"));
		c.addOrder(Order.desc("id"));
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}

	@Override
	public List<Map<String, Object>> obtenerComentariosParaListado() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> obtenerComentariosPorId(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_OBTENER_DATOS_COMENTARIO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>)query.uniqueResult();
		
	}
	
	@Override
	public void guardarCambiosComentario(Comentario c) {
		sessionFactory.getCurrentSession().merge(c);
	}

	@Override
	public void eliminarComentario(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_COMENTARIO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		// TODO Auto-generated method stub
		
	}
	
	
	public void eliminarComentariosPost(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_COMENTARIOS_DE_POST);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		// TODO Auto-generated method stub
	}

	@Override
	public Comentario obtenerComentarioPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}










