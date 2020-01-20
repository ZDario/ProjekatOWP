package bioskop.model;

public class TipProjekcije {
	
	public enum Tip {D2,D3,D4};
	
	private String idTipProjekcije;
	private Tip tip;
	
	
	public TipProjekcije(String idTipProjekcije,Tip tip) {
		this.idTipProjekcije = idTipProjekcije;
		this.tip = tip;
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

	

	public String getId() {
		return idTipProjekcije;
	}

	public void setId(String id) {
		this.idTipProjekcije = id;
	}

	public Tip getTip() {
		return tip;
	}


	public void setTip(Tip tip) {
		this.tip = tip;
	}
	
}
