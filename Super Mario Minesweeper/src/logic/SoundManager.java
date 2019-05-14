/*
 * This class controls everything that has to do with the sounds and music of the game
 */
package logic;
import java.io.*;
import sun.audio.*;
import javax.sound.sampled.*;
/**
 *
 * @author Eric
 */
public class SoundManager{
    private Clip musicClip;
    public void playMusic(String path)
    {
        
        try
        {
            musicClip = AudioSystem.getClip();
            InputStream file = new FileInputStream(path);
            InputStream bufferedFile = new BufferedInputStream(file);
            AudioInputStream audioFile = AudioSystem.getAudioInputStream(bufferedFile);
            
            musicClip.open(audioFile);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    
    public void stopMusic()
    {
        musicClip.stop();
    }
    
    public void playSound(String path)
    {
        InputStream soundFile;
        try
        {
            soundFile = new FileInputStream(new File(path));
            AudioStream sound = new AudioStream(soundFile);
            AudioPlayer.player.start(sound);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    
}
