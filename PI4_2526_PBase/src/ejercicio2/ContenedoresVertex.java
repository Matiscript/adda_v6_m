package ejercicio2;

import java.util.Comparator;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record ContenedoresVertex(Integer index, List<Integer> cargas) 
implements VirtualVertex<ContenedoresVertex, ContenedoresEdge, Integer> {
	

	//este tenemos que hacerlo, la mayoria de funciones se pueden generar a partir de este constructor
	public static ContenedoresVertex initial() {
		List<Integer> cargasInit = List2.empty();
		for (int j=0; j<Datos2.getNumContenedores(); j++) {
			cargasInit.add(Datos2.getTamContenedor(j));
		}
		return ContenedoresVertex.of(0, cargasInit);
	}

	public static ContenedoresVertex of(Integer i, List<Integer> cargas) {
		return new ContenedoresVertex(i, cargas);
	}
	
	public List<Integer> remaining(){
		return List.copyOf(this.cargas());
	}
	
	public Boolean goal() {
		return this.index() == Datos2.getNumElementos();
	}
	
	public Boolean goalHasSolution() {
		return true; //porque no importa si los llenamos todos o no
		/* Si se quisiera llenar TODOS los contenedores
		 * return this.remaining().stream().allMatch(e -> e.equals(0));
		 */
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		//en que contenedores nos podemos meter ?
		if(index < Datos2.getNumElementos()) {
			for(int j=0; j<Datos2.getNumContenedores();j++) {
				if (Datos2.getPuedeUbicarse(index, j)) {
					if(remaining().get(j) >= Datos2.getTamElemento(index)) {
						alternativas.add(j);
					}
				}
			}
			alternativas.add(Datos2.getNumContenedores());
			}
		return alternativas;
	}

	@Override
	public ContenedoresVertex neighbor(Integer a) {
		if(a==Datos2.getNumContenedores()) {
			//no se ha llenado ninguno entonces simplemente avanzamos un elemento
			return of(index+1, cargas());
		}
		//tenemos que restarle la capacidad al contenedor
		
		return of(index+1, List2.set(cargas(), a, cargas.get(a)-Datos2.getTamElemento(index)));
	}


	
	@Override
	public Integer greedyAction() {
		List<Integer> opciones = actions();
		// Si la única opción que hay es la basura (tamaño 1 y es el ID de la basura), pues la devolvemos
		if (opciones.size() == 1) {
			return opciones.get(0);
		}
		Comparator<Integer> cmp = Comparator.comparing(j -> remaining().get(j) - Datos2.getTamElemento(index));

		// Cogemos las opciones, quitamos la papelera de la ecuación con un filter, y sacamos la mejor
		return opciones.stream()
					.filter(j -> j < Datos2.getNumContenedores())
					.min(cmp)
					.get();
	}
	
	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

	@Override
	public ContenedoresEdge edge(Integer a) {
		Double peso = 0.0;
		//solo ganamos puntos si metemos el elemento y el contenedor se llena (tamaño remaining == 0 )
		if (a< Datos2.getNumContenedores()) {
			Integer huecoRestante = cargas.get(a) - Datos2.getTamElemento(index);
			if (huecoRestante == 0) {
				peso = 1.0;
			}
		}
		return ContenedoresEdge.of(this,this.neighbor(a), a, peso);
	}
}

	


