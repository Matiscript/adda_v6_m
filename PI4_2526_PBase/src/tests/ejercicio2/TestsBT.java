package tests.ejercicio2;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import ejercicio2.*;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestsBT {

	public static void main(String[] args) {

		Datos2.iniDatos("datos_entrada/ejercicio2/DatosEntrada1.txt");

		ContenedoresVertex vInicial = ContenedoresVertex.initial();
		
		EGraph<ContenedoresVertex, ContenedoresEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial)
				.pathType(PathType.Sum)
				.type(Type.Max)
				.edgeWeight(e->e.weight())
				.heuristic(ContenedoresHeuristic::heuristic)
				.build();

		GreedyOnGraph<ContenedoresVertex, ContenedoresEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<ContenedoresVertex, ContenedoresEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		path = null;
		
		BT<ContenedoresVertex,ContenedoresEdge,Solucion2>alg_bt = path==null? BT.of(graph):
			BT.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<ContenedoresVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<ContenedoresEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/ejercicio2/ContenedoresBTGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		if(res!=null)
			System.out.println("Solucion BT: " + Solucion2.of(res) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
		
	}	

}
