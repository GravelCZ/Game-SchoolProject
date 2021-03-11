package cz.vesely.game.common;

public class Weapon {

	private String name;
	private int attack;
	private int defence;
	private int level;
	
	public Weapon(String name, int attack, int defence, int level) {
		this.name = name;
		this.attack = attack;
		this.defence = defence;
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	@Override
	public String toString() {
		return name + "(" + attack + "/" + defence + ") - " + level;
	}
	
}
