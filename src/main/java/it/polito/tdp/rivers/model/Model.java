package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map<Integer, River> idMap;
	private Simulator sim;
	private double c_med;
	
	public Model() {
		dao = new RiversDAO();
		idMap = new HashMap<>(dao.getAllRivers());
		sim = new Simulator();
	}
	
	public Map<Integer, River> getAllRivers(){
		return dao.getAllRivers();
	}
	
	public List<Flow> getFlowsByRiver(int riverId){
		return dao.getFlowsByRiver(idMap, riverId);
	}
	
	public double simula(int riverId) {
		sim.init(idMap.get(riverId));
		sim.run();
		
		c_med = sim.getStatistiche().getC_tot() / (sim.getFlussi().size()+1);
		return c_med;
		
	}

}
