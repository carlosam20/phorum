package parseo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaParaUsuario {

	public static String parseoDeFecha(Date fechaCreacion) {
		Date fechaInstante = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInstante);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		fechaInstante = calendar.getTime();

		// Pasamos los datos a calendario
		Calendar fechaActual = new GregorianCalendar();
		fechaActual.setTime(fechaCreacion);

		Calendar fechaCreacionCalendar = new GregorianCalendar();
		fechaCreacionCalendar.setTime(fechaInstante);

		// Datos de la fecha anterior a la actual
		int anyoCreacion = fechaCreacionCalendar.get(Calendar.YEAR);
		int mesCreacion = fechaCreacionCalendar.get(Calendar.MONTH);
		int diaCreacion = fechaCreacionCalendar.get(Calendar.DAY_OF_MONTH);

		// Datos de la fecha actual
		int anyoActual = fechaActual.get(Calendar.YEAR);
		int mesActual = fechaActual.get(Calendar.MONTH);
		int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);

		// Realizamos las operaciones para calcular la diferencia
		int anyos = anyoCreacion - anyoActual;
		int meses = mesCreacion - mesActual;
		int dias = diaCreacion - diaActual;

		if (meses < 0) {
			anyos--;
			meses += 12;
		}

		if (dias < 0) {
			meses--;
			dias += fechaCreacionCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}

		if (anyos <= 0) {
		    if (meses == 1 && dias > 1) {
		        return String.format("%d mes y %d dias", meses, dias);
		    } else if (meses > 1 && dias == 1) {
		        return String.format("%d meses y %d dia", meses, dias);
		    } else if (meses == 1 && dias == 1) {
		        return String.format("%d mes y %d dia", meses, dias);
		    } else if(dias == 1){
		        return String.format("%d dia", dias);
		    }else {
		    	return String.format("%d dias", dias);
		    }
		} else if (anyos == 1) {
		    if (meses == 1 && dias == 1) {
		        return String.format("%d año, %d mes y %d dia", anyos, meses, dias);
		    } else if (meses == 1 && dias > 1) {
		        return String.format("%d año, %d mes y %d dias", anyos, meses, dias);
		    } else if (meses > 1 && dias == 1) {
		        return String.format("%d año, %d meses y %d dia", anyos, meses, dias);
		    } else if (meses > 1 && dias > 1) {
		        return String.format("%d año, %d meses y %d dias", anyos, meses, dias);
		    } else {
		        return String.format("%d año", anyos);
		    }
		} else {
		    if (meses == 1 && dias == 1) {
		        return String.format("%d años, %d mes y %d dia", anyos, meses, dias);
		    } else if (meses == 1 && dias > 1) {
		        return String.format("%d años, %d mes y %d dias", anyos, meses, dias);
		    } else if (meses > 1 && dias == 1) {
		        return String.format("%d años, %d meses y %d dia", anyos, meses, dias);
		    } else if (meses > 1 && dias > 1) {
		        return String.format("%d años, %d meses y %d dias", anyos, meses, dias);
		    } else {
		        return String.format("%d años", anyos);
		    }
		}

	}

}
