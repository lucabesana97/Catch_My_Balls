package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import gui.Game_Frame;

public class Medikit extends GameObject{
	int[] crossY = new int[12];
	int[] crossX = new int[12];
	
	public Medikit(int tile_x, int tile_y) {
		size = 16;
		int offset = (Game_Frame.TILE_SIZE - size) / 2;
		x = Game_Frame.TILE_SIZE * tile_x + offset;
		y = Game_Frame.TILE_SIZE * tile_y + offset;
		margin = 1.1;
		damage = 25;
		
		crossY[0] = (int)(y + size * 1/5);
		crossY[1] = (int)(y + size * 1/5);
		crossY[2] = (int)(y + size * 2/5);
		crossY[3] = (int)(y + size * 2/5);
		crossY[4] = (int)(y + size * 3/5);
		crossY[5] = (int)(y + size * 3/5);
		crossY[6] = (int)(y + size * 4/5);
		crossY[7] = (int)(y + size * 4/5);
		crossY[8] = (int)(y + size * 3/5);
		crossY[9] = (int)(y + size * 3/5);
		crossY[10] = (int)(y + size * 2/5);
		crossY[11] = (int)(y + size * 2/5);
		
		crossX[0] = (int)(x + size * 2/5);
		crossX[1] = (int)(x + size * 3/5);
		crossX[2] = (int)(x + size * 3/5);
		crossX[3] = (int)(x + size * 4/5);
		crossX[4] = (int)(x + size * 4/5);
		crossX[5] = (int)(x + size * 3/5);
		crossX[6] = (int)(x + size * 3/5);
		crossX[7] = (int)(x + size * 2/5);
		crossX[8] = (int)(x + size * 2/5);
		crossX[9] = (int)(x + size * 1/5);
		crossX[10] = (int)(x + size * 1/5);
		crossX[11] = (int)(x + size * 2/5);
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRoundRect((int) x, (int) y, size, size, 4, 4);
		graphics.setColor(new Color(76, 99, 50));
		graphics.fillRoundRect((int) x, (int) y, size - 4, size - 4, 4, 4);
		graphics.setColor(Color.RED);
		graphics.drawPolygon(crossX,crossY,12);
	}
}
