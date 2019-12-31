package bioskop.model;

import bioskop.model.TipProjekcije.Tip;

public class Sala{
	
	private String idSala;
	private String naziv;
	private Tip tip;


	
	public Sala(String idSala, String naziv, Tip tip) {
		super();
		this.idSala = idSala;
		this.naziv = naziv;
		this.tip = tip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + naziv.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Sala other = (Sala) obj;
		return idSala == other.idSala;
	}
	

	public String getIdSala() {
		return idSala;
	}

	public void setIdSala(String idSala) {
		this.idSala = idSala;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
	}
	
}
