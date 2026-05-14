package ejercicio1.manual;

import ejercicio1.*;

public class CandidatosBT {

	private static Double mejorValor;
	private static CandidatosState estado;
	private static Solucion1 solucion;
	
	public static void search() {
		solucion = null;
		mejorValor = -100000.0; // Estamos MAXIMIZANDO (buscando un valor mayor que este siempre)
		estado = CandidatosState.of(CandidatosVertex.initial());
		bt_search();
	}

	private static void bt_search() {
		// PASO 1: ¿Hemos llegado a la hoja (último candidato)?
	    if (estado.esTerminal()) {
	     
	        // PASO 2: Ahora que hemos terminado, ¿lo que elegimos cumple las reglas?
	        if (estado.esSolucion()) {
	            Double valorObtenido = estado.valorAcumulado();
	            
	            // PASO 3 (MAXIMIZAR): ¿Es mejor que mi récord?
	            if (valorObtenido > mejorValor) {  
	                mejorValor = valorObtenido;
	                solucion = estado.getSolucion();
	            }
	        }
	        
	    // PASO 4: Si no es terminal, seguimos construyendo el árbol
	    } else {
	        for (Integer a: estado.alternativas()) {
	            
	            // PASO 5 (PODA MAXIMIZAR): ¿Tiene futuro esta rama?
	            if (estado.cota(a) > mejorValor) {  
	                estado.forward(a);
	                bt_search();
	                estado.back(a);
	            }
	        }
	    }
	}

	public static Solucion1 getSolucion() {
		return solucion;
	}

}
