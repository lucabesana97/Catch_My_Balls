package hud;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import gui.Game_Frame;

public class Victory extends Dialogue{


	//private BufferedImage godNO;

	public Victory() {
		str_1 = "You won!!";
		str_2 = "You absolute legend!";
		str_3 = "Press enter to try again";
		/*try {
		godNO = ImageIO.read(getClass().getResourceAsStream("/player/god_no.png"));
	}catch(Exception e) {}
	*/
}
/*
public void draw(Graphics g) {
	g.setColor(Color.black);
	g.fillRect(0, 0,  Game_Frame.WIDTH, Game_Frame.HEIGHT);
	g.drawImage(godNO, 10 * Game_Frame.TILE_SIZE, 0, Game_Frame.WIDTH - 20 * Game_Frame.TILE_SIZE, Game_Frame.HEIGHT, null);
}*/
}
