package tests;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import modelo.Usuario;
import servicios.ServicioUsuarios;
import serviciosWEB.identificado.ServicioWebUsuarios;

@RunWith(MockitoJUnitRunner.class)
class WebUsuarios {
	
	   @Mock
	    private ServicioUsuarios servicioUsuarios;

	    @InjectMocks
	    private ServicioWebUsuarios servicioWebUsuarios;

//	    
//	    @Test
//	    public void testEditarUsuarioPorId() throws Exception {
//	        // Set up request parameters
//	        Map<String, Object> formData = new HashMap()<>();
//	        formData.put("id", "1");
//	        formData.put("nombre", "Carlos");
//	        formData.put("pass", "carlos@24Elcarlos");
//	        formData.put("param2", "value2");
//	        formData.put("param2", "value2");
//	        formData.put("param2", "value2");
//
//	        CommonsMultipartFile foto = mock(CommonsMultipartFile.class);
//
//	        HttpServletRequest request = mock(HttpServletRequest.class);
//	        HttpSession session = mock(HttpSession.class);
//	        Usuario usuario = new Usuario();
//	        usuario.setId(1L);
//	        when(request.getSession()).thenReturn(session);
//	        when(session.getAttribute("usuario")).thenReturn(usuario);
//
//	        // Set up service method mock
//	        doNothing().when(servicioUsuarios).guardarCambiosUsuario((Usuario) any(Usuario.class));
//
//	        // Call controller method
//	        ResponseEntity<String> response = servicioWebUsuarios.editarUsuarioPorId(formData, foto, request);
//
//	        // Assert response
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//	        assertEquals("ok", response.getBody());
//	    }
	    
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		
	}
	
	

}
