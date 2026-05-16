package ejercicio2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import ejercicio2.Datos2.*;
import us.lsi.common.List2;
import us.lsi.common.Map2;


public class Solucion2 {
	
	public static Solucion2 of(GraphPath<ContenedoresVertex, ContenedoresEdge> gp) {
		List<Integer> ls = gp.getEdgeList().stream().map(e -> e.action()).toList();
		Solucion2 res = of(ls);
		return res;
	}
	
	
	public static Solucion2 of(List<Integer> acciones) { 
		return new Solucion2(acciones); 
		}

	private Map<Contenedor, List<Elemento>> distribucion;

	private Solucion2(List<Integer> ls) {  // Lista de acciones/alternativas
		distribucion = Map2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)<Datos2.getNumContenedores()) {
				Contenedor cont = Datos2.getContenedor(ls.get(i));
				distribucion.computeIfAbsent(cont, k->List2.empty()).add(Datos2.getElemento(i));
			}
		}
	}
	
	
	@Override
	public String toString() {
		for(int j=0; j<Datos2.getNumContenedores(); j++) {
			Contenedor c = Datos2.getContenedor(j);
			if(distribucion.containsKey(c)) {
				int sum = distribucion.get(c).stream().mapToInt(e->e.tam()).sum();
				if(sum<c.capacidad())
					distribucion.remove(c);
			}
		}
		
		return distribucion.entrySet().stream().map(e->e.getKey().nombre()+": "
		+e.getValue().stream().map(d->d.nombre()).collect(Collectors.joining(",")))
		.collect(Collectors.joining("\n", "Reparto obtenido:\n", "\n"));
	}


	
	
}