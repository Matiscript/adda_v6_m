package ejercicio1.manual;

import ejercicio1.*;

public class TestPDManual {

	public static void main(String[] args) {
		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {
			Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			System.out.println("Solucion obtenida: " + CandidatosPD.search());
		}
	}
	
}
