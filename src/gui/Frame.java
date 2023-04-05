package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Frame extends JFrame{
	public static final int TILE_SIZE = 32;
	public static final int TILE_NUM_X = 40;
	public static final int TILE_NUM_Y = 24;
	public static final int WIDTH = TILE_SIZE * TILE_NUM_X;
	public static final int HEIGHT = TILE_SIZE * TILE_NUM_Y;
	
	private Panel panel;
	
	public Frame() {
		setTitle("Catch my balls");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setFocusable(true);
		setResizable(false);
		setVisible(true);
	}
	
    public void setPanel(Panel panel) {
        if (this.panel != null) {
            remove(this.panel);
        }
        this.panel = panel;
        this.add(panel, BorderLayout.SOUTH);
    }
}
