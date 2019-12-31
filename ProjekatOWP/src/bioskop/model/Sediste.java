package bioskop.model;

public class Sediste{
	
	private String redniBroj;
	private Sala sala;
	
	public Sediste(String redniBroj, Sala sala) {
		super();
		this.redniBroj = redniBroj;
		this.sala = sala;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + redniBroj.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Sediste other = (Sediste) obj;
		return redniBroj.equals(other.redniBroj);
	}

	public String getRedniBroj() {
		return redniBroj;
	}

	public void setRedniBroj(String redniBroj) {
		this.redniBroj = redniBroj;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	

}
