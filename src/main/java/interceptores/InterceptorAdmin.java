package interceptores;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class InterceptorAdmin extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//para el caso en que se identifique el admin:
		if( request.getParameter("pass")!=null &&
			request.getParameter("pass").equals("123")) {
			request.getSession().setAttribute("admin", "ok");
		}
		
		//para sucesivas llamadas a cualquier ruta en /admin
		if( request.getSession().getAttribute("admin") != null && 
			request.getSession().getAttribute("admin").equals("ok")) {
			return true;
		}else {
			Cookie[] cookies = request.getCookies();
			String pass_a_recordar = "";
			for (Cookie c : cookies) {
				if(c.getName().equals("pass_admin")) {
					pass_a_recordar = c.getValue();
					
				}
			}
			request.getSession().setAttribute("campo_pass", pass_a_recordar);
			
			response.sendRedirect("../loginAdmin.jsp");
			return false;
		}
	}

}
