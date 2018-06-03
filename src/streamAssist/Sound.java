package streamAssist;

import java.io.File;
import javax.sound.sampled.*;


public class Sound{
	public static File f;
	
	public Sound(File f){
		Sound.f = f;
	}
	
	public static void play(){
		try{
			  AudioInputStream ais = AudioSystem.getAudioInputStream(f);
			  Clip clip = AudioSystem.getClip();
			  clip.open(ais);
			  clip.start();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
	}
	
//  public static void main(String[] args) {
//	  new Sound(new File("resources/sounds/sound.wav")).play();
//	  
//  }
}