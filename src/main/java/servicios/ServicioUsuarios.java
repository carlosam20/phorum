package servicios;

import java.util.List;
import java.util.Map;

import modelo.Usuario;

public interface ServicioUsuarios {

	boolean comprobarEmail(String email);

	void registrarUsuario(Usuario u);

	Usuario obtenerUsuarioPorEmailYpass(String email, String pass);

	Map<String, Object> obtenerUsuarioPorId(long id);

	Usuario obtenerUsuario(long id);

	List<Usuario> obtenerUsuarios(String nombre, int comienzo);

	int obtenerTotalDeUsuarios(String nombre);

	void eliminarUsuario(long id);

	void guardarCambiosUsuario(Usuario l);

	Map<String, String> obtenerUsuariosParaDesplegable();

}
