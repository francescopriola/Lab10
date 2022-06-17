package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
//	Modello
	private List<Flow> flussi;
	private RiversDAO dao = new RiversDAO();
	private Map<Integer, River> rivers = dao.getAllRivers();
	
//	Parametri della simulazione
	private double f_med;
	private double fattore_scala; //k
	private double capienzaTot = fattore_scala * f_med * (30*86400); //Q
	private double capacita = capienzaTot/2; //C
	private double f_out_min = 0.8 * f_med;

//	Coda degli eventi
	private PriorityQueue<Event> queue;
	
//	Output
	private Statistiche statistiche;
	
	private void creaEventi(double fMed, double k, List<Flow> flussi) {
		this.fattore_scala = k;
		this.f_med = fMed;
		this.flussi = flussi;
		for(Flow f : flussi) {
			double f_out = this.f_out_min;
			double x = Math.random() * 100 +1;
			
			if(x <= 5) {
				f_out = this.f_out_min*10;
			}
			this.queue.add(new Event(EventType.FLUSSO_INGRESSO, f.getDay(), f.getFlow(), f_out));
		}
		
	}
	
	public void init(River river) {
		this.queue = new PriorityQueue<Event>();
		this.statistiche = new Statistiche();
		
		flussi = dao.getFlowsByRiver(rivers, river.getId());
		creaEventi(f_med, fattore_scala, flussi);
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e= queue.poll();
			processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
		case FLUSSO_INGRESSO:
			double c = capacita+ e.getF_in();
			if(c > capienzaTot)
				this.queue.add(new Event(EventType.TRACIMAZIONE, e.getData(), e.getF_in(), e.getF_out()));
			
			if(e.getF_in() > e.getF_out()) 
				capacita += (e.getF_in() - e.getF_out());
			
			if(e.getF_out() > e.getF_in())
				this.queue.add(new Event(EventType.FLUSSO_USCITA, e.getData(), e.getF_in(), e.getF_out()));
			statistiche.incrementaCTot(capacita);
			break;
		case FLUSSO_USCITA:
			double cc = capacita - (e.getF_out() - e.getF_in());
			if(capacita - cc< f_out_min) {
				statistiche.incrementaNGiorni();
				if(capacita - cc < 0)
					capacita = 0;
			}
			statistiche.incrementaCTot(capacita);
			break;
		case TRACIMAZIONE:
			capacita = capienzaTot;
			break;
		}
		
	}

	public List<Flow> getFlussi() {
		return flussi;
	}

	public void setFlussi(List<Flow> flussi) {
		this.flussi = flussi;
	}

	public Statistiche getStatistiche() {
		return statistiche;
	}

	public void setStatistiche(Statistiche statistiche) {
		this.statistiche = statistiche;
	}
	
	

}
