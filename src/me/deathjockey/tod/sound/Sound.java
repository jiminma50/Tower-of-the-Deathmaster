package me.deathjockey.tod.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public static final Sound walk = new Sound("/walk.wav");
	public static final Sound cell = new Sound("/cell.wav");
	public static final Sound door = new Sound("/door.wav");
	public static final Sound stairs = new Sound("/stairs.wav");
	public static final Sound hit = new Sound("/hit.wav");
	public static final Sound pickup = new Sound("/item.wav");
	public static final Sound select = new Sound("/select.wav");
	public static final Sound buy = new Sound("/buy.wav");
	public static final Sound die = new Sound("/die.wav");
	public static final Sound criticalHit = new Sound("/critical.wav");
	public static final Sound parry = new Sound("/parry.wav");
	
	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
