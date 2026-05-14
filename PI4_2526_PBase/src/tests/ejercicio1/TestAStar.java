package tests.ejercicio1;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import ejercicio1.CandidatosEdge;
import ejercicio1.CandidatosHeuristic;
import ejercicio1.CandidatosVertex;
import ejercicio1.Datos1;
import ejercicio1.Solucion1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "DatosEntrada1.txt";
		Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada1.txt");
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		CandidatosVertex start = CandidatosVertex.initial();
		
		// --- TRAMPAS PARA CAZAR AL CULPABLE ---
		System.out.println("Sospechoso 1 (Meta): ¿El inicio se cree que es la meta? -> " + start.goal());
		System.out.println("Sospechoso 2 (Adivino): ¿La heurística devuelve 0? -> " + CandidatosHeuristic.heuristic(start, null, null));
		// -------------------------------------

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<CandidatosVertex, CandidatosEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(x -> x.weight())
					.heuristic(CandidatosHeuristic::heuristic)
					.build();
					
		AStar<CandidatosVertex, CandidatosEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<CandidatosVertex, CandidatosEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
	
		Solucion1 s_as = Solucion1.create(gp);

		System.out.println(s_as);
		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/ejercicio1/CandidatosAStarGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red,v.goal()),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
