package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	//coda degli eventi
	private PriorityQueue<Event> queue= new PriorityQueue<>();
	//parametri di simulazione
	private Integer T10posti=2;
	private Integer T8posti=4;
	private Integer T6posti=4;
	private Integer T4posti=5;
	private final LocalTime ORA_APERTURA=LocalTime.of(6, 00);
	private final LocalTime ORA_CHIUSURA=LocalTime.of(22, 00);
	
	//modello del mondo
	//valori da calcolare
	private Integer numero_totale_clienti, numero_clienti_soddisfatti,numero_clienti_insoddisfatti;
	
	//simulazione vera e proria
	public void run() {
		//preparazione iniziale, variabili del mondo e coda degli eventi
		this.numero_clienti_insoddisfatti=this.numero_clienti_soddisfatti=this.numero_totale_clienti=0;
		this.queue.clear();
		//imposto l'arrivo del primo gruppo di clienti e poi ogni random aggiungo un arrivo di gruppo clienti
		LocalTime oraArrivoGruppo=this.ORA_APERTURA;
		int i=0;
		do {
			//riempio la coda degli eventi
			Integer numPersone=this.generaNumPersone();
			ThreadLocalRandom random= ThreadLocalRandom.current();
			Duration durata=Duration.of(random.nextInt(60,121), ChronoUnit.MINUTES);
			float tolleranza=(float)Math.random();
			Event e=new Event(oraArrivoGruppo,EventType.ARRIVO_GRUPPO_CLIENTI,numPersone,durata,tolleranza);
			this.queue.add(e);
			oraArrivoGruppo=oraArrivoGruppo.plus(arrivoGruppo());
			i++;
		}while(oraArrivoGruppo.isBefore(ORA_CHIUSURA));//while(i<2000);
		//eseguo ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			processEvent(e);
			
		}
	}
	/**
	 * Analizza l'evento della coda
	 * @param e
	 */
	private void processEvent(Event e) {
		switch(e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:
			//sono arrivati un gruppo di clienti
				//1.aggiorno il modello del mondo
				//controllo la dimensione del gruppo e i tavoli rimanenti
				Integer occupanti=e.getNumPersone();
				if(occupanti<=4 && this.T4posti>0 && ((float)occupanti/(float)this.T4posti)>=0.5) {
					this.T4posti--;
				}else if(occupanti<=6 && this.T6posti>0 && ((float)occupanti/(float)this.T6posti)>=0.5) {
					this.T6posti--;
				}else if(occupanti<=8 && this.T8posti>0 && ((float)occupanti/(float)this.T8posti)>=0.5) {
					this.T8posti--;
				}else if(occupanti<=10 && this.T10posti>0 && ((float)occupanti/(float)this.T10posti)>=0.5) {
					this.T10posti--;
				}else {
					//non ci sono tavoli perciò i clienti si devono sedere al bancone
					//a patto che la tolleranza sia maggiore del numero casuale
					//tiro un dado, se faccio più di te tu ti fermi al bancone
					double rand=Math.random();
					if(rand>e.getTolleranza()) {
						this.numero_totale_clienti+=e.getNumPersone();
						this.numero_clienti_soddisfatti+=e.getNumPersone();
						//this.numero_totale_clienti++;
						//this.numero_clienti_soddisfatti++;
					}else {
						this.numero_clienti_insoddisfatti+=e.getNumPersone();
						this.numero_totale_clienti+=e.getNumPersone();
						//this.numero_totale_clienti++;
						//this.numero_clienti_insoddisfatti++;
					}
				}
				//2.aggiorno i risultati
				this.numero_totale_clienti+=e.getNumPersone();
				this.numero_clienti_soddisfatti+=e.getNumPersone();
				//3.genero i nuovi eventi
				//devo generare un evento PARTENZA_GRUPPO_CLIENTI
				LocalTime partenza=e.getTime().plus(e.getDurata());
				Event nuovo=new Event(partenza,EventType.PARTENZA_GRUPPO_CLIENTI,e.getNumPersone(),e.getDurata(),e.getTolleranza());
				this.queue.add(nuovo);
				
			break;
		case PARTENZA_GRUPPO_CLIENTI:
			//un gruppo di clienti se ne va
			//1.aggiorno il modello del mondo
			occupanti=e.getNumPersone();
			if(occupanti<=4) {
				this.T4posti++;
			}else if(occupanti<=6 && this.T6posti>0 && ((float)occupanti/(float)this.T6posti)>=0.5) {
				this.T6posti++;
			}else if(occupanti<=8 && this.T8posti>0 && ((float)occupanti/(float)this.T8posti)>=0.5) {
				this.T8posti++;
			}else if(occupanti<=10 && this.T10posti>0 && ((float)occupanti/(float)this.T10posti)>=0.5) {
				this.T10posti++;
			}
			break;
		}
	}
	/**
	 * Genera un numero casuale tra 1 e 10 che corrisponde al numero di persone
	 * che desiderano sedersi al tavolo
	 * @return numPersone
	 */
	private Integer generaNumPersone() {
		double numPersone=Math.random();
		if(numPersone==0) {
			generaNumPersone();
		}else {
			numPersone=numPersone*10;
		}
		return (int)numPersone;
	}
	/**
	 * Genera un numero casuale compreso tra 1 e 10 che rappresenta il tempo che intercorre
	 * tra lo scorso arrivo di un gruppo e l'arrivo di un nuovo gruppo
	 */
	private Duration arrivoGruppo() {
		double num=Math.random();
		Duration nuovoArrivo;
		if(num<0.1) {
			nuovoArrivo=Duration.of(1, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.2) {
			nuovoArrivo=Duration.of(2, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.3) {
			nuovoArrivo=Duration.of(3, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.4) {
			nuovoArrivo=Duration.of(4, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.5) {
			nuovoArrivo=Duration.of(5, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.6) {
			nuovoArrivo=Duration.of(6, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.7) {
			nuovoArrivo=Duration.of(7, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.8) {
			nuovoArrivo=Duration.of(8, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<0.9) {
			nuovoArrivo=Duration.of(9, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}else if(num<1) {
			nuovoArrivo=Duration.of(10, ChronoUnit.MINUTES);
			return nuovoArrivo;
		}
		return null;
	}
	
	//metodi per impostare i parametri
	public void setT10posti(Integer t10posti) {
		T10posti = t10posti;
	}
	public void setT8posti(Integer t8posti) {
		T8posti = t8posti;
	}
	public void setT6posti(Integer t6posti) {
		T6posti = t6posti;
	}
	public void setT4posti(Integer t4posti) {
		T4posti = t4posti;
	}
	//metodi per restituire i risultati
	public Integer getNumero_totale_clienti() {
		return numero_totale_clienti;
	}
	public Integer getNumero_clienti_soddisfatti() {
		return numero_clienti_soddisfatti;
	}
	public Integer getNumero_clienti_insoddisfatti() {
		return numero_clienti_insoddisfatti;
	}
	
}
