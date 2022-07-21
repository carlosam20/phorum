package controladores.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/")
public class ControladorInicioAdmin {

	@RequestMapping("")
	public String inicio(HttpServletRequest request, HttpServletResponse response) {
		
		//si el checkbox recordar contraseña esta activo, guardo la contraseña en 
				//una cookie
				if(request.getParameter("recordar_pass")!=null) {
					Cookie c = new Cookie("pass_admin", "123");
					c.setMaxAge(100*24*60*60);//la cookie caducará en 100 dias
					response.addCookie(c);
					System.out.println("cookie guardada con el pass de admin");
				}else {
					Cookie c = new Cookie("pass_admin", "");
					c.setMaxAge(0);
					response.addCookie(c);
					System.out.println("cookie eliminada");
					
				}
		return "admin/inicio";
	}
	
}
