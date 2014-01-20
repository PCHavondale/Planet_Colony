package Mobclasses;

/**
 *  A Skeleton is something that moves around!
 * @author jimmy
 *
 */
public class Skeleton {
	public int atk = 5;
	public int def = 5;
	public int health = 10;
	public int level = 1;

	public double x;
	public double y;
	public double vx;
	public double vy;
	
	/**
	 *  Create a new Skeleton with the following variables.
	 * @param px	The X position.
	 * @param py	The Y position.
	 */
	public Skeleton(double px, double py) {
		x = px;
		y = py;
	}
	
}