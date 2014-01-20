package Mobclasses;

public class Villager {
	public int def = 5;
	public int health = 10;
	public int level = 5;
	
	public double x;
	public double y;
	public double vx;
	public double vy;
	
	/**
	 *  Create a new Villager with the following variables.
	 * @param px	The X position.
	 * @param py	The Y position.
	 */
	
	public Villager(double px, double py) {
		x = px;
		y = py;
	}
	
}