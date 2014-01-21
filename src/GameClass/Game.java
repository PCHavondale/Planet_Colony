package GameClass;

/**
 * @author  Thomas Roberts <thomas.jack.roberts@gmail.com>
 * @version 0.1
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.net.*;

import javax.swing.*;

import Mobclasses.*;
import img.*;
import player.*;
import sound.*;

//TODO LIST
//
//Comment save/load + world gen.
//Save/load errors. Revise save/load methods.
//World gen is giving errors.  Revise code.
//save is slow. switch '1111111111' to '1x10' / other format.

public class Game extends JFrame implements MouseListener, KeyListener{
	
	private static LinkedList<Skeleton> skeletons = new LinkedList();
	private static LinkedList<Villager> villager = new LinkedList();
	
	static final int fps = 30;
	static final double a = 0.4;
	static String osname;
	
	int windowWidth = 800;
	int windowHeight = 600;
	
	static final int ROOM_MENU = 0;
	static final int ROOM_GAME = 1;
	static final int ROOM_PAUSE = 2;
	static int room = ROOM_MENU;
	
	static final int BLOCK_H = 20;
	static final int BLOCK_W = 20;
		
	static boolean isRunning = false;
	static boolean dev = false;
	static boolean[] keystates = new boolean[]{false,false,false,false};
	static int [][] levelArray = Level.create(500,100);
	
	final int KEY_UP = 0;
	final int KEY_DOWN = 1;
	final int KEY_LEFT = 2;
	final int KEY_RIGHT = 3;
	
	//Get session info
	//TODO: GET DATE
	String osName = System.getProperty("os.name");
	String osMilltime = String.valueOf(System.currentTimeMillis());
	String osVersion = System.getProperty("os.version");
	String osArch = System.getProperty("os.arch");
	String javaVersion = System.getProperty("java.version");
	String javaVendor = System.getProperty("java.vendor");
	String userName = System.getProperty("user.name");
	String userHome = System.getProperty("user.home");
	//TODO: SEND TO SERVER DATABASE
	
	BufferedImage backBuffer;
	Insets insets;
	
	public static Random randomGenerator = new Random();
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		System.exit(0);
	}
	
	public Game() {
		Game.isRunning = true;
		
		System.out.println(osName);
		System.out.println(osVersion);
		System.out.println(osArch);
		System.out.println(javaVersion);
		System.out.println(javaVendor);
		System.out.println(userName);
		System.out.println(userHome);
	}
	
	public double getFPS(LinkedList updates, long ms) {
		long min_time = System.currentTimeMillis() - ms;
		int n = updates.size();
		int frames = 0;
		for(int i=n-1;i>=0;i--) {
			long l = ((Long)updates.get(i)).longValue();
			if (l<min_time) break;
			frames++;
		}
		return frames * 1000 / ms;
	}
	
	public void run() {
		initialize();
		
		long lastUpdate = System.currentTimeMillis();
		double targetFPS = 40;
//		long PERIOD_ADJUSTMENT = 1000 * 1;
		long last_adjustment = System.currentTimeMillis();
		long loop_sleep = (long) (1000 / targetFPS);
		LinkedList updates = new LinkedList();
		
		while (isRunning) {
			lastUpdate = System.currentTimeMillis();
			if (room == ROOM_MENU) {
				Graphics g = getGraphics();
				Graphics bbg = backBuffer.getGraphics();
				Menu.drawMenu(bbg);
				g.drawImage(backBuffer, insets.left, insets.top, null);
			} else if (room == ROOM_GAME) {
				drawLevel();
				update();
			} else if (room == ROOM_PAUSE) {
				drawPause();
			}
/*
			long now = System.currentTimeMillis();
			updates.add(new Long(now));	// Log the update			

			long min_time = now - (1000 * 60);
			while(true) {
				if (updates.size()==0) break;
				Long l = (Long)updates.get(0);
				if (l<min_time) {
					updates.removeFirst();
				} else {
					break;
				}
			}

//			if (now - last_adjustment > PERIOD_ADJUSTMENT) {
				double currentFPS = getFPS(updates, 5000);
				if (currentFPS<targetFPS) {
					loop_sleep--;
				} else if (currentFPS>targetFPS) {
					loop_sleep++;
				}
				
				System.out.println("FPS Adjustment: Current FPS 5=" + currentFPS + " 30=" + getFPS(updates, 30000) + " loop_sleep=" + loop_sleep);
				
//				last_adjustment = now;
//			}
			
			if (loop_sleep > 0) {
				try{
					Thread.sleep(loop_sleep);
				} catch(Exception e) {
					System.out.println("error in fps handling");
				}
			}
			*/
		long delay = (1000 / fps) - (System.currentTimeMillis() - lastUpdate);
			if (delay > 0) {
				try{
					Thread.sleep(delay);
				} catch(InterruptedException e) {
					System.out.println("error in fps handling");
				}
			}
		
		}
		setVisible(false);
	}
	
	void initialize() {	
		setTitle("Planet Colony - Developers Edition");
		
		setSize(windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(img.icon);
		
		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right, insets.top + windowHeight + insets.bottom);
		
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		
		addMouseListener(this);
		addKeyListener(this);
		
		villager.add(new Villager(player.x - 100, player.y - 100));
		//skeletons.add(new Skeleton(100,150));
		
		//sound.play("theme.wav");
		
   	}
	
	void update() {
		if (room == ROOM_GAME) {
			
        	if (keystates[KEY_UP]) {}
        	if (keystates[KEY_DOWN]) {}
        	if (keystates[KEY_LEFT]) {player.vx -= 1.2;}
        	if (keystates[KEY_RIGHT]) {player.vx += 1.2;}
        	if (keystates[KEY_LEFT] == keystates[KEY_RIGHT]) {player.vx = player.vx / 1.1;}
			
			double[] newposition = Movement.checkForMapCollisions(player.x, player.y, player.vx, player.vy, player.width, player.height);
			
			player.x = newposition[0];
			player.y = newposition[1];
			
			player.vx = Math.max(Math.min(player.vx, 3), -3);
        	player.vy = Math.max(Math.min(player.vy, 7), -7);
			
			player.vy += a;
			
			for (int b=0; b < villager.size(); b++) {
				Villager s = (Villager) villager.get(b);
				//int sx = s.x;
				s.x++;
			}
			
		}
	}
	
	void drawPause() {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		bbg.setColor(new Color(0f, 0f, 0f, 0.05f));
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		bbg.setColor(Color.BLUE);
		bbg.fillRect(100, 400, 250, 60);
		bbg.fillRect(450, 400, 250, 60);
		bbg.setColor(Color.WHITE);
		bbg.setFont(new Font("sansserif", Font.PLAIN, 32));
		bbg.drawString("Back", 130, 440);
		bbg.drawString("Exit", 480, 440);
		bbg.setFont(new Font("sansserif", Font.PLAIN, 36));
		bbg.drawString("PAUSED", 300, 200);
		g.drawImage(backBuffer, insets.left, insets.top, null);
	}
	
	void drawLevel() {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(new Color(79, 205, 251));
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		//bbg.drawImage(img.sky, 0, 0, null);
		
		int windowWidthMap = windowWidth / BLOCK_W;
		int windowHeightMap = windowHeight / BLOCK_H;
		
		int viewportOffsetX = (windowWidth / 2) - 10;
		int viewportOffsetY = (windowHeight / 2) - 20;

		int px = (int)player.x;
		int py = (int)player.y;
		
		int offsetx = (px - viewportOffsetX) % BLOCK_W;
		int offsety = (py - viewportOffsetY) % BLOCK_H;
		int mapx = (px - viewportOffsetX) / BLOCK_W;
		int mapy = (py - viewportOffsetY) / BLOCK_H;
		
		for (int i = 0; i <= windowHeightMap; i++) {
			for (int j = 0; j <= windowWidthMap; j++) {
				int block = 0;	// Assume empty air
				if ((i + mapy) >= 0 && ((i + mapy) < levelArray.length)
				  &&(j + mapx) >= 0 && ((j + mapx) < levelArray[i + mapy].length)) {
					block = levelArray[i+mapy][j+mapx];
				}
				if (block == Inventory.ITEM_BLOCK_GRASS) {
					bbg.drawImage(img.grass, (j * BLOCK_W) - offsetx, (i * BLOCK_H) - offsety, null);
				} else if (block == Inventory.ITEM_BLOCK_DIRT) {
					bbg.drawImage(img.dirt, (j * BLOCK_W) - offsetx, (i * BLOCK_H) - offsety, null);
				} else if (block == Inventory.ITEM_BLOCK_STONE) {
					bbg.drawImage(img.stone, (j * BLOCK_W) - offsetx, (i * BLOCK_H) - offsety, null);
				} else if (block == Inventory.ITEM_BLOCK_WOOD) {
					bbg.drawImage(img.wood, (j * BLOCK_W) - offsetx, (i * BLOCK_H) - offsety, null);
				} else if (block == Inventory.ITEM_BLOCK_LEAVES) {
					bbg.drawImage(img.leaves, (j * BLOCK_W) - offsetx, (i * BLOCK_H) - offsety, null);
				}
			}
		}
		
		Inventory.drawinv(bbg);
		
		bbg.setColor(Color.PINK);
		for (int b=0; b < villager.size(); b++) {
			Villager s = (Villager) villager.get(b);
			//int sx = s.x;
			bbg.fillRect((int) s.x - offsetx, (int) s.y - offsety , 20, 40);
		}
		
		if (player.vx < 0) {
			if (keystates[2]){
				bbg.drawImage(img.playerlw, viewportOffsetX, viewportOffsetY, null);
			} else if (player.vx < -1) {
				bbg.drawImage(img.playerls, viewportOffsetX, viewportOffsetY, null);
			} else {
				bbg.drawImage(img.playerl, viewportOffsetX, viewportOffsetY, null);
			}
		} else {
			if (keystates[3]){
				bbg.drawImage(img.playerrw, viewportOffsetX, viewportOffsetY, null);
			} else if (player.vx > 1) {
				bbg.drawImage(img.playerrs, viewportOffsetX, viewportOffsetY, null);
			} else {
				bbg.drawImage(img.playerr, viewportOffsetX, viewportOffsetY, null);
			}
		}
		
		if (Inventory.drag){
			Point window = getLocation();
			Point curser = MouseInfo.getPointerInfo().getLocation();
			
			//Point drawAt = subtract(window, curser);
			double x = curser.getX();
			double y = curser.getY();
			g.drawImage((BufferedImage)Inventory.images.get(Inventory.dragType), (int) x, (int) y, null);
			g.drawString(String.valueOf(Inventory.dragNo), (int) x + 10, (int) y + 30);
		}
		
		g.drawImage(backBuffer, insets.left, insets.top, this);
		
	}
	
	public void showError(String message){
		Component frame = null;
		JOptionPane.showMessageDialog(frame,message + "\n" + "send me an email at: thomas.jack.roberts@gmail.com","Error",JOptionPane.WARNING_MESSAGE);
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP || key == 87 || key == 32) {
			player.vy =- 7;
			keystates[0] = true;
		} else if (key == KeyEvent.VK_DOWN || key == 83) {
			keystates[1] = true;
		} else if (key == KeyEvent.VK_LEFT || key == 65) {
			keystates[2] = true;
		} else if (key == KeyEvent.VK_RIGHT || key == 68) {
			keystates[3] = true;
		} else if (key == 76) {
			Level.load("levela");
		} else if (key == 80) {
			Level.save("levela");
		} else if (key == 86) {
			//villager.add(new Villager(player.x - 100, player.y - 100));
		} else if (key == 69) {
			Inventory.show = !Inventory.show;
		} else if (key == 78) {
			levelArray = Level.create(500,200);
		} else if (key == 74) {
			levelArray = Level.create(500,200);
			room = ROOM_GAME;
		} else if (key == 27) {
			if (room == ROOM_GAME){
				room = ROOM_PAUSE;
			} else if (room == ROOM_PAUSE){
				room = ROOM_GAME;
			}
		}
		
		if (key>=48 && key<=57) {		// 0 - 9
			Inventory.invsel = ((key - 39) % 10);
		}
		
		//System.out.println(key);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(key + "up");
		if (key == KeyEvent.VK_UP || key == 87 || key == 32) {
			keystates[0] = false;
		} else if (key == KeyEvent.VK_DOWN || key == 83) {
			keystates[1] = false;
		} else if (key == KeyEvent.VK_LEFT || key == 65) {
			keystates[2] = false;
		} else if (key == KeyEvent.VK_RIGHT || key == 68) {
			keystates[3] = false;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {}
	
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	@Override
	public void mousePressed(MouseEvent me) {
		//Gets mouse location and comphensates for windows vista borders.
		int mX = me.getX() - 4;
		int mY = me.getY() - 24;
		
		int viewportOffsetX = (windowWidth / 2) - 10;
		int viewportOffsetY = (windowHeight / 2) - 20;
		
		int mapX = (mX + (int)player.x - viewportOffsetX) / BLOCK_W;
		int mapY = (mY + (int)player.y - viewportOffsetY) / BLOCK_H;
		
		//System.out.println("MOUSE PRESS " + mX + "," + mY+ " - " + mapX + "," + mapY);
		if (room == ROOM_MENU){
			Menu.mouseClick(mX, mY);
		}
		
		if (room == ROOM_GAME && me.getButton() == 1 && pointDistance((mX + (int)player.x - viewportOffsetX), (mY + (int)player.y - viewportOffsetY), (int)player.x, (int)player.y) < player.reach){
			try{
				if(!mouseCollidePlayer(mX, mY)){
					if (levelArray[mapY][mapX] > 0
						&& Inventory.invarray[0][Inventory.invsel] > 99
						&& Inventory.invarray[0][Inventory.invsel] < 111){
						
						int hitblock = levelArray[mapY][mapX];
						levelArray[mapY][mapX] = Inventory.ITEM_BLANK;
							Inventory.add(hitblock,1);
					} else if (levelArray[mapY][mapX] == 0 && Inventory.invarray[0][Inventory.invsel] < 100){
						levelArray[mapY][mapX] = Inventory.invarray[0][Inventory.invsel];
						Inventory.rem(Inventory.invsel,1);
					}
				}
			} catch (Exception ex){
				
			}
		
		} else if (room == ROOM_PAUSE){
			if (mX > 100 && mY > 400 && mX < 349 && mY < 459){
				room = ROOM_GAME;
			} else if (mX > 450 && mY > 400 && mX < 699 && mY < 459){
				room = ROOM_MENU;
			}
		}
	}
	
	public boolean mouseCollidePlayer(int mouseX, int mouseY){
		
		if (mouseX > (windowWidth / 2) - 10
			&& mouseX < (windowWidth / 2) - 10 + player.width
			&& mouseY > (windowHeight / 2) - 20
			&& mouseY < (windowHeight / 2) - 20 + player.height){
			return true;
		}
		
		//System.out.println(mouseX);
		//System.out.println(mouseY);
		//System.out.println(player.x / BLOCK_W);
		//System.out.println(player.y / BLOCK_H);
		
		return false;
	}
	
	public int pointDistance(int x1, int y1, int x2, int y2){
		int distance = (int) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		
		return distance;
	}
	
    @Override
    public void mouseReleased(MouseEvent me) {
	int mX = me.getX() - 4;
	int mY = me.getY() - 24;
	
	if (me.getButton() == 1 && room == ROOM_GAME) {
			
			loop:
			for (int h = 0; h < 4; h++) {
				for (int g = 0; g < 11; g++){
					if (mY > (h * 50) + 10 && mY < (h * 50) + 50 && mX > (g * 50) + 10 && mX < (g * 50) + 50) {
						
						if (Inventory.drag == false && Inventory.invarray[h][g] > 0){
							Inventory.drag = true;
							Inventory.dragType = Inventory.invarray[h][g];
							Inventory.dragNo = Inventory.invarrayno[h][g];
							Inventory.invarray[h][g] = 0;
							Inventory.invarrayno[h][g] = 0;
						} else if (Inventory.drag == true && Inventory.invarray[h][g] == 0){
							Inventory.drag = false;
							Inventory.invarray[h][g] = Inventory.dragType;
							Inventory.invarrayno[h][g] = Inventory.dragNo;
							Inventory.dragType = 0;
							Inventory.dragNo = 0;
						}
						
						break loop;
						//d.fillRect((w * 50) + 10, (h * 50) + 10, 40, 40);
						
					}
				}
			}
			
			loop2:
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++){
					if (mX > (x * 50) + 560 && 
						mX < (x * 50) + 600 && 
						mY > (y * 50) + 10 && 
						mY < (y * 50) + 50) {
						
						//System.out.println("click at box " + y + ", " + x);
						
						if (Inventory.drag == false && Inventory.craftarray[y][x] > 0){
							
							Inventory.drag = true;
							Inventory.dragType = Inventory.craftarray[y][x];
							Inventory.dragNo = Inventory.craftarrayno[y][x];
							Inventory.craftarray[y][x] = 0;
							Inventory.craftarrayno[y][x] = 0;
							
							//System.out.println("drag" + Inventory.drag);
							//System.out.println("dragType" + Inventory.dragType);
							//System.out.println("dragNo" + Inventory.dragNo);
						} else if (Inventory.drag == true && Inventory.craftarray[y][x] == 0){
							Inventory.drag = false;
							Inventory.craftarray[y][x] = Inventory.dragType;
							Inventory.craftarrayno[y][x] = Inventory.dragNo;
							Inventory.dragType = 0;
							Inventory.dragNo = 0;
							//System.out.println("drag" + Inventory.drag);
							//System.out.println("dragType" + Inventory.dragType);
							//System.out.println("dragNo" + Inventory.dragNo);
						}
						
						break loop2;
						//d.fillRect((w * 50) + 10, (h * 50) + 10, 40, 40);
						
					}
				}
			}
			
			//Press Craft
			if (mX > 580 && mX < 680 && mY > 160 && mY < 200) {
				
				if (Arrays.deepEquals(Inventory.craftarray,Craft.stone)){
					
					Inventory.clearCraft();
					Inventory.add(3,1);
				}
			}
			
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent me) {}
	
	@Override
	public void mouseExited(MouseEvent me) {}
}