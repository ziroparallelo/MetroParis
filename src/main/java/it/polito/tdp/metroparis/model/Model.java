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
	}
	
	//Il prima possibile devo costruirmi l'Identity MAP
	
	public List<Fermata> getFermate(){

		if(this.fermate == null || this.fermate.size()==0) {
		this.fermate = dao.getAllFermate();
		}
		this.fermateIdMap = new HashMap<Integer, Fermata>();
		
		for(Fermata f:this.fermate) {
			this.fermateIdMap.put(f.getIdFermata(), f);
		}
		
		return fermate;
	}
	
	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo){
		creaGrafo();
		Map<Fermata, Fermata> alberoInverso = visitaGrafo(partenza);
		
		Fermata corrente = arrivo;
		List<Fermata> percorso = new ArrayList<Fermata>();
		while(corrente != null) {

			//Questo medoto add con int aggiunge gli elementi e poi li schifta
			//l'ultimo avrà posizione zero
			percorso.add(0, corrente);
			corrente = alberoInverso.get(corrente);
		}
		return percorso;
	}
	
	//Devo avere un metodo per la creazione del grafo
	
	public void creaGrafo() {
		
		//Grafo riparte con i dati puliti se l'utente da ripartire l'interfaccia con nuovi dati
		//per questo lo inizializzo qui
		
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		
		
		Graphs.addAllVertices(this.grafo, getFermate());
		
		List<CoppiaId> fermateDaCollegare = dao.getIdAllFermateConnesse();
		for(CoppiaId coppia: fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()),
					fermateIdMap.get(coppia.getIdArrivo()));
		}
		
		
		
//		System.out.println(this.grafo);
//		System.out.println("Vertici = "+this.grafo.vertexSet().size()); 
//		System.out.println("Archi = "+this.grafo.edgeSet().size()); 
		
//		visitaGrafo(fermate.get(0));		
	}
	
	//Mi creo l'albero inverso
	
	public Map<Fermata, Fermata> visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita = 
				new BreadthFirstIterator<Fermata, DefaultEdge> (this.grafo, partenza);
		
		Map<Fermata, Fermata> alberoInverso = new HashMap<Fermata, Fermata>();
		
		//Aggiungo la radice (che non ha il predecessore
		alberoInverso.put(partenza, null);
		
		//Ci sto passando la mappa dove voglio che passi le informazioni che voglio
		visita.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, this.grafo));
		while(visita.hasNext()){
			Fermata f = visita.next();
//			System.out.println(f);
		}
		
		List<Fermata> percorso = new ArrayList<>();
		
		//Ho costruito una lista al contrario che contiene il percorso.
		
		return alberoInverso;
//		fermata = arrivo; //Dall'albero inverso inizio dall'arrivo e arrivo alla partenza (prima di partenza c'è source null)
//		while(fermata!== null) {
//			fermata = alberoInverso.get(fermata);
//			percorso.add(fermata);
//		}
	}

}
