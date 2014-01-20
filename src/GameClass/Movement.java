package GameClass;

/**
 * @author  Thomas Roberts <thomas.jack.roberts@gmail.com>
 * @version 0.0
 */

import static GameClass.Game.BLOCK_W;
import static GameClass.Game.BLOCK_H;

public class Movement {
	
	public static double[] checkForMapCollisions(double character_x,double character_y, double vx, double vy, int width, int height) {
		double newposition[] = new double[2];
		newposition[0] = character_x + vx;
		newposition[1] = character_y + vy;

		//Now check if there's anything in the way of our character being there...
		if (canGoHere(character_x + vx, character_y + vy)) return newposition;
		
		//Now check how far we can apply vx before we hit something...
		newposition[0] = character_x;
		if (vx > 0) {
			for(double xx = character_x; xx <= (character_x + vx); xx++) {
				if (canGoHere(xx, character_y)) {
					newposition[0] = xx;
				} else {
					break;
				}
			}
		} else if (vx < 0) {
			for(double xx = character_x; xx >= (character_x + vx); xx--) {
				if (canGoHere(xx, character_y)) {
					newposition[0] = xx;
				} else {
					break;
				}
			}
		}

		//Now check how far we can apply vy before we hit something...
		newposition[1] = character_y;
		if (vy > 0) {
			for(double yy = character_y; yy <= (character_y + vy); yy++) {
				if (canGoHere(newposition[0], yy)) {
					newposition[1] = yy;
				} else {
					break;
				}
			}
		} else if (vy < 0) {
			for(double yy = character_y; yy >= (character_y + vy); yy--) {
				if (canGoHere(newposition[0], yy)) {
					newposition[1] = yy;
				} else {
					break;
				}
			}			
		}

		return newposition;
	}
	
	private static boolean canGoHere(double x, double y) {
		// find top left
		int map_x = (int) Math.round((x - (BLOCK_W/2)) / BLOCK_W);
		int map_y = (int) Math.round((y - (BLOCK_H/2)) / BLOCK_H);
		
		// find bottom right
		int map_ex = (int) Math.round((x + (BLOCK_W/2) - 1) / BLOCK_W);
		int map_ey = (int) Math.round((y + (BLOCK_H/2) - 1) / BLOCK_H) + 1;	// 2 blocks high
		
		for (int l = map_y; l <= map_ey; l++) {
			for (int v = map_x; v <= map_ex; v++) {
				if (l<0 || l >= Game.levelArray.length || v<0 || v >= Game.levelArray[l].length) {
					// outside map
				} else {
					if (Game.levelArray[l][v] != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
