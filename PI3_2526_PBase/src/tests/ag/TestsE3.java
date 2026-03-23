package tests.ag;

import java.util.List;

import ejercicio3.Cromosoma3;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestsE3 {

	public static void main(String[] args) {
		AlgoritmoAG.POPULATION_SIZE = 100000; //Aumentar si no obtiene res optimo
		StoppingConditionFactory.NUM_GENERATIONS = 10000000; // El doble de tiempo para pensar. aumentar si no optimo
		
		
		List.of(1,2,3).forEach(i -> { // indique los tests a realizar
			var cr = new Cromosoma3("datos_entrada/ejercicio3/DatosEntrada"+i+".txt");
			var ag = AlgoritmoAG.of(cr);
			ag.ejecuta();
			
			System.out.println("================================");
			System.out.println(ag.bestSolution());
			System.out.println("================================");
		});
	}	
}