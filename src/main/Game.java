package main;
import gameplay.Play;
import gui.*;
import io.KeyHandler;

public class Game {
    final private Frame frame;
    final private KeyHandler keyHandler;

    private Play play;
    private Panel panel;
    
    public Game() {
    	frame = new Frame();
		keyHandler = new KeyHandler();
		frame.addKeyListener(keyHandler);
    }
    
    public void init() {
        panel = new Panel();
        frame.setPanel(panel);
    }
    
    public void start() {
        panel = new Panel();
        play = new Play(panel, keyHandler);
        frame.setPanel(panel);
        
        play.init();
        play.run();
    }
}
