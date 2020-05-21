package it.polito.tdp.bar.model;

public class Model {
	
	
		Simulator simulatore=new Simulator();
		simulatore.run();
		
		Integer totClienti=simulatore.getNumero_totale_clienti();
		Integer soddisfatti=simulatore.getNumero_clienti_soddisfatti();
		Integer insoddisfatti=simulatore.getNumero_clienti_insoddisfatti();
		
		//System.out.format("Clienti totali: %d, clienti soddisfatti: %d, clienti insoddisfatti: %d",	totClienti,soddisfatti,insoddisfatti);
}
