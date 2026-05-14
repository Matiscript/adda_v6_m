package ejercicio1;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class CandidatosHeuristic {
	// Lo mas optimista: los alumnos que quedan estaran en el mejor grupo posible con hueco
		public static Double heuristic(CandidatosVertex v1, Predicate<CandidatosVertex> goal, CandidatosVertex v2) {
			if (v1.remaining().isEmpty()) {
				return 0.0;
			}
			return IntStream.range(v1.index(), Datos1.getNumCandidatos())
					.filter(i-> aportaCualidadNueva(i,v1.remaining()))
					.mapToDouble(i-> Datos1.getValoracion(i))
					.sum();
		}

		private static Boolean aportaCualidadNueva(int i, Set<String> remaining) {
			List<String> cualidadesCandidato = Datos1.getCualidades(i);
			for (String cualidad: remaining) {
				if (cualidadesCandidato.contains(cualidad)) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}
	}