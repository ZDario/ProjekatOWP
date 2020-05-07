package bioskop.model;

import java.util.Date;

public class Karta {
	
	private String idKarta;
	private Projekcija projekcija;
	private Sediste sediste;
	private Date vremeProdaje;
	private User user;
	
	public Karta(String idKarta, Projekcija projekcija, Sediste sediste,  Date vremeProdaje, User user) {
		super();
		this.idKarta = idKarta;
		this.projekcija = projekcija;
		this.sediste = sediste;
		this.vremeProdaje = vremeProdaje;
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

	public Sediste getSediste() {
		return sediste;
	}

	public void setSediste(Sediste sediste) {
		this.sediste = sediste;
	}

	public Date getVremeProdaje() {
		return vremeProdaje;
	}

	public void setVremeProdaje(Date vremeProdaje) {
		this.vremeProdaje = vremeProdaje;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
