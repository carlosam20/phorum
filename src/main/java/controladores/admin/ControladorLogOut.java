package controladores.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/")
public class ControladorLogOut {

	@RequestMapping("logOut")
	public String logOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return "admin/logOut";
	}
	
}
