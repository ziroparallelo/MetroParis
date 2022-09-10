package it.polito.tdp.metroparis.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class RegistraAlberoDiVisita implements TraversalListener<Fermata, DefaultEdge> {
	
	private Map<Fermata, Fermata> alberoInverso;
	private Graph<Fermata, DefaultEdge> grafo;
	
	
	
	public RegistraAlberoDiVisita(Map<Fermata, Fermata> alberoInverso, Graph<Fermata, DefaultEdge> grafo) {
		this.alberoInverso = alberoInverso;
		this.grafo = grafo;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
//		System.out.println(e.getEdge());
		
		//Questo arco non contiene un metodo per trovare il source
		//Mi serve anche l'oggetto grafo
		Fermata source = this.grafo.getEdgeSource(e.getEdge());
		Fermata target = this.grafo.getEdgeTarget(e.getEdge());
		
//		System.out.println(source +"; "+target);
		
		//Se il source o il target esistono già nella mappa, non li devo aggiungere
		//Almeno uno dei due deve per forza già esistere
		
		//Se il source c'era già allora ho scoperto il target
		
		//Può sempre succedere che a non esistere sia il source?
		if(!alberoInverso.containsKey(target)) {
			alberoInverso.put(target, source);
//			System.out.println(target +" si raggiunge da "+source);
			}
		else if(!alberoInverso.containsKey(source)) {
			alberoInverso.put(source, target);
//			System.out.println(source +" si raggiunge da "+target);
		}
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

}
