package tests.ejercicio2;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import ejercicio2.*;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestsAStar {

	public static void main(String[] args) throws IOException {
		// Set up
				
				Datos2.iniDatos("datos_entrada/ejercicio2/DatosEntrada1.txt");

				ContenedoresVertex vInicial = ContenedoresVertex.initial();

				// Grafo

				System.out.println("#### Algoritmo A* ####");

				// Algoritmo A*
				EGraph<ContenedoresVertex, ContenedoresEdge> graph =
							EGraph.virtual(vInicial)
							.pathType(PathType.Sum)
							.type(Type.Max)
							.edgeWeight(x -> x.weight())
							.heuristic(ContenedoresHeuristic::heuristic)
							.build();
							
				AStar<ContenedoresVertex, ContenedoresEdge,?> aStar = AStar.ofGreedy(graph);
					
				GraphPath<ContenedoresVertex, ContenedoresEdge> gp = aStar.search().get();
					
				List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
							.collect(Collectors.toList()); // getEdgeList();
			
				Solucion2 s_as = Solucion2.of(gp);

				System.out.println(s_as);
				System.out.println(gp_as);

				GraphColors.toDot(aStar.outGraph(), "ficheros_generados/ejercicio2/CandidatosAStarGraph1.gv", 
							v -> v.toGraph(),
							e -> e.action().toString(), 
							v -> GraphColors.colorIf(Color.red,v.goal()),
							e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
			}
			

		}
