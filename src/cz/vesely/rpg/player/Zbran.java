package cz.vesely.rpg.player;

public class Zbran {

	private String nazev;
	private int utok;
	private int obrana;
	
	public Zbran(String nazev, int utok, int obrana) {
		this.nazev = nazev;
		this.utok = utok;
		this.obrana = obrana;
	}
	
	public String getNazev() {
		return nazev;
	}
	
	public int getObrana() {
		return obrana;
	}
	
	public int getUtok() {
		return utok;
	}
	
	@Override
	public String toString() {
		return nazev + "(" + utok + "/" + obrana + ")";
	}
	
}
