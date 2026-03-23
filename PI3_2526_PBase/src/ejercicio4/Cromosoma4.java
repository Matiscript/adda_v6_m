package ejercicio4;

import java.util.List;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma4 implements PermutationData<Solucion4> {
	public Cromosoma4(String file) {
		//Inicialización de los datos
		Datos4.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return Datos4.N;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// Variables para llevar el recuento
		Double beneficio = 0.0;
		Double duracionTrayecto = 0.0;
		Double esfuerzoTrayecto = 0.0;
		for (int i = 0; value.size() > i; i++) {
				int origen = value.get(i);
				int destino;
				if (i == value.size() -1) {
					destino = value.get(0); //el destino es el primero
				} else {
					destino = value.get(i + 1); // si no, el destino sera el siguiente
				}
				if (Datos4.sonMonumentos(origen, destino)) {
					beneficio++;
				}
				duracionTrayecto += Datos4.tiempo(origen, destino);
				esfuerzoTrayecto += Datos4.esfuerzo(origen, destino);
		}
		Double multaDuracion = AuxiliaryAg.distanceToGeZero(Datos4.maxTime - duracionTrayecto );
		Double multaMonumentos =0.0;
		if(beneficio < 1) {
			multaMonumentos++;
		}
		return -esfuerzoTrayecto - 100000.0 * (multaDuracion + multaMonumentos);		
			
		}
	

	@Override
	public Solucion4 solution(List<Integer> value) {
		return Solucion4.create(value);
	}

}