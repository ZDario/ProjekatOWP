package bioskop.model;

public class Sediste{
	
	private String idSediste;
	private Sala sala;
	private boolean zauzeto;
	
	public Sediste(String idSediste, Sala sala, boolean zauzeto) {
		super();
		this.idSediste = idSediste;
		this.sala = sala;
		this.zauzeto = zauzeto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + idSediste.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Sediste other = (Sediste) obj;
		return idSediste.equals(other.idSediste);
	}

	public String getIdSediste() {
		return idSediste;
	}

	public void setIdSediste(String idSediste) {
		this.idSediste = idSediste;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public boolean isZauzeto() {
		return zauzeto;
	}

	public void setZauzeto(boolean zauzeto) {
		this.zauzeto = zauzeto;
	}
	
	

}
