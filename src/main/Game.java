package main;

import gameplay.Play;
import gui.Game_Frame;
import gui.Panel;
import io.KeyHandler;

public class Game {
	final private Game_Frame gameFrame;
	final private KeyHandler keyHandler;

	private Play play;
	private Panel panel;

	public Game() {
		gameFrame = new Game_Frame();
		keyHandler = new KeyHandler();
		gameFrame.addKeyListener(keyHandler);
	}

	public void init() {
		panel = new Panel();
		gameFrame.setPanel(panel);
	}

	public void start() {
		panel = new Panel();
		play = new Play(panel, keyHandler);
		gameFrame.setPanel(panel);

		play.init();
		play.run();
	}
}
