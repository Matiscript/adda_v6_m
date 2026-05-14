package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CandidatosEdge(CandidatosVertex source, CandidatosVertex target, Integer action, Double weight) 
	implements SimpleEdgeAction<CandidatosVertex, Integer>	{
	
	public static CandidatosEdge of(CandidatosVertex c1, CandidatosVertex c2, Integer action) {
		Double w = (double) Datos1.getValoracion(c1.index())*action;
		return new CandidatosEdge(c1, c2, action, w);
	}
}

