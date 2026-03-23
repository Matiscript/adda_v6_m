package ejercicio3;

import java.util.List;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3> {
	public Cromosoma3(String file) {
		//Inicialización de los datos
		Datos3.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return Datos3.getNumElementos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Implementar segun el modelo
		// Variables para llevar el recuento
		Integer multaIncompatible = 0;
		Double multaCapacidad = 0.0;
		Double objetivo = 0.0;
		
		int[] sumaTamaños = new int[Datos3.getNumContenedores()];
		//contadores
		for (int i=0; i< value.size() ; i++) {
			int j = value.get(i);
			if (Datos3.getNumContenedores() > j) {
				sumaTamaños[j] += Datos3.getTamElemento(i);
				if (Datos3.getNoPuedeUbicarse(i, j)) {
					multaIncompatible++;
				}
			}
		}
		for (int j = 0; j< Datos3.getNumContenedores(); j++) {
			double distanciaTamCont = AuxiliaryAg.distanceToGeZero((double) (Datos3.getTamContenedor(j) - sumaTamaños[j]));
			multaCapacidad += distanciaTamCont;
			
				if(sumaTamaños[j] == Datos3.getTamContenedor(j)) {
					objetivo += 10.0;
			}}
			
		return objetivo - 100000.0 * (multaCapacidad + multaIncompatible);		
			
		}
	

	@Override
	public Solucion3 solution(List<Integer> value) {
		return Solucion3.create(value);
	}

	@Override
	public Integer max(Integer i) {
		return Datos3.getNumContenedores() + 1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

}