package ejercicio2;

import java.util.function.Predicate;

public class ContenedoresHeuristic {
	
	public static Double heuristic(ContenedoresVertex v1, Predicate<ContenedoresVertex> goal, ContenedoresVertex v2) {
		Integer numElementosRestantes = Datos2.getNumElementos() - v1.index();
		Integer numContenedoresSinLLena = 0;
		for (Integer hueco: v1.remaining()) {
			if(hueco > 0) {
				numContenedoresSinLLena++;
			}
		}
		return (double) Math.min(numElementosRestantes, numContenedoresSinLLena);
	
	}

}
