package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {

	private List<Fermata> fermate;
		
	private Graph<Fermata, DefaultEdge> grafo;
	private MetroDAO dao;
	private Map<Integer, Fermata> fermateIdMap;
	
	public Model() {
		this.dao = new MetroDAO();
		this.fermate = new ArrayList<Fermata>();
		Map<Integer, Fermata> fermateIdMap = new HashMap<Integer, Fermata>();
	}
	public List<Fermata> getFermate(){

		if(this.fermate == null || this.fermate.size()==0) {
		this.fermate = dao.getAllFermate();
		}
		
		for(Fermata f:this.fermate) {
			this.fermateIdMap.put(f.getIdFermata(), f);
		}
		
		return fermate;
	}
	
//	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo){
//		creaGrafo();
//	}
	public void creaGrafo() {
		
		//Grafo riparte con i dati puliti se l'utente da ripartire l'interfaccia con nuovi dati
		//per questo lo inizializzo qui
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		
		
		Graphs.addAllVertices(this.grafo, getFermate());
		
		//Tra due fermate c'è un arco se esiste almeno una linea che li collega
		//Non posso fare tutti questi accessi perché richiederebbe troppo tempo
		
		//METODO 1: SOLUZIONE SEMPLICE, ITERO SU OGNI COPPIA DI VERTICE
		
//		FACCIO 619*619 QUERY
		
		
//		for(Fermata partenza: fermate) 
//			for(Fermata arrivo: fermate) 
//				if(dao.isFermateConnesse(partenza, arrivo))
//					this.grafo.addEdge(partenza, arrivo);
		
//		Posso concentrarmi su un vertice alla volta chidendo quali sono tutti gli
//		archi uscenti da tale fermata
		
		//METODO 2A: Dato ciascun vertice, trova i vertici adiacenti
		//Il dao resituisce la chiave e devo ripescare io l'oggetto
		
//		Faccio solo 619 QUERY
		
		
		
//		for(Fermata partenza: fermate) {
//			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
//			for(Integer id: idConnesse) {
				
				//OGGETTO FERMATA FAKE
				
				//Mi basterebbe l'oggetto solo con l'id e gli altri attributi null
				//Poiché potrei sfruttare l'hashcode e l'equals dell'oggetto che
				//è sufficiente per agganciare l'arco poiché l'oggetto Fermata
				//è già presente nel mio grafo
				
				//POSSO ITERARE SU FERMATE PERCHE' LO CHIAMO PRIMA QUESTA STRUTTURA
				//OPPURE SU this.grafo.vertexSet()
//				Fermata arrivo = null;
//				for(Fermata f: fermate) {
//					if(f.getIdFermata()==id)
//					{
//						arrivo = f;
//						break;
//					}
//				}
				//Lo cerco nella struttura dati che già ho tra le fermate
//				this.grafo.addEdge(partenza, arrivo);
//			}
//		}
		
		//METODO 2B: Dato ciascun vertice, trova i vertici adiacenti
		//Il dao resituisce l'oggetto stesso FERMATA
		
//		for(Fermata partenza: fermate) {
//			List<Fermata> arrivi = dao.getFermataConnesse(partenza);
//			for(Fermata arrivo: arrivi)
//				this.grafo.addEdge(partenza, arrivo);
			
			//sto passando al grafo degli oggetti uguali a quelli che contiene
			//l'oggetto arrivo il grafo lo vede come un oggetto esterno ma lo
			//riconosce come un oggetto uguale ad uno che possiede
//		}
				
		//METODO 2C: Dato ciascun vertice, trova i vertici adiacenti
		//Il dao resituisce la chiave ed uso una mappa conoscendo la chiave

		//Mi creo una mappa all'interno del metodo
		
//		-> Identity Map
		
//		for(Fermata partenza: fermate) {
//			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
//			for(int id: idConnesse) {
//				Fermata arrivo = fermateIdMap.get(id);
//				this.grafo.addEdge(partenza, arrivo);
//			}
//		}
		
		
//		-> Fino a qui faccio N quesry
		
		//METODO 3: Faccio UNA SOLA QUERY che mi restituisce le coppie
		//di fermate da collegare
//		-> Identity Map
		
		//Devo crearmi una classe apposta (delego quasi tutto il lavoro al DB)
		//poiché con la Mappa la conversione da Id a mappa è veloce
		
		List<CoppiaId> fermateDaCollegare = dao.getIdAllFermateConnesse();
		for(CoppiaId coppia: fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()),
					fermateIdMap.get(coppia.getIdArrivo()));
		}
		
		
		
		System.out.println(this.grafo);
		System.out.println("Vertici = "+this.grafo.vertexSet().size()); 
		System.out.println("Archi = "+this.grafo.edgeSet().size()); 
		
		visitaGrafo(fermate.get(0));		
	}
	
	public void visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita = 
//				new BreadthFirstIterator<Fermata, DefaultEdge> (this.grafo, partenza);
		new DepthFirstIterator<Fermata, DefaultEdge> (this.grafo, partenza);
		while(visita.hasNext()){
			Fermata f = visita.next();
			System.out.println(f);
		}
	}

}
