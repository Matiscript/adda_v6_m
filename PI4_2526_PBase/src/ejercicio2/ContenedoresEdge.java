package ejercicio2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ContenedoresEdge(ContenedoresVertex source, ContenedoresVertex target, Integer action, Double weight) 
implements SimpleEdgeAction<ContenedoresVertex,Integer> {

	public static ContenedoresEdge of(ContenedoresVertex v1, ContenedoresVertex v2, Integer a, Double w) {	
		return new ContenedoresEdge(v1, v2, a, w);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}