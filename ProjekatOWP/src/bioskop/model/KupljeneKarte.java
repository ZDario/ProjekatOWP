package bioskop.model;

import java.util.ArrayList;
import java.util.Date;

public class KupljeneKarte {
	
	private ArrayList<Karta> karte;
	
	public KupljeneKarte() {
		karte = new ArrayList<Karta>();
	}
	
	public void dodajKartu(String idKarta,Projekcija projekcija,Sediste sediste,Date vremeProdaje,User user) {
		karte.add(new Karta(idKarta, projekcija, sediste, vremeProdaje, user));
	}
	
	public void obrisiKartu(int index) {
		karte.remove(index);
	}
	
	public ArrayList<Karta> getKarte(){
		return karte;
	}
}