package ejercicio1.manual;

import java.util.ArrayList;
import java.util.List;

import ejercicio1.*;

public class CandidatosState {
    
    private CandidatosVertex vertice;
    private Double valorAcumulado; // Usamos Double porque las valoraciones son decimales
    private List<Integer> acciones;
    private List<CandidatosVertex> vertices;

    // Constructor privado, tal como indica la diapositiva
    private CandidatosState(CandidatosVertex vertice, Double valorAcumulado, 
                            List<Integer> acciones, List<CandidatosVertex> vertices) {
        super();
        this.vertice = vertice;
        this.valorAcumulado = valorAcumulado;
        this.acciones = acciones;
        this.vertices = vertices;
    }

    // Método factoría para inicializar el estado en el vértice 0
    public static CandidatosState of(CandidatosVertex vertex) {
        List<CandidatosVertex> vt = new ArrayList<>();
        vt.add(vertex);
        return new CandidatosState(vertex, 0.0, new ArrayList<>(), vt);
    }
    
    public Boolean esSolucion() {
		return vertice.remaining().isEmpty();
	}
    public Solucion1 getSolucion() {
		return Solucion1.create(acciones);
	}
    public boolean esTerminal() {
		return vertice.index() == Datos1.getNumCandidatos();
    }
    
    public List<Integer> alternativas() {
		return vertice.actions();
	}
 // 5. El Adivino (Cota = lo que llevo + lo que gano con este paso + lo que ganaré en el futuro)
    public Double cota(Integer a) {
		Double weight = a > 0 ? Datos1.getValoracion(vertice.index()) : 0.;
		CandidatosVertex vecino = vertice.neighbor(a);
		Double estimacionHeuristica = CandidatosHeuristic.heuristic(vecino,v->v.index() == Datos1.getNumCandidatos(), null);
		return valorAcumulado + weight + estimacionHeuristica ;
	}

    // --- LA MAGIA DEL BACKTRACKING MANUAL ---

    // Avanzar (forward): Tomamos una decisión (1 o 0) y bajamos un nivel en el árbol
    public void forward(Integer a) {
        this.acciones.add(a);
        
        // Pedimos al vértice actual que nos genere el siguiente según la acción elegida
        CandidatosVertex vcn = this.vertice.neighbor(a);
        this.vertices.add(vcn);
        
        // Si a=1 (contratamos), sumamos su valoración. Si a=0, sumamos 0.
        this.valorAcumulado = this.valorAcumulado + a * Datos1.getValoracion(this.vertice.index());
        
        // Actualizamos el vértice en el que estamos parados
        this.vertice = vcn;
    }

    // Retroceder (back): Deshacemos la última decisión para subir un nivel y probar otra rama
    public void back(Integer a) {
        // Borramos la última acción y el último vértice visitado
        this.acciones.remove(this.acciones.size() - 1);
        this.vertices.remove(this.vertices.size() - 1);
        
        // Nuestro vértice actual vuelve a ser el anterior
        this.vertice = this.vertices.get(this.vertices.size() - 1);
        
        // Restamos el valor que habíamos sumado (si a=1 restamos la valoración, si a=0 restamos 0)
        this.valorAcumulado = this.valorAcumulado - a * Datos1.getValoracion(this.vertice.index());
    }

    // Getters
    public CandidatosVertex vertice() { return vertice; }
    public Double valorAcumulado() { return valorAcumulado; }
    public List<Integer> acciones() { return acciones; }
    public List<CandidatosVertex> vertices() { return vertices; }
}