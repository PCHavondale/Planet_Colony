package player;

import img.*;
import java.awt.*;
import GameClass.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Inventory extends Game {
	
	/*
	 * Item code boundaries.
	 * 0 blank
	 * 1 - 99 block
	 * 100 - 110 item + breaking
	 * 111 - 120 item + combat
	 */
	
	public static int [][] invarray = {{0,0,0,0,0,0,0,0,0,0},
									   {0,0,0,0,0,0,0,0,0,0},
									   {0,0,0,0,0,0,0,0,0,0},
									   {0,0,0,0,0,0,0,0,0,0}};
	
	public static int [][] invarrayno = {{0,0,0,0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0,0,0,0},
										 {0,0,0,0,0,0,0,0,0,0}};
	
	public static int [][] craftarray = {{0,0,0},{0,0,0},{0,0,0}};
	
	public static int [][] craftarrayno = {{0,0,0},{0,0,0},{0,0,0}};
	
	public static int invsel = 0;
	public static boolean show = false;
	
	public static boolean drag = false;
	public static int dragType = 0;
	public static int dragNo = 0;
	
	public static final int ITEM_BLANK = 0;
	
	public static final int ITEM_BLOCK_GRASS = 1;
	public static final int ITEM_BLOCK_DIRT = 2;
	public static final int ITEM_BLOCK_STONE = 3;
	public static final int ITEM_BLOCK_WOOD = 4;
	public static final int ITEM_BLOCK_LEAVES = 5;
	
	public static final int ITEM_AXE = 100;
	
	public static final int ITEM_SWORD = 111;
	
	public static HashMap images = new HashMap();
	static {
		images.put(new Integer (ITEM_BLOCK_GRASS), img.grass);
		images.put(new Integer (ITEM_BLOCK_DIRT), img.dirt);
		images.put(new Integer (ITEM_BLOCK_STONE), img.stone);
		images.put(new Integer (ITEM_BLOCK_WOOD), img.wood);
		images.put(new Integer (ITEM_BLOCK_LEAVES), img.leaves);
		images.put(new Integer (ITEM_AXE), img.axe);
	}
	
	public static void add(int des, int amt) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				if (invarray[y][x] == 0 || invarray[y][x] == des && invarrayno[y][x] < 50) {
					invarray[y][x] = des;
					invarrayno[y][x] += amt;
					return;	
				}
			}
		}
	}
	
	public static void rem(int des, int amt) {
		invarrayno[0][des] -= amt;
		
		if (invarrayno[0][des] == 0){
			invarray[0][des] = 0;
		}
	}
	
	static public void drawinv(Graphics d) {
		//Draw selecected border
		d.setColor(Color.WHITE);
		d.drawRect((invsel * 50) + 9, 9, 41, 41);
		
		if (show) {
			//Draw inv
			for (int h=0; h<=3; h++) {
				for (int w=0; w<10; w++) {
					d.setColor(Color.BLUE);
					d.fillRect((w * 50) + 10, (h * 50) + 10, 40, 40);
					
					d.drawImage((BufferedImage)images.get(Inventory.invarray[h][w]), (w * 50) + 20, (h * 50) + 20, null);
					if (Inventory.invarrayno[h][w] > 1) {
						d.setColor(Color.WHITE);
						String tempno = Integer.toString(Inventory.invarrayno[h][w]);
						d.drawString(tempno, (w * 50) + 10, (h * 50) + 30);
					}
					
				}
			}
			
			//Draw craft
			
			for (int h=0; h<3; h++){
				for (int w=0; w<3; w++){
					d.setColor(Color.BLUE);
					d.fillRect((w * 50) + 560, (h * 50) + 10, 40, 40);
					
					d.drawImage((BufferedImage)images.get(Inventory.craftarray[h][w]), (w * 50) + 570, (h * 50) + 20, null);
					if (Inventory.craftarrayno[h][w] > 1) {
						d.setColor(Color.WHITE);
						String tempno = Integer.toString(Inventory.craftarrayno[h][w]);
						d.drawString(tempno, (w * 50) + 560, (h * 50) + 30);
					}
				}
			}
			
			//Draw craft button
			d.setColor(Color.WHITE);
			d.fillRect(580, 160, 100, 40);
			d.setColor(Color.BLACK);
			d.setFont(new Font("sansserif", Font.PLAIN, 32));
			d.drawString("Craft!", 590, 190);
			
		} else {
			for (int h=0; h<10; h++) {
				d.setColor(Color.BLUE);
				d.fillRect((h * 50) + 10, 10, 40, 40);
				d.drawImage((BufferedImage)images.get(Inventory.invarray[0][h]), (h * 50) + 20, 20, null);
				if (Inventory.invarrayno[0][h] > 1) {
					d.setColor(Color.WHITE);
					String tempno = Integer.toString(Inventory.invarrayno[0][h]);
					d.drawString(tempno, (h * 50) + 10, 30);
				}
			}
		}
		
//		if (drag){
//			d.drawImage((BufferedImage)images.get(dragType), mousex, mousey, null);
//			d.drawString(dragNo, mousex + 10, mousey + 30);
//		}
		
	}
	
	public static void clearCraft() {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				
				craftarray[y][x] = 0;
				craftarrayno[y][x] = 0;
				
			}
		}
	}
	
}