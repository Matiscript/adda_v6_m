package ejercicio1;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

public record CandidatosHyperEdge(CandidatosHyperVertex source, List<CandidatosHyperVertex> targets, Integer action, Double weight) 
	implements SimpleHyperEdge<CandidatosHyperVertex,CandidatosHyperEdge, Integer>	{
	
	public static CandidatosHyperEdge of(CandidatosHyperVertex candidatosHyperVertex, 
			List<CandidatosHyperVertex> targets, Integer action) {
		Double w = (double) Datos1.getValoracion(candidatosHyperVertex.index())*action;
		return new CandidatosHyperEdge(candidatosHyperVertex, targets, action, w);
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		// targetsWeight.get(0) -> Es lo que ganaremos en el futuro (subproblemas)
	    // this.weight -> Es lo que ganamos AHORA MISMO (calculado arriba en el of)
	    return targetsWeight.get(0) + this.weight;
	}
}

