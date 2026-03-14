package ejercicio2;

import java.util.List;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2> {
	public Cromosoma2(String file) {
		//Inicialización de los datos
		Datos2.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Implementar segun el modelo
		// Variables para llevar el recuento
		Integer tiempoProdMax = Datos2.getTiempoProdTotal();
		Integer tiempoElabMax = Datos2.getTiempoElabTotal();
		//contadores
		Double ingresos = 0.0;
		Double tiempoProdUsado = 0.0;
		Double tiempoElabUsado = 0.0;
		Double multaPorMaxSemanal = 0.0;
		for (int i = 0; value.size() > i ; i++) {
			Integer cantidad = value.get(i);
			
			if(cantidad > 0) {
				ingresos += Datos2.getPrecioProd(i) * cantidad; 
				tiempoProdUsado += Datos2.getTiempoProdProd(i) * cantidad;
				tiempoElabUsado += Datos2.getTiempoElabProd(i) * cantidad;
				multaPorMaxSemanal += AuxiliaryAg.distanceToGeZero((double) (Datos2.getUnidsSemanaProd(i) - cantidad));
				
			}
			
		}
		//restricciones
		double k = 10000.0;
		double distanciaTimeProd = AuxiliaryAg.distanceToLeZero(tiempoProdUsado - tiempoProdMax);
		double distanciaTimeElab = AuxiliaryAg.distanceToLeZero(tiempoElabUsado - tiempoElabMax);
		return ingresos - k * (distanciaTimeProd + distanciaTimeElab + multaPorMaxSemanal);

	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value);
	}

	@Override
	public Integer max(Integer i) {
		Integer tpr = Datos2.getTiempoProdTotal();
		Integer ter = Datos2.getTiempoElabTotal();
		return Datos2.getMaxUnidadesPosibles(i, tpr, ter) + 1; // +1 porque el max es exclusivo
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

}