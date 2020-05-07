package bioskop.model;

import java.util.ArrayList;

public class TipProjekcije {
	
	private String idTipProjekcije;
	private String naziv;
	public ArrayList<String> tipProjekcije = new ArrayList<String>();
	
	
	public TipProjekcije(String idTipProjekcije, String naziv) {
		super();
		this.idTipProjekcije = idTipProjekcije;
		this.naziv = naziv;
		tipProjekcije.clear();
		tipProjekcije.add("2D");
		tipProjekcije.add("3D");
		tipProjekcije.add("4D");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + idTipProjekcije.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		TipProjekcije other = (TipProjekcije) obj;
		return idTipProjekcije == other.idTipProjekcije;
	}

	

	public String getIdTipProjekcije() {
		return idTipProjekcije;
	}

	public void setIdTipProjekcije(String idTipProjekcije) {
		this.idTipProjekcije = idTipProjekcije;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		if(tipProjekcije.contains(naziv)) {
			this.naziv = naziv;
		}else {
			this.naziv = "2D";
		}
	}
	
	public ArrayList<String> getTipProjekcije(){
		return tipProjekcije;
	}
	
	public void setTipProjekcije(ArrayList<String> tipProjekcije) {
		this.tipProjekcije = tipProjekcije;
	}
	
}
