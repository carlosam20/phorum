package interceptores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import modelo.Usuario;

public class InterceptorIdentificado extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Usuario u = (Usuario)request.getSession().getAttribute("usuario");
		if( u!=null ) {
			return true;
		}else {
			response.sendRedirect("../../error_identificacion.jsp");
			return false;
		}
		
	}

	
}
