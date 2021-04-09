package cz.vesely.game.common;

public class Weapon extends Item {

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

	public int getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return name + "(" + attack + "/" + defence + ") - " + level;
	}

}
