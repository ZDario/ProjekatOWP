package bioskop.model;

public class Film {
	
	
	private String idFilm;
	private String naziv;
	private String reziser;
	private String glumci;
	private String zanrovi;
	private int trajanje;
	private String distributer;
	private String zemljaPorekla;
	private int godinaProizvodnje;
	private String opis;
	
	
	
	public Film(String idFilm, String naziv, String reziser, String glumci, String zanrovi, int trajanje,
			String distributer, String zemljaPorekla, int godinaProizvodnje, String opis) {
		super();
		this.idFilm = idFilm;
		this.naziv = naziv;
		this.reziser = reziser;
		this.glumci = glumci;
		this.zanrovi = zanrovi;
		this.trajanje = trajanje;
		this.distributer = distributer;
		this.zemljaPorekla = zemljaPorekla;
		this.godinaProizvodnje = godinaProizvodnje;
		this.opis = opis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + idFilm.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Film other = (Film) obj;
		return idFilm == other.idFilm;
	}

	public String getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(String idFilm) {
		this.idFilm = idFilm;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getReziser() {
		return reziser;
	}

	public void setReziser(String reziser) {
		this.reziser = reziser;
	}

	public String getGlumci() {
		return glumci;
	}

	public void setGlumci(String glumci) {
		this.glumci = glumci;
	}

	public String getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(String zanrovi) {
		this.zanrovi = zanrovi;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getDistributer() {
		return distributer;
	}

	public void setDistributer(String distributer) {
		this.distributer = distributer;
	}

	public String getZemljaPorekla() {
		return zemljaPorekla;
	}

	public void setZemljaPorekla(String zemljaPorekla) {
		this.zemljaPorekla = zemljaPorekla;
	}

	public int getGodinaProizvodnje() {
		return godinaProizvodnje;
	}

	public void setGodinaProizvodnje(int godinaProizvodnje) {
		this.godinaProizvodnje = godinaProizvodnje;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
}
