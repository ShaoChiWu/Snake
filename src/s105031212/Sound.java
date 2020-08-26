package s105031212;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private static Clip clip;

	public Sound(String fileName) {
		// specify the sound to play
		// (assuming the sound can be played by the audio system)
		// from a wave File
		try {
			File file = new File(fileName);
			if (file.exists()) {
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				// load the sound into memory (a Clip)
				clip = AudioSystem.getClip();
				clip.open(sound);
			} else {
				throw new RuntimeException("Sound: file not found: " + fileName);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Malformed URL: " + e);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Unsupported Audio File: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Input/Output Error: " + e);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
		}

		// play, stop, loop the sound clip
	}

	public static void play() {
		clip.setFramePosition(0); // Must always rewind!
		clip.start();
	}

	public static void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void stop() {
		clip.stop();
	}
}