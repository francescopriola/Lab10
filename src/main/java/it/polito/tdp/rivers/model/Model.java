package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map<Integer, River> idMap;
	
	public Model() {
		dao = new RiversDAO();
		idMap = new HashMap<>(dao.getAllRivers());
	}
	
	public Map<Integer, River> getAllRivers(){
		return dao.getAllRivers();
	}
	
	public List<Flow> getFlowsByRiver(int riverId){
		return dao.getFlowsByRiver(idMap, riverId);
	}

}
