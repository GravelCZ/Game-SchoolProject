package cz.vesely.rpg;

import java.util.Scanner;

import cz.vesely.rpg.player.Postava;
import cz.vesely.rpg.player.Ruka;
import cz.vesely.rpg.player.Zbran;

public class Hlavni {

	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		Postava postava1 = nactiPostavu();
		Zbran postava1ZbranLeva = nactiZbran();
		Zbran postava1ZbranPrava = nactiZbran();
		vyzbrojPostavu(postava1, postava1ZbranLeva, postava1ZbranPrava);
		
		Postava postava2 = nactiPostavu();
		Zbran postava2ZbranLeva = nactiZbran();
		Zbran postava2ZbranPrava = nactiZbran();
		vyzbrojPostavu(postava2, postava2ZbranLeva, postava2ZbranPrava);
		
		System.out.println("Vízěz: " + souboj(postava1, postava2));
	}

	private static Postava nactiPostavu() {
		String name = s.nextLine();
		if (name == null || name.isEmpty()) {
			return null;
		}
		String utokRaw = s.nextLine();
		String obranaRaw = s.nextLine();
		String zdraviRaw = s.nextLine();
		
		int silaUtok = 0;
		int silaObrana = 0;
		int zdravi = 0;
		
		try {
			silaUtok = Integer.parseInt(utokRaw);
			silaObrana = Integer.parseInt(obranaRaw);
			zdravi = Integer.parseInt(zdraviRaw);
		} catch (NumberFormatException e) {
			System.err.println("Špatně zadaný argument: číslo");
			e.printStackTrace();
		}
		
		return new Postava(name, silaUtok, silaObrana, zdravi);
	}
	
	private static Zbran nactiZbran() {
		String name = s.nextLine();
		if (name == null || name.isEmpty()) {
			return null;
		}
		String utokRaw = s.nextLine();
		String obranaRaw = s.nextLine();
		
		int utok = 0;
		int obrana = 0;
		
		try {
			utok = Integer.parseInt(utokRaw);
			obrana = Integer.parseInt(obranaRaw);
		} catch (NumberFormatException e) {
			System.err.println("Špatně zadaný argument: číslo");
			e.printStackTrace();
		}
		
		return new Zbran(name, utok, obrana);
	}
	
	private static void vyzbrojPostavu(Postava postava, Zbran levaZbran, Zbran pravaZbran) {
		postava.vezmiZbran(Ruka.LEVA, levaZbran);
		postava.vezmiZbran(Ruka.PRAVA, pravaZbran);
	}
	
	private static Postava souboj(Postava postava1, Postava postava2) {
		boolean flag = true;
		while (postava1.jeZiva() && postava2.jeZiva()) {
			if (flag) {
				int dmg = postava2.branSe(postava1.zautoc());
				System.out.println("utoci " + postava1.toString() + " a dava " + dmg + " zraneni");
			} else {
				int dmg = postava1.branSe(postava2.zautoc());
				System.out.println("utoci " + postava2.toString() + " a dava " + dmg + " zraneni");
			}
			flag = !flag;
		}
		
		
		
		return postava1.jeZiva() ? postava1 : postava2;
	}
	
	
	
}
