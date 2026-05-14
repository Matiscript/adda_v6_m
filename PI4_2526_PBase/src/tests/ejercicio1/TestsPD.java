package tests.ejercicio1;



import java.util.List;
import java.util.Map;

import ejercicio1.*;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestsPD {

	public static void main(String[] args) {
		Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada3.txt");

		CandidatosHyperVertex vInicial = CandidatosHyperVertex.initial();
		
		
		System.out.println("\n\n#### Algoritmo PD ####");

		System.out.println(vInicial);				
		SimpleVirtualHyperGraph<CandidatosHyperVertex,CandidatosHyperEdge,Integer> graph3 = 
				SimpleVirtualHyperGraph.simpleVirtualHyperGraph(vInicial);
		
		PD<CandidatosHyperVertex, CandidatosHyperEdge, Integer, List<Integer>> a = 
				PD.dynamicProgrammingSearch(graph3,PDType.Max);
		
//		a.withGraph = true;
		a.search();
		
		Map<CandidatosHyperVertex, Sp<Integer, CandidatosHyperEdge>> s = a.getSolutionsTree();
		
		if (s.get(vInicial) == null) {
			System.out.println("No hay solución");
		} else {			
			GraphTree<CandidatosHyperVertex,CandidatosHyperEdge,Integer,List<Integer>> tree = 
					GraphTree.graphTree(vInicial,s);

			System.out.println(tree.solution());

			System.out.println(CandidatosHyperVertex.valor(tree.solution()));
		}

		
	}



}
