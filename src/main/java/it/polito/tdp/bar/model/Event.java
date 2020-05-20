package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Event implements Comparable<Event>{
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI,PARTENZA_GRUPPO_CLIENTI
	}
	
	private LocalTime time;
	private EventType type;
	private Integer numPersone;
	private Duration durata;
	private float tolleranza;
	
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalTime time, EventType type, Integer numPersone, Duration durata, float tolleranza) {
		super();
		this.time = time;
		this.type = type;
		this.numPersone=numPersone;
		this.durata=durata;
		this.tolleranza=tolleranza;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}
	public Integer getNumPersone() {
		return numPersone;
	}
	public void setNumPersone(Integer numPersone) {
		this.numPersone = numPersone;
	}
	public Duration getDurata() {
		return durata;
	}
	public void setDurata(Duration durata) {
		this.durata = durata;
	}
	public float getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}
}
