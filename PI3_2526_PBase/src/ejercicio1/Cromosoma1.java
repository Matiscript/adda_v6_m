package ejercicio1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ejercicio1.Datos1.Candidato;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		//Inicialización de los datos
		Datos1.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Implementar segun el modelo
		// Variables para llevar el recuento manual y súper rápido
        double valTotal = 0.0;
        double gasto = 0.0;
        Set<String> cualidadesCubiertas = new HashSet<>();
        int numIncompatibilidades = 0;
        for (int i = 0; i < value.size(); i++) {
        	if(value.get(i) > 0) {// si el candidato i esta contratado
        		Candidato c = Datos1.getCandidato(i);
        		//añadimos las cualidades del candidato i
        		valTotal += c.valoracion();
        		gasto += c.sueldo();
        		cualidadesCubiertas.addAll(c.cualidades());
        		
        		for(int j=i+1; j < value.size(); j++) {
        			if (value.get(j) > 0 && Datos1.getSonIncompatibles(i, j)) {
        				numIncompatibilidades++;
        				
        			}
        		}
        	}
        }
        
     // 2. FÓRMULA USANDO TU LIBRERÍA (Con sumas matemáticas para ajustar al 0)
        // Usamos K=1000 porque los métodos ya elevan la diferencia al cuadrado.
        double k = 1000.0; 
        
        // Distancia 1: (gasto - maximo) debe ser <= 0
        double distPresupuesto = AuxiliaryAg.distanceToLeZero(gasto - Datos1.getPresupuestoMax());
        
        // Distancia 2: (totales - cubiertas) debe ser == 0
        double distCualidades = AuxiliaryAg.distanceToEqZero((double) Datos1.getNumCualidades() - cualidadesCubiertas.size());
        
        // Distancia 3: incompatibilidades debe ser == 0
        double distIncompat = AuxiliaryAg.distanceToEqZero((double) numIncompatibilidades);
        
        // Devolvemos la valoración menos la penalización gigante
        return valTotal - k * (distPresupuesto + distCualidades + distIncompat);
    }
        
        /*METODO PARA HACERLO CON IFS (mas impreciso pero facil de entender)
         * 
         * //hora de aplicar multas a nuestros sumatorios de arriba
          double fitnessReal = valTotal;
          double penalizacion = 0.0;
        
         * restriccion1: presupuestoMAX
        double excesoGasto = gasto - Datos1.getPresupuestoMax();
       if (excesoGasto > 0) {
        	penalizacion += 1000000* excesoGasto;	
        }
        //restriccion2: cubrir todas las cualidades
        double cualidadesNoCubiertas = Datos1.getNumCualidades() - cualidadesCubiertas.size();
        if (cualidadesNoCubiertas > 0) {
        	penalizacion += 1000000 * cualidadesNoCubiertas;
        }
        //restriccion3: incompatibilidades
        if(numIncompatibilidades > 0) {
        	penalizacion += 1000000 * numIncompatibilidades;
        }
        
        return fitnessReal - penalizacion;
        
        
        */ 

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}