package it.polito.tdp.rivers.model;

public class Statistiche {

	private int nGiorni;
	private double c_tot;
	
	public Statistiche() {
		super();
		this.nGiorni = 0;
		this.c_tot = 0;
	}

	public int getnGiorni() {
		return nGiorni;
	}

	public double getC_tot() {
		return c_tot;
	}
	
	public void incrementaCTot(double c) {
		this.c_tot += c;
	}
	
	public void incrementaNGiorni() {
		this.nGiorni++;
	}
	
	
}
