package tests.ejercicio1;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import ejercicio1.CandidatosEdge;
import ejercicio1.CandidatosHeuristic;
import ejercicio1.CandidatosVertex;
import ejercicio1.Datos1;
import ejercicio1.Solucion1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {

		Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada1.txt");

		CandidatosVertex vInicial = CandidatosVertex.initial();
		
		Predicate<CandidatosVertex> es_terminal = v -> v.index() == Datos1.getNumCandidatos() && v.remaining().isEmpty();
		
		//(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
		EGraph<CandidatosVertex, CandidatosEdge> graph = 
			    EGraph.virtual(vInicial)
			    .pathType(PathType.Sum)
		        .type(Type.Max)
		        .edgeWeight(e -> e.weight())
		        // AQUÍ ESTÁ: Le pasamos 'es_terminal' directamente a tu heurística
		        .heuristic((v1, predicadoVacio, v2) -> CandidatosHeuristic.heuristic(v1, es_terminal, v2))
		        .build();
         
		GreedyOnGraph<CandidatosVertex, CandidatosEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<CandidatosVertex, CandidatosEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;
	

		BT<CandidatosVertex,CandidatosEdge,Solucion1>alg_bt = BT.of(graph);
			//	BT.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<CandidatosVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<CandidatosEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/ejercicio1/Candidatos1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		if(res!=null)
			System.out.println("Solucion BT: " + Solucion1.create(res) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
		
	}	

}
