package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import junit.framework.Assert;
import modelo.Post;
import modelo.Usuario;
import servicios.ServicioComentarios;
import servicios.ServicioPosts;
import servicios.ServicioUsuarios;
import servicios.ServicioValoracion;
import serviciosWEB.ServicioWebPosts;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Post.class })
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class WebPosts {

	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
    @Mock
    private ServicioPosts servicioPosts;

    @Mock
    private ServicioComentarios servicioComentarios;

    @Mock
    private ServicioValoracion servicioValoraciones;

    @Mock
    private ServicioUsuarios servicioUsuarios;

    @Mock
    private HttpServletRequest request;
    
    @InjectMocks
    private ServicioWebPosts servicioWebPosts;
    
    private MockMvc mockMvc;
    
    
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    

    @Test
    public void obtenerPostYComentariosPorIdTest() throws Exception {
        
    }
    
    
}
