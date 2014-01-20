package GameClass;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

import player.*;
import player.Inventory.*;

public class Level extends Game {
	private static int worldx;
	private static int worldy;
	
	private static Scanner l;
	private static Scanner p;
	
	static final int TYPE_HILL = 0;
	static final int TYPE_MOUNTAIN = 1;
	static final int TYPE_CREATIVE = 2;
	
	/**
	 * Generates a new level. Justa like mama used to make!
	 * @param w The width of the desired level.
	 * @param h The height of the desired level.
	 * @return The generated level.
	 */
	
//	public static int[][] create(int w, int h, int type) {
//		int genx = 0;
//		int geny = h / 2;
//		
//		player.x = (w / 2) * Game.BLOCK_W;
//		player.y = 0;
//		player.resetStats();
//		
//		for (int y = 0; y < Inventory.invarray.length; y++) {
//			for (int x = 0; x < Inventory.invarray[y].length; x++) {
//				Inventory.invarray[y][x] = 0;
//				Inventory.invarrayno[y][x] = 0;
//			}
//		}
//		
//		Inventory.invarray[0][0] = 100;
//		Inventory.invarrayno[0][0] = 1;
//		Inventory.show = false;
//		
//		int[][] templevelArray = new int[h][w];
//		
//		int miny = 10;
//		int maxy = h - 4;
//		
//		Random randomGeneratorcreate = new Random();
//		
//		for (genx = 0; genx < w; genx++){
//			
//			int rawran = randomGeneratorcreate.nextInt(4);
//			int ran = 0;
//			
//			if (rawran == 2) {
//				ran = 1;
//			} else if (rawran == 1){
//				ran = 0;
//			} else if (rawran == 0){
//				ran = -1;
//			}
//			
//			if ((geny + ran) > miny && ((geny + ran)  < maxy)){
//				geny += ran;
//			}
//			//System.out.println("LEVEL " + genx + "," + geny);
//			templevelArray[geny][genx] = 1;
//			
//			for (int q = geny + 1; q < geny + 10; q++){
//				templevelArray[q][genx] = 2;
//			}
//			for (int q = geny + 10; q < h; q++){
//				templevelArray[q][genx] = 3;
//			}
//			
//			if (genx == (w / 2)){
//				player.y = (geny * Game.BLOCK_H) - (2 * BLOCK_H);
//			}
//			
//			
//			int treeran = randomGeneratorcreate.nextInt(12);
//			
//			if (treeran == 7 && !(genx == w / 2)){
//				templevelArray[geny - 1][genx] = 4;
//				templevelArray[geny - 2][genx] = 4;
//				templevelArray[geny - 3][genx - 1] = 5;
//				templevelArray[geny - 3][genx] = 5;
//				templevelArray[geny - 3][genx + 1] = 5;
//				templevelArray[geny - 4][genx - 1] = 5;
//				templevelArray[geny - 4][genx] = 5;
//				templevelArray[geny - 4][genx + 1] = 5;
//			}
//			
//		}
//		return templevelArray;
//		
//	}
	
	public static int[][] create(int w, int h) {
		
		for (int y = 0; y < Inventory.invarray.length; y++) {
			for (int x = 0; x < Inventory.invarray[y].length; x++) {
				Inventory.invarray[y][x] = 0;
				Inventory.invarrayno[y][x] = 0;
			}
		}
		
		Inventory.invarray[0][0] = 100;
		Inventory.invarrayno[0][0] = 1;
		Inventory.show = false;
		
		int[][] templevelArray = new int[h][w];
		
		int genx = 0;
		int geny = h / 2;
		
		int miny = 5;
		int maxy = h + 5;
		
		Random randomGeneratorcreate = new Random();
		
		for (genx = 0; genx < w; genx++){
			
			int rawran = randomGeneratorcreate.nextInt(3);
			int ran = 0;
			
			if (rawran == 2) {
				ran = 1;
			} else if (rawran == 1){
				ran = 0;
			} else if (rawran == 0){
				ran = -1;
			}
			
			if ((geny + ran) > miny && ((geny + ran)  < maxy)){
				geny += ran;
			}
			//System.out.println("LEVEL " + genx + "," + geny);
			templevelArray[geny][genx] = 1;
			
			for (int q = geny + 1; q < geny + 10; q++){
				templevelArray[q][genx] = 2;
			}
			for (int q = geny + 10; q < h; q++){
				templevelArray[q][genx] = 3;
			}
			
			if (genx == (w / 2)){
				player.x = (w / 2) * Game.BLOCK_W;
				player.y = (geny * Game.BLOCK_H) - (2 * BLOCK_H);
				player.resetStats();
			}
			
			
			int treeran = randomGeneratorcreate.nextInt(12);
			
			if (treeran == 7 && !(genx == w / 2)){
				templevelArray[geny - 1][genx] = 4;
				templevelArray[geny - 2][genx] = 4;
				templevelArray[geny - 3][genx - 1] = 5;
				templevelArray[geny - 3][genx] = 5;
				templevelArray[geny - 3][genx + 1] = 5;
				templevelArray[geny - 4][genx - 1] = 5;
				templevelArray[geny - 4][genx] = 5;
				templevelArray[geny - 4][genx + 1] = 5;
			}
			
		}
		return templevelArray;
		
	}
	
