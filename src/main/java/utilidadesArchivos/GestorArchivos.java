package utilidadesArchivos;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import modelo.Foro;
import modelo.Post;
import modelo.Usuario;


public class GestorArchivos {
	
	public static void guardarFotoUsuario(Usuario u, String rutaReal, CommonsMultipartFile foto) {
		MultipartFile archivo = u.getImagen();
		String nombreArchivo = u.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidasUsuario";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		
		if(archivo.getSize() > 0) {
			try {
				archivo.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("No pude mover el archivo a la ruta de subidas");
				e.printStackTrace();
			}
		}	
	}
	

	public static void guardarImagenForo(Foro f, String rutaReal, CommonsMultipartFile foto) {
	
		MultipartFile archivo = f.getImagen();
		String nombreArchivo = f.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidas";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		//mover el archivo a dicha ruta poniendole el nombre indicado:
		if(archivo.getSize() > 0) {
			try {
				archivo.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("No pude mover el archivo a la ruta de subidas");
				e.printStackTrace();
			}
		}
		else {
			
		}
		
		
	}// end guardarImagenForo
	
	
	public static void guardarImagenPost(Post p, String rutaReal, CommonsMultipartFile foto) {
		
		MultipartFile archivo = p.getImagen();
		String nombreArchivo = p.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidasPost";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		
		if(archivo.getSize() > 0) {
			try {
				archivo.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("No pude mover el archivo a la ruta de subidas");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Ordenador sin imagen de momento es opcional");
		}
		
		
	}
	
	
	
}
