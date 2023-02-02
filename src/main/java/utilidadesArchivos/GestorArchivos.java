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
		String nombreArchivo = u.getId()+".jpg";
		
		String rutaFotos = rutaReal+"/subidasUsuario";
		File fileCarpetaFotos = new File(rutaFotos);
		if( ! fileCarpetaFotos.exists()) {
			fileCarpetaFotos.mkdirs();
		}
		if(foto.getSize() > 0 && foto.getSize() < 5000000) {
			
			
			try {
				foto.transferTo(new File(rutaFotos, nombreArchivo));
				System.out.println("ruta: "+rutaFotos);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void guardarFotoUsuarioAdmin(Usuario u, String rutaReal) {
		
		MultipartFile archivo = u.getImagen();
		String nombreArchivo = u.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidasUsuario";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		//mover el archivo a dicha ruta poniendole el nombre indicado:
		if(archivo.getSize() > 0 && archivo.getSize() < 5000000) {
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
		
		
	}// end guardarImagenForoz
	
	
	
	public static void guardarImagenForo(Foro f, String rutaReal, CommonsMultipartFile foto) {
		String nombreArchivo = f.getId()+".jpg";
		
		String rutaFotos = rutaReal+"/subidas";
		File fileCarpetaFotos = new File(rutaFotos);
		if( ! fileCarpetaFotos.exists()) {
			fileCarpetaFotos.mkdirs();
		}
		if(foto.getSize() > 0 && foto.getSize() < 5000000) {
			
			
			try {
				foto.transferTo(new File(rutaFotos, nombreArchivo));
				System.out.println("ruta: "+rutaFotos);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void guardarImagenForoAdmin(Foro f, String rutaReal) {
	
		MultipartFile archivo = f.getImagen();
		String nombreArchivo = f.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidas";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		//mover el archivo a dicha ruta poniendole el nombre indicado:
		if(archivo.getSize() > 0 && archivo.getSize() < 5000000) {
			try {
				archivo.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("No se pudo mover el archivo a rutas subidas");
				e.printStackTrace();
			}
		}
		
		
		
	}// end guardarImagenForo
	
	
	public static void guardarImagenPostAdmin(Post p, String rutaReal) {
		
		MultipartFile archivo = p.getImagen();
		String nombreArchivo = p.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidasPost";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		
		if(archivo.getSize() > 0 && archivo.getSize() < 5000000) {
			try {
				archivo.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("No se pudo mover el archivo a rutas subidas");
				e.printStackTrace();
			}
		}
			
	}
	
	public static void guardarImagenPost(Post p, String rutaReal, CommonsMultipartFile foto) {
		String nombreArchivo = p.getId()+".jpg";
		
		
		String rutaSubidas = rutaReal+"/subidasPost";
		File fileRutaSubidas = new File(rutaSubidas);
		if(! fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		//mover el archivo a dicha ruta poniendole el nombre indicado:
		if(foto.getSize() > 0 && foto.getSize() < 5000000) {

			try {
				foto.transferTo(new File(rutaSubidas, nombreArchivo));
				System.out.println("archivo movido a: "+rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				
				e.printStackTrace();
			}
		}
		else {
			
		}
		
		
	}// end guardarImagenPost del usuario
	
	
	
}
