package cz.vesely.game.common;

public class PlayerLoginInformation {

	private int id;
	private int attack;
	private int defence;
	private int health;
	
	public PlayerLoginInformation(int id, int attack, int defence, int health) {
		this.id = id;
		this.attack = attack;
		this.defence = defence;
		this.health = health;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
