package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import fieldobject.TileManager;
import gui.Game_Frame;

public class Obstacle extends GameObject {
	double alfa;
	boolean goingUp = false;
	int tileDist;
	public int start_x, start_y;

	public Obstacle(int tile_x, int tile_y, int tileDist) {
		this.tile_x_1 = tile_x;
		start_x = tile_x;
		this.tile_y_1 = tile_y;
		start_y = tile_y;
		y = this.tile_y_1 * Game_Frame.TILE_SIZE;
		x = this.tile_x_1 * Game_Frame.TILE_SIZE;
		speed = 10;
		alfa = 0;
		this.tileDist = tileDist;
		size = Game_Frame.TILE_SIZE;
		damage = 25;
		margin = 0.6;
		health = 10000;
	}

	public void move(double diffSeconds, TileManager tileM) {
		if (y + speed * diffSeconds > (start_y + tileDist) * Game_Frame.TILE_SIZE) {
			goingUp = true;
		} else if (y - speed * diffSeconds < start_y * Game_Frame.TILE_SIZE) {
			goingUp = false;
		}
		y += speed * diffSeconds * ((goingUp) ? -1 : 1);
		setTile();
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRoundRect((int) x, (int) y, size, size, 4, 4);
	}
}
