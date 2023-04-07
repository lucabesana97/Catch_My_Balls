package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import fieldobject.Map;
import fieldobject.TileManager;
import gameobject.GameObject;
import hud.HudObject;

public class Panel extends JPanel {

	public static final int WIDTH = Game_Frame.WIDTH;
	public static final int HEIGHT = Game_Frame.HEIGHT - 28;

	public Graphics graphics;
	final private BufferedImage imageBuffer;

	public Panel() {

		this.setSize(WIDTH, HEIGHT + 28);
		this.setBackground(Color.darkGray);

		GraphicsConfiguration graphicsConf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
		graphics = imageBuffer.getGraphics();
	}

	public void clear() {
		graphics.setColor(Color.darkGray);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
	}

	public void redraw() {
		this.getGraphics().drawImage(imageBuffer, 0, 0, this);
	}

	public void draw(GameObject object) {
		object.draw(graphics);
	}

	public void draw(HudObject object) {
		object.draw(graphics);
	}

    public void draw(TileManager tileM) { tileM.draw(graphics); }
}
