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
import modelo.Usuario;
import servicios.ServicioUsuarios;

@Service
@Transactional
public class ServicioUsuariosImpl implements ServicioUsuarios {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registrarUsuario(Usuario u) {
		// Esto ha estado comentado
		
		sessionFactory.getCurrentSession().save(u);

	}

	public Map<String, String> obtenerUsuariosParaDesplegable() {
		SQLQuery query = sessionFactory.getCurrentSession()
				.createSQLQuery(ConstantesSQL.SQL_OBTENER_USUARIOS_PARA_DESPLEGABLE);

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
	public boolean comprobarEmail(String email) {
		SQLQuery query = sessionFactory.getCurrentSession()
			    .createSQLQuery("SELECT COUNT(*) FROM Usuario WHERE email = :email");
			query.setParameter("email", email);
			BigInteger count = (BigInteger) query.uniqueResult();
			if (count.intValue() == 0) {
			    // Email existe ya
			    return false;
			} else {
			    // Email no existe
			    return true;
			}

	}
	public boolean comprobarEmailEditar(String email) {
		SQLQuery query = sessionFactory.getCurrentSession()
			    .createSQLQuery("SELECT COUNT(*) FROM Usuario WHERE email = :email");
			query.setParameter("email", email);
			BigInteger count = (BigInteger) query.uniqueResult();
			if (count.intValue() == 0) {
			    // Email existe ya
			    return false;
			} else {
			    // Email no existe
			    return true;
			}

	}

	@Override
	public int obtenerTotalDeUsuarios(String nombre) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.OBTENER_TOTAL_USUARIOS);
		query.setParameter("nombre", "%" + nombre + "%");
		return Integer.parseInt(query.list().get(0).toString());
	}

	@Override
	public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
		c.add(Restrictions.eq("email", email));
		c.add(Restrictions.eq("pass", pass));
		return (Usuario) c.uniqueResult();
	}

	@Override
	public List<Usuario> obtenerUsuarios(String nombre, int comienzo) {

		Criteria c = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
		c.add(Restrictions.like("nombre", "%" + nombre + "%"));
		c.addOrder(Order.desc("id"));
		c.setFirstResult(comienzo);
		c.setMaxResults(10);
		return c.list();
	}



	@Override
	public Map<String, Object> obtenerUsuarioPorId(long id) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_OBTENER_DATOS_USUARIO);
		query.setParameter("id", id);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>) query.uniqueResult();

	}

	@Override
	public void guardarCambiosUsuario(Usuario u) {
		
		
		sessionFactory.getCurrentSession().merge(u);
		
	}

	@Override
	public void eliminarUsuario(long id) {
		System.out.println("Eliminando usuario");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(ConstantesSQL.SQL_BORRAR_USUARIO);
		query.setParameter("id", id);
		query.executeUpdate();

	}

	@Override
	public Usuario obtenerUsuario(long id) {
		// TODO Auto-generated method stub
		return (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
	}

}
