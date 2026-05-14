package ejercicio1.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ejercicio1.*;
import us.lsi.common.List2;
import us.lsi.common.Map2;


public class CandidatosPD {

	public static record Spm(Integer a, Double weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Double weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	public static Map<CandidatosHyperVertex, Spm> memory;

	// Cambiamos el return para que devuelva directamente Solucion1
	public static Solucion1 search() {
		memory =  Map2.empty();
		pd_search(CandidatosHyperVertex.initial());
		return getSolucion();
	}

	// 4. EL MOTOR RECURSIVO 
	private static Spm pd_search(CandidatosHyperVertex actual) {
		Spm res = null;
		// A) ¿Ya lo he calculado? (Tiro de la memoria global)
		if (memory.containsKey(actual)) {
			res = memory.get(actual);
		// B) ¿Es el final?
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseWeight();
			if (w!=null) res = Spm.of(null,w);
			else res = null;
			memory.put(actual, res);
		// C) Si no he terminado, calculo las opciones
		} else {
			List<Spm> sps = new ArrayList<>();
			for (Integer action : actual.actions()) {
				// Reciclo el neighbors() del Hipergrafo. Como el hipergrafo devuelve una 
                // lista de subproblemas pero el nuestro es lineal, cogemos el primero: .get(0)
				CandidatosHyperVertex neighbor = actual.neighbors(action).get(0);
				
				Spm spNeighbor = pd_search(neighbor);
				
				if (spNeighbor != null) {
					//Calcular el peso
					Double pesoActual = (double) (action * Datos1.getValoracion(actual.index()));
					Spm act = Spm.of(action, spNeighbor.weight() + pesoActual);
					sps.add(act);
				}
			}
			//MAXIMIZAR
			res = sps.stream().max(Comparator.naturalOrder()).orElse(null);
			memory.put(actual, res);
		}
		return res;
	}

	// 5. DESANDAR EL CAMINO (marchatras)
	public static Solucion1 getSolucion(){
		List<Integer> acciones = List2.empty();
		CandidatosHyperVertex prob = CandidatosHyperVertex.initial();
		
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			CandidatosHyperVertex old = prob;
			acciones.add(spm.a());
			prob = old.neighbors(spm.a()).get(0);
			spm = memory.get(prob);
		}
		return Solucion1.create(acciones);
	}

}