	public static void load(String name) {
		String leveltext=null;
		
		try {
			l = new Scanner(new File("src/saves/" + name + "/level.PCS"));
		} catch (Exception e){
			System.out.println("file not found ");
		}
		
		while (l.hasNext()) {
			leveltext = l.next();
		}
		
		StringReader sr = new StringReader(leveltext);
		try {
			for (int g = 0; g < worldx; g++) {
				for (int h = 0; h < worldy ; h++) {
					char c = (char) sr.read();
					int myInt = c - '0';
					levelArray[h][g] = myInt;
				}
			}
			sr.close();
		} catch (IOException ex) {
			System.out.println("Error using FilerReader");
		}
		
		//-------------------------------
		
		try {
			p = new Scanner(new File("src/saves/" + name + "/player.PCS"));
		} catch (Exception e) {
			System.out.println("file not found");
		}
		
		int linecount = 0;
		
		while(p.hasNextLine()) {
			
			String temptext = p.nextLine();
			
			//System.out.println(temptext);
			
			if (linecount == 0) {
				player.x = Double.parseDouble(temptext);
			} else if (linecount == 1) {
				player.y = Double.parseDouble(temptext);
			}/* else if (linecount == 2) {
			Inventory.invarray[0] = Integer.parseInt(temptext);
			} else if (linecount == 3) {
			Inventory.invarray[1] = Integer.parseInt(temptext);
			} else if (linecount == 4) {
			Inventory.invarray[2] = Integer.parseInt(temptext);
			} else if (linecount == 5) {
			Inventory.invarray[3] = Integer.parseInt(temptext);
			} else if (linecount == 6) {
			Inventory.invarray[4] = Integer.parseInt(temptext);
			} else if (linecount == 7) {
			Inventory.invarray[5] = Integer.parseInt(temptext);
			} else if (linecount == 8) {
			Inventory.invarray[6] = Integer.parseInt(temptext);
			} else if (linecount == 9) {
			Inventory.invarray[7] = Integer.parseInt(temptext);
			} else if (linecount == 10) {
			Inventory.invarray[8] = Integer.parseInt(temptext);
			} else if (linecount == 11) {
			Inventory.invarray[9] = Integer.parseInt(temptext);
			} else if (linecount == 12) {
			Inventory.invarrayno[0] = Integer.parseInt(temptext);
			} else if (linecount == 13) {
			Inventory.invarrayno[1] = Integer.parseInt(temptext);
			} else if (linecount == 14) {
			Inventory.invarrayno[2] = Integer.parseInt(temptext);
			} else if (linecount == 15) {
			Inventory.invarrayno[3] = Integer.parseInt(temptext);
			} else if (linecount == 16) {
			Inventory.invarrayno[4] = Integer.parseInt(temptext);
			} else if (linecount == 17) {
			Inventory.invarrayno[5] = Integer.parseInt(temptext);
			} else if (linecount == 18) {
			Inventory.invarrayno[6] = Integer.parseInt(temptext);
			} else if (linecount == 19) {
			Inventory.invarrayno[7] = Integer.parseInt(temptext);
			} else if (linecount == 20) {
			Inventory.invarrayno[8] = Integer.parseInt(temptext);
			} else if (linecount == 21) {
			Inventory.invarrayno[9] = Integer.parseInt(temptext);
			}*/
			linecount++;
		}
		player.vx = 0;
		player.vy = 0;
	}
	
	public static void save(String name) {
		System.out.println("Saving Level");
		String levelfile = "";
		
		for (int d = 0; d < worldx; d++) {
			for (int e = 0; e < worldy; e++) {
				levelfile += levelArray[e][d];
			}
		}
		
		String playerfile = "";
		
		int savex = (int) player.x;
		int savey = (int) player.y;
		
		playerfile += savex + "\n";
		playerfile += savey + "\n";
		
		for (int b = 0; b < 10; b++) {
			playerfile += Inventory.invarray[b] + "\n";
		}
		
		for (int c = 0; c < 10; c++) {
			playerfile += Inventory.invarrayno[c] + "\n";
		}
		
		FileWriter fileWriter = null;
		
		try {
			File level = new File("src/saves/" + name + "/level.PCS");
			fileWriter = new FileWriter(level);
			fileWriter.write(levelfile);
			fileWriter.close();
			
			File player = new File("src/saves/" + name + "/player.PCS");
			fileWriter = new FileWriter(player);
			fileWriter.write(playerfile);
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println("Error using FileWriter");
		} finally {
			try {
				fileWriter.close();
			} catch (IOException ex) {
				System.out.println("Error in closing fileWriter");
			}
		}
		System.out.println("Level Saved!");
	}
}