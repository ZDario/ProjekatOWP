package bioskop.model;

import java.util.GregorianCalendar;

public class Karta {
	
	private String idKarta;
	private Projekcija projekcija;
	private String sediste;
	private GregorianCalendar datumPredaje;
	private User user;
	
	public Karta(String idKarta, Projekcija projekcija, String sediste, GregorianCalendar datumPredaje, User user) {
		super();
		this.idKarta = idKarta;
		this.projekcija = projekcija;
		this.sediste = sediste;
		this.datumPredaje = datumPredaje;
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + idKarta.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Karta other = (Karta) obj;
		return idKarta.equals(other.idKarta);
	}

	public String getIdKarta() {
		return idKarta;
	}

	public void setIdKarta(String idKarta) {
		this.idKarta = idKarta;
	}

	public Projekcija getProjekcija() {
		return projekcija;
	}

	public void setProjekcija(Projekcija projekcija) {
		this.projekcija = projekcija;
	}

	public String getSediste() {
		return sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

	public GregorianCalendar getDatumPredaje() {
		return datumPredaje;
	}

	public void setDatumPredaje(GregorianCalendar datumPredaje) {
		this.datumPredaje = datumPredaje;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
