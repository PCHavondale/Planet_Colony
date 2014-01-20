package GameClass;

import img.img;
import java.awt.*;

/**
 * @author  Thomas Roberts <thomas.jack.roberts@gmail.com>
 * @version 0.0
 */
public class Menu extends Game {
	static final int MENU_MAIN = 0;
	static final int MENU_SINGLE = 1;
	static final int MENU_MULTI = 2;
	static final int MENU_OPTIONS = 3;
	static final int MENU_LEVELGEN = 4;
	
	static int menuScreen = MENU_MAIN;
	
	static void drawMenu(Graphics bbg) {
		bbg.drawImage(img.background, 1, 1, 800, 600, null);
		bbg.drawImage(img.logo, 181, 100, 437, 135, null);
		bbg.setFont(new Font("sansserif", Font.PLAIN, 20));
		bbg.setColor(Color.WHITE);
		bbg.drawString("Dev - 1b", 670, 30);
		bbg.setFont(new Font("sansserif", Font.PLAIN, 32));
		bbg.setColor(Color.BLACK);
		if (menuScreen == MENU_MAIN) {
			DrawMenuBox(100,400,"Single Player",bbg);
			DrawMenuBox(450,400,"Multiplayer",bbg);
			DrawMenuBox(100,500,"Settings",bbg);
			DrawMenuBox(450,500,"Quit",bbg);
		} else if (menuScreen == MENU_OPTIONS) {
			DrawMenuBox(100,400,"window size",bbg);
			DrawMenuBox(450,400,"volume",bbg);
			DrawMenuBox(100,500,"Particles",bbg);
			DrawMenuBox(450,500,"back",bbg);
		} else if (menuScreen == MENU_SINGLE) {
			bbg.setColor(Color.BLACK);
			DrawMenuBox(100,400,"New world",bbg);
			DrawMenuBox(450,400,"load",bbg);
			DrawMenuBox(100,500,"back",bbg);
		} else if (menuScreen == MENU_MULTI) {
			bbg.setColor(Color.BLACK);
			DrawMenuBox(100,400,"check ip",bbg);
			DrawMenuBox(450,400,"join",bbg);
			DrawMenuBox(100,500,"host",bbg);
			DrawMenuBox(450,500,"back",bbg);
			/*
			if (telluserip){
				bbg.setFont(new Font("sansserif", Font.PLAIN, 15));
				DrawMenuBox(350,300,hostAddress,bbg);
			}
			*/
		} else if (menuScreen == MENU_LEVELGEN) {
			bbg.setColor(Color.BLACK);
			bbg.drawString("NOT YET IMPLEMENTED - All make a hilly world", 30, 100);
			DrawMenuBox(100,400,"Hills",bbg);
			DrawMenuBox(450,400,"Rocky",bbg);
			DrawMenuBox(100,500,"Creative",bbg);
			DrawMenuBox(450,500,"back",bbg);
		}
		//g.drawImage(backBuffer, insets.left, insets.top, null);
	}
	
	static void DrawMenuBox(int x, int y, String text, Graphics d) {
		d.drawImage(img.box, x, y, 250, 60, null);
		d.drawRect(x,y,250,60);
		d.drawString(text, x+30, y+40);
	}

	static void mouseClick(int mX, int mY) {
			
		if (menuScreen == MENU_MAIN) {
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				menuScreen = MENU_SINGLE;
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				menuScreen = MENU_MULTI;
			} else if (mX > 100 && mY > 500 && mX < 349 && mY < 559){
				menuScreen = MENU_OPTIONS;
			} else if (mX > 450 && mY > 500 && mX < 699 && mY < 559){
				isRunning = false;
			}
		} else if (menuScreen == MENU_OPTIONS) {
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				//setSize(1000, 600);
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				System.out.println("UNDER DEVELOPMENT");
			} else if (mX > 100 && mY > 500 && mX < 349 && mY < 559){
				System.out.println("UNDER DEVELOPMENT");
			} else if (mX > 450 && mY > 500 && mX < 699 && mY < 559){
				menuScreen = MENU_MAIN;
			}
		} else if (menuScreen == MENU_SINGLE) {
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				menuScreen = MENU_LEVELGEN;
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				Level.load("levela");
				room = ROOM_GAME;
			} else if (mX > 100 && mY > 500 && mX < 349 && mY < 559){
				menuScreen = MENU_MAIN;
			} else if (mX > 450 && mY > 500 && mX < 699 && mY < 559){
				//4th button
			}
		} else if (menuScreen == MENU_MULTI) {
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				//try {
					//hostInetAddress = InetAddress.getLocalHost();
					//hostAddress = hostInetAddress.getHostAddress();
					//hostName = hostInetAddress.getHostName();
					//telluserip = true;
				//} catch (UnknownHostException ex) {
					//showError("Cannot work out your IP." + "\n" + "Are you connected to the internet?");
				//}
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				System.out.println("UNDER DEVELOPMENT");
			} else if (mX > 100 && mY > 500 && mX < 349 && mY < 559){
				System.out.println("UNDER DEVELOPMENT");
			} else if (mX > 450 && mY > 500 && mX < 699 && mY < 559){
				menuScreen = MENU_MAIN;
			}
		} else if (menuScreen == MENU_LEVELGEN) {
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				levelArray = Level.create(500,100);
				room = ROOM_GAME;
				menuScreen = MENU_MAIN;
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				levelArray = Level.create(500,100);
				room = ROOM_GAME;
				menuScreen = MENU_MAIN;
			} else if (mX > 100 && mY > 500 && mX < 349 && mY < 559){
				levelArray = Level.create(500,100);
				room = ROOM_GAME;
				menuScreen = MENU_MAIN;
			} else if (mX > 450 && mY > 500 && mX < 699 && mY < 559){
				menuScreen = MENU_MAIN;
			}
		}
	}
}