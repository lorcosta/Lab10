package it.polito.tdp.bar.model;

public class Model {
	private Simulator simulatore=new Simulator();
	
	public void simula() {
		
		simulatore.run();
	}
	
	public Integer getTotClienti() {
		Integer totClienti=simulatore.getNumero_totale_clienti();
		return totClienti;

	}
	public Integer getSoddisfatti() {
		Integer soddisfatti=simulatore.getNumero_clienti_soddisfatti();
		return soddisfatti;
	}
	public Integer getInsoddisfatti() {
		Integer insoddisfatti=simulatore.getNumero_clienti_insoddisfatti();
		return insoddisfatti;
	}
}
