package bioskop.model;

import java.util.ArrayList;
import java.util.Date;

public class Projekcija {
	
	private String idProjekcija;
	private Film film;
	private TipProjekcije tipProjekcije;
	private Sala sala;
	private Date datumPrikazivanja;
	private double cena;
	private User user;
	
	private ArrayList<Karta> karte;
	
	
	public Projekcija(String idProjekcija, Film film, TipProjekcije tipProjekcije, Sala sala, Date datumPrikazivanja,
			double cena, User user) {
		super();
		this.idProjekcija = idProjekcija;
		this.film = film;
		this.tipProjekcije = tipProjekcije;
		this.sala = sala;
		this.datumPrikazivanja = datumPrikazivanja;
		this.cena = cena;
		this.user = user;
		karte = new ArrayList<Karta>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + idProjekcija.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Projekcija other = (Projekcija) obj;
		return idProjekcija.equals(other.idProjekcija);
	}
	

	public String getIdProjekcija() {
		return idProjekcija;
	}

	public void setIdProjekcija(String idProjekcija) {
		this.idProjekcija = idProjekcija;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public TipProjekcije getTipProjekcije() {
		return tipProjekcije;
	}

	public void setTip(TipProjekcije tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Date getDatumPrikazivanja() {
		return datumPrikazivanja;
	}

	public void setDatumPrikazivanja(Date datumPrikazivanja) {
		this.datumPrikazivanja = datumPrikazivanja;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ArrayList<Karta> getKarte(){
		return karte;
	}
	
}
