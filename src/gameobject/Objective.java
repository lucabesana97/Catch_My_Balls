package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import gui.Game_Frame;

public class Objective extends GameObject {

	public Objective(int tile_x, int tile_y) {
		size = 16;
		int offset = (Game_Frame.TILE_SIZE - size) / 2;
		x = Game_Frame.TILE_SIZE * tile_x + offset;
		y = Game_Frame.TILE_SIZE * tile_y + offset;
		margin = 1.1;
	}

	public Objective() {
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.MAGENTA);
		graphics.fillOval((int) x, (int) y, size, size);
		graphics.setColor(Color.pink);
		graphics.fillOval((int) x, (int) y, size - 4, size - 4);
	}

}
