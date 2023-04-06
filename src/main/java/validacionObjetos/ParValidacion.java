package validacionObjetos;

public class ParValidacion {
	private boolean resultado;
	private  String respuesta;

	public ParValidacion(boolean resultado, String respuesta) {
		this.resultado = resultado;
		this.respuesta = respuesta;
	}

	public boolean getResultado() {
		return resultado;
	}

	public String getRespuesta() {
		return respuesta;
	}
	
	public void setResultado(boolean resultado){
		this.resultado=resultado;
	}
	public void setRespuesta(String respuesta){
		this.respuesta=respuesta;
	}
	
	
}
