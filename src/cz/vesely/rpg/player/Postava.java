package cz.vesely.rpg.player;

public class Postava {

	private String jmeno;
	private int silaUtok;
	private int silaObrana;
	private int zdravi;
	
	private Zbran levaRuka;
	private Zbran pravaRuka;
	
	public Postava(String jmeno, int silaUtok, int silaObrana, int zdravi) {
		this.jmeno = jmeno;
		this.silaUtok = silaUtok;
		this.silaObrana = silaObrana;
		this.zdravi = zdravi;
	}
	
	// tento kod se mi nelíbí ale nevím jak bych ho mohl napsat lépe
	public boolean vezmiZbran(Ruka ruka, Zbran zbran) {
		if (ruka == Ruka.LEVA) {
			if (levaRuka == null) {
				this.levaRuka = zbran;
				return true;
			}
		} else if (ruka == Ruka.PRAVA) {
			if (pravaRuka == null) {
				this.pravaRuka = zbran;
				return true;
			}
		}
		
		return false;
	}
	
	
	public int branSe(int utok) {
		if (utok < getObranaSeZbrani()) {
			return 0;
		}
		int damage = utok - getObranaSeZbrani();

		this.zdravi -= damage;
		if (this.zdravi <= 0) {
			this.zdravi = 0;
		}
		
		return damage;
	}
	
	public int zautoc() {
		return getUtokSeZbrani();
	}
	
	public boolean jeZiva() {
		return zdravi > 0;
	}
	
	private int getObrana() {
		return this.silaObrana;
	}
	
	private int getObranaSeZbrani() {
		int obrana = getObrana();
		if (levaRuka != null) {
			obrana += levaRuka.getObrana();
		}
		if (pravaRuka != null) {
			obrana += pravaRuka.getObrana();
		}
		return obrana;
	}
	
	private int getUtok() {
		return this.silaUtok;
	}
	
	private int getUtokSeZbrani() {
		int utok = getUtok();
		if (levaRuka != null) {
			utok += levaRuka.getUtok();
		}
		if (pravaRuka != null) {
			utok += pravaRuka.getUtok();
		}
		return utok;
	}
	
	@Override
	public String toString() {
		return this.jmeno + " [" + this.zdravi + "] (" + this.getUtokSeZbrani() + "/" + this.getObranaSeZbrani() + ")";
	}
}
