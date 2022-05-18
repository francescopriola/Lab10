package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public Map<Integer, River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		Map<Integer, River> rivers = new HashMap<Integer, River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r = new River(res.getInt("id"), res.getString("name"));
				rivers.put(r.getId(), r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getFlowsByRiver(Map<Integer, River> idMap, int riverId){
		String sqlString = "SELECT * "
				+ "FROM flow "
				+ "WHERE river = ? ";
		
		List<Flow> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sqlString);
			st.setInt(1, riverId);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Flow f = new Flow(rs.getDate("day").toLocalDate(), rs.getDouble("flow"), idMap.get(rs.getInt("river")));
				result.add(f);
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	} 
}
