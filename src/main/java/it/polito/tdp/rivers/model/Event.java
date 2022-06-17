package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		FLUSSO_INGRESSO,
		FLUSSO_USCITA,
		TRACIMAZIONE
	}
	
	private EventType type;
	private LocalDate data;
//	private Flow flusso;
	private double f_in;
	private double f_out;
	

	public Event(EventType type, LocalDate data, double f_in, double f_out) {
		super();
		this.type = type;
		this.data = data;
		this.f_in = f_in;
		this.f_out = f_out;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getF_in() {
		return f_in;
	}

	public void setF_in(double f_in) {
		this.f_in = f_in;
	}

	public double getF_out() {
		return f_out;
	}

	public void setF_out(double f_out) {
		this.f_out = f_out;
	}
	
	@Override
	public int compareTo(Event o) {
		return this.data.compareTo(o.getData());
	}
	
	
	
	
	

}
