package ejercicio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import us.lsi.graphs.virtual.VirtualVertex;


public record CandidatosVertex(Integer index, Set<String> remaining, 
		Double presupuestoRestante,
		List<Integer> elegidos) 
	implements VirtualVertex<CandidatosVertex, CandidatosEdge, Integer>{
	
	//este tenemos que hacerlo, la mayoria de funciones se pueden generar a partir de este constructor
		public static CandidatosVertex initial() {
			return of(0,  //index empieza en 0
					Datos1.getCualidades(), //las cualidades son un set todas las que faltan
					(double)Datos1.getPresupuestoMax(), //el presupuesto restante inicial es el maximo
					new ArrayList<>() //inicialmente no hay ninguno elegido
					);
		}
		
	private static CandidatosVertex of(int i, Set<String> cualidades, double presupuestoMax, List<Integer> elegidos) {
		return new CandidatosVertex(i,cualidades,presupuestoMax,elegidos); 
	}
	
	public Boolean goal() {
		// Solo comprobamos si hemos llegado al final de la lista de candidatos
		return this.index == Datos1.getNumCandidatos();
	}
	public Boolean goalHasSolution() {
	    // Comprobamos si la mochila de cualidades que nos faltan está vacía
	    return this.remaining.isEmpty();
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
		// 3. La Puerta 1 (Sí contratar): Hay que comprobar si puede pasar
		Double sueldoCandidato = Datos1.getSueldoMin(index);
		boolean quedaDinero = sueldoCandidato <= presupuestoRestante;
		boolean noHayPeleas = Boolean.TRUE;
		for (Integer j: elegidos) {
			if (Datos1.getSonIncompatibles(index, j)) {
				noHayPeleas = Boolean.FALSE;
				break;
			}
		}
		if (quedaDinero && noHayPeleas) {
			caminosAbiertos.add(1);
		}
		// 4. La Puerta 0 (No contratar): Siempre se puede abrir. La metemos en la lista.
		caminosAbiertos.add(0);
				
		return caminosAbiertos;
		}

	@Override
	public CandidatosVertex neighbor(Integer a) {
		if (a == 0) {
			return CandidatosVertex.of(index+1, remaining, presupuestoRestante, elegidos);
		}
		Double presupuestoTotal = presupuestoRestante - Datos1.getSueldoMin(index);
		
		Set<String> nuevasCualidades = new HashSet<>(remaining);
		nuevasCualidades.removeAll(Datos1.getCualidades(index));
		List<Integer> nuevosElegidos = new ArrayList<>(elegidos);
		nuevosElegidos.add(index);
		return CandidatosVertex.of(index+1, nuevasCualidades, presupuestoTotal, nuevosElegidos);
		}
		

	@Override
	public CandidatosEdge edge(Integer a) {
		return CandidatosEdge.of(this, this.neighbor(a), a);
	}
	
	@Override
	public Integer greedyAction() {
		if(actions().contains(1)) {
		List<String> cualidadesCandidato = Datos1.getCualidades(index);
		for (String cualidad: cualidadesCandidato) {
			if (remaining.contains(cualidad)) {
				return 1;
				}
		}
		}
		return 0;
	}
	
	
	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

	

}
