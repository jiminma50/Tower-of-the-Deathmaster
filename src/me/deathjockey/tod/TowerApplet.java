package me.deathjockey.tod;

import java.applet.Applet;
import java.awt.BorderLayout;

public class TowerApplet extends Applet {

	private static final long serialVersionUID = 1L;
	private TowerComponent game;
	
	public TowerApplet() {
		game = new TowerComponent();
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}
	
	public void start() {
		game.start();
	}
	
	public void stop() {
		game.stop();
	}
}
