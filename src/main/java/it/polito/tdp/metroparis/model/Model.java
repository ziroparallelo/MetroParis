package it.polito.tdp.metroparis.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata,DefaultEdge> grafo ;
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedGraph<Fermata,DefaultEdge>(DefaultEdge.class) ;
		
		MetroDAO dao = new MetroDAO() ;
		
		List<Fermata> fermate = dao.getAllFermate() ;
		
		Graphs.addAllVertices(this.grafo, fermate) ;
		
		// METODO 1: itero su ogni coppia di vertici
//		for(Fermata partenza: fermate) {
//			for(Fermata arrivo: fermate) {
//				if(dao.isFermateConnesse(partenza, arrivo)) {
//					this.grafo.addEdge(partenza, arrivo) ;
//				}
//			}
//		}
		
		// METODO 2: dato ciascun vertice, trova i vertici ad esso adiacenti
		// Variante 2a: il DAO restituisce un elenco di ID numerici
		
		// Nota: posso iterare su 'fermate' oppure su 'this.grafo.vertexSet()'
		for(Fermata partenza: fermate) {
			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza) ;
			for(Integer id: idConnesse) {
//				Fermata arrivo = (fermata che possiede questo "id") ;
				Fermata arrivo = null ;
				for(Fermata f: fermate) {
					if(f.getIdFermata()==id) {
						arrivo = f ;
						break ;
					}
				}
				this.grafo.addEdge(partenza, arrivo) ;
			}
		}
		
		// METODO 2: dato ciascun vertice, trova i vertici ad esso adiacenti
		// Variante 2b: il DAO restituisce un elenco di oggetti Fermata
		
		// METODO 2: dato ciascun vertice, trova i vertici ad esso adiacenti
		// Variante 2c: il DAO restituisce un elenco di ID numerici, che converto in oggetti
		// tramite una Map<Integer,Fermata> - "Identity Map"
		
		// METODO 3: faccio una sola query che mi restituisca le coppie
		// di fermate da collegare 
		// (variante preferita: 3c: usare Identity Map)
		
		
		System.out.println(this.grafo) ;
		System.out.println("Vertici = " + this.grafo.vertexSet().size());
		System.out.println("Archi   = " + this.grafo.edgeSet().size());

	}

}
