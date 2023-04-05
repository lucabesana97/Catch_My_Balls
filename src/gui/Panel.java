package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import gameobject.GameObject;

public class Panel extends JPanel{

    public static final int WIDTH = gui.Frame.WIDTH;
    public static final int HEIGHT = Frame.HEIGHT - 28;

    public Graphics graphics;
    final private BufferedImage imageBuffer;

	public Panel() {

        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.darkGray);
        
		GraphicsConfiguration graphicsConf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
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
}
