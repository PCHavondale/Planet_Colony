package img;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * @author  Thomas Roberts <thomas.jack.roberts@gmail.com>
 * @version 0.0
 */
public class img {
	
	static String imgdir = "src" + File.separator + "img" + File.separator;
	//static String imgdir = /*getpcdir() + File.separator + */"src" + File.separator + "img" + File.separator;
	
	public static BufferedImage icon = load(imgdir + "icon.png");
	
	public static BufferedImage playerl = load(imgdir + "player" + File.separator + "playerleft.png");
	public static BufferedImage playerlw = load(imgdir + "player" + File.separator + "playerleftw.png");
	public static BufferedImage playerls = load(imgdir + "player" + File.separator + "playerlefts.png");
	public static BufferedImage playerr = load(imgdir + "player" + File.separator + "playerright.png");
	public static BufferedImage playerrw = load(imgdir + "player" + File.separator + "playerrightw.png");
	public static BufferedImage playerrs = load(imgdir + "player" + File.separator + "playerrights.png");
	
	public static BufferedImage axe = load(imgdir + "axe.png");
	public static BufferedImage logo = load(imgdir + "PClogo.png");
	public static BufferedImage box = load(imgdir + "box.png");
	public static BufferedImage sky = load(imgdir + "sky.png");
	public static BufferedImage background = load(imgdir + "background.png");
	
	public static BufferedImage blockPack = load(imgdir + "packs" + File.separator + "pack.png");
	public static BufferedImage dirt = blockPack.getSubimage(0, 0, 20, 20);
	public static BufferedImage stone = load(imgdir + "block" + File.separator + "stone.png");
	public static BufferedImage grass = load(imgdir + "block" + File.separator + "grass.png");
	//public static BufferedImage dirt = load(imgdir + "block" + File.separator + "dirt.png");
	public static BufferedImage wood = load(imgdir + "block" + File.separator + "wood.png");
	public static BufferedImage leaves = load(imgdir + "block" + File.separator + "leaves.png");
	
	static BufferedImage load(String path) {
		BufferedImage tempimage = null ;
		try {
			tempimage = ImageIO.read(new File(path));
		} catch (IOException ex) {
			System.out.println("error loading img at " + path);
		}
		return tempimage;
	}
	
	public static String getpcdir(){
		
		String OS = System.getProperty("os.name").toUpperCase();
		
		if (OS.contains("WIN")){
			return System.getenv("APPDATA") + File.separator + ".PlanetColony" + File.separator;
		} else if (OS.contains("MAC")) {
			return System.getProperty("user.home") + "/Library/Application " + "Support/.PlanetColony/";
		} else if (OS.contains("NUX")) {
			return System.getProperty("user.home");
		}
		return null;
	}
	
}
