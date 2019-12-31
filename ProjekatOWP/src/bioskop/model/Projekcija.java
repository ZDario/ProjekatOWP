package bioskop.model;

import java.util.GregorianCalendar;

import bioskop.model.TipProjekcije.Tip;

public class Projekcija {
	
	private String idProjekcija;
	private Film film;
	private Tip tip;
	private Sala sala;
	private GregorianCalendar datumPrikazivanja;
	private float cena;
	
	
	public Projekcija(String idProjekcija, Film film, Tip tip, Sala sala, GregorianCalendar datumPrikazivanja,
			float cena) {
		super();
		this.idProjekcija = idProjekcija;
		this.film = film;
		this.tip = tip;
		this.sala = sala;
		this.datumPrikazivanja = datumPrikazivanja;
		this.cena = cena;
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

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public GregorianCalendar getDatumPrikazivanja() {
		return datumPrikazivanja;
	}

	public void setDatumPrikazivanja(GregorianCalendar datumPrikazivanja) {
		this.datumPrikazivanja = datumPrikazivanja;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}
	
	
	
}
