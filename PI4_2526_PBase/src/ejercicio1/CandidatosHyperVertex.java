package ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import us.lsi.hypergraphs.VirtualHyperVertex;


public record CandidatosHyperVertex(Integer index, Set<String> remaining, 
		Double presupuestoRestante, Set<Integer> vetados) 
	implements VirtualHyperVertex<CandidatosHyperVertex, CandidatosHyperEdge, Integer, List<Integer>>{
	
	//este tenemos que hacerlo, la mayoria de funciones se pueden generar a partir de este constructor
		public static CandidatosHyperVertex initial() {
			return of(0,  //index empieza en 0
					Datos1.getCualidades(), //las cualidades son un set todas las que faltan
					(double)Datos1.getPresupuestoMax(), //el presupuesto restante inicial es el maximo
					new HashSet<>() //inicialmente no hay ninguno vetado	
					);
		}
		
	private static CandidatosHyperVertex of(int i, Set<String> cualidades, double presupuestoMax,Set<Integer> vetados) {
		return new CandidatosHyperVertex(i,cualidades,presupuestoMax, vetados); 
	}
	
	@Override
	public List<Integer> actions() {
		//preparamos la lista vacia de alternativas, 
		//y la llenamos con los indices de los candidatos que cumplen las condiciones
		List<Integer> caminosAbiertos = new ArrayList<>();		
		// 2. Si ya no quedan candidatos, no hay más puertas. Se acabó la casa.
		if(index >= Datos1.getNumCandidatos()) {
			return caminosAbiertos;
		} 
		//el candidato i esta vetado ?
		boolean estaVetado = vetados.contains(index);
		//Tenemos dinero para contratarlo?
		Double sueldoCandidato = Datos1.getSueldoMin(index);
		boolean quedaDinero = sueldoCandidato <= presupuestoRestante;
		
		if (quedaDinero && !estaVetado) {
			caminosAbiertos.add(1);
		}
		//La Puerta 0 (No contratar): Siempre se puede abrir. La metemos en la lista.
		caminosAbiertos.add(0);
				
		return caminosAbiertos;
		}

	@Override
	public List<CandidatosHyperVertex> neighbors(Integer a) {
		if (a == 0) {
			return List.of(CandidatosHyperVertex.of(index+1, remaining, presupuestoRestante, vetados));
		}
		//Si contrato
		Double presupuestoTotal = presupuestoRestante - Datos1.getSueldoMin(index);
		Set<String> nuevasCualidades = new HashSet<>(remaining);
		nuevasCualidades.removeAll(Datos1.getCualidades(index));
		
		Set<Integer> nuevosVetados = new HashSet<>(vetados);
		for (int j = index + 1 ; j< Datos1.getNumCandidatos(); j++) {
			if ( Datos1.getSonIncompatibles(index, j) ) {
				nuevosVetados.add(j);
			}
		}
		
		return List.of(CandidatosHyperVertex.of(index+1, nuevasCualidades, presupuestoTotal, nuevosVetados));
		}
		

	@Override
	public CandidatosHyperEdge edge(Integer a) {
		List<CandidatosHyperVertex> targets = this.neighbors(a);
		return CandidatosHyperEdge.of(this,targets,a);
	}	
	
	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

	//NUEVO PA HIPERGRAFOS ----	
	@Override
	public Boolean isBaseCase() { // es lo mismo que GOAL
		return this.index == Datos1.getNumCandidatos(); 
	}

	@Override
	public Double baseCaseWeight() { //en caso de que el caso base no cumpla el objetivo
		if (remaining.isEmpty()) {
			return 0.0;
		} else {
			return -100000.0;
		}
	}

	@Override
	public Boolean isValid() { //siempre true para nuestros ejercicios
		return true;
	}

	@Override
	public List<Integer> baseCaseSolution() {
		return new ArrayList<>();
	}

	@Override
	public List<Integer> solution(Integer a, List<List<Integer>> solutions) {
		// Mecanizado: La PD nos da la solución del subproblema (solutions.get(0)).
        // Nosotros le añadimos nuestra decisión (a) por delante.
        List<Integer> res = new ArrayList<>(solutions.get(0));
        res.add(0, a);
        return res;
	}
	
	public static Integer valor(List<Integer> solution) {
		Integer val_total = 0;
		for(int i=0; i<solution.size(); i++) {
			val_total += Datos1.getValoracion(i) * solution.get(i); //si es 0 no se sumara su valoracion
			//si es 1 si se sumara su valoracion y tendremos la valoracion total
		}
		return val_total;
	}

	

}
