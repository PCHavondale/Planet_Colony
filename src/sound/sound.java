package sound;

import java.io.File;
import javax.sound.sampled.*;

public class sound {
	
	public static void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
	
}