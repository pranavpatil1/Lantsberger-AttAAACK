import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;
/**
 * class Sound plays different kinds of sounds static-ly
 *
 * @author Pranav Patil
 * @version 06.03.18
 */
public class Sound
{
    private static List<MediaPlayer> media = new ArrayList<MediaPlayer>();
    /**
     * method loop takes a file and plays it on an infinite loop
     * @param   file    string for file path
     */
    public static void loop (String file)
    {
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        media.add(mediaPlayer);
        
        // stolen from StackOverflow
        // https://stackoverflow.com/questions/23498376/ahow-to-make-a-mp3-repeat-in-javafx
        mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        mediaPlayer.play();
        media.remove(mediaPlayer);
    }

    /**
     * method loop takes a file and plays it on an infinite loop
     * @param   file    string for file path
     * @param   volume  volume of sound from 0.0 to 1.0
     */
    public static void loop (String file, double volume)
    {
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        media.add(mediaPlayer);
        mediaPlayer.setVolume(volume);
        
        // stolen from StackOverflow
        // https://stackoverflow.com/questions/23498376/ahow-to-make-a-mp3-repeat-in-javafx
        mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        mediaPlayer.play();
        media.remove(mediaPlayer);
    }

    /**
     * method play takes a file and plays it once
     * @param   file    string for file path
     */
    public static void play (String file)
    {
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        media.add(mediaPlayer);
        mediaPlayer.play();
    }

    /**
     * method play takes a file and plays it once
     * @param   file    string for file path
     * @param   volume  volume of sound from 0.0 to 1.0
     */
    public static void play (String file, double volume)
    {
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        media.add(mediaPlayer);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }
}
