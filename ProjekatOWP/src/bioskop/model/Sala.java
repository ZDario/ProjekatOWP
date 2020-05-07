package bioskop.model;

import java.util.ArrayList;

public class Sala{
	
	private String idSala;
	private String naziv;

	private ArrayList<TipProjekcije> tipProjekcije;
	private ArrayList<Sediste> sedista;
	private ArrayList<Projekcija> projekcije;


	
	public Sala(String idSala, String naziv) {
		super();
		this.idSala = idSala;
		this.naziv = naziv;
		tipProjekcije = new ArrayList<TipProjekcije>();
		sedista = new ArrayList<Sediste>();
		projekcije = new ArrayList<Projekcija>();
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

	public ArrayList<TipProjekcije> getTipProjekcija() {
		return tipProjekcije;
	}
	
	public ArrayList<Sediste> getSedista(){
		return sedista;
	}
	
	public ArrayList<Projekcija> getProjekcije(){
		return projekcije;
	}
	
}
