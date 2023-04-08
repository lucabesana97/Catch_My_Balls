package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;

public class Enemy extends GameObject {

	public Enemy(int tile_x, int tile_y, Direction dir) {
		this.tile_x = tile_x;
		this.tile_y = tile_y;
		x = this.tile_x * Game_Frame.TILE_SIZE;
		y = this.tile_y * Game_Frame.TILE_SIZE - 1;
		this.dir = dir;
		speed = 20;
		damage = 40;
		margin = 0.5;
	}

	public void move(double diffSeconds, TileManager tileM) {
		if (!bottomCollision(diffSeconds, tileM) || sideCollision(diffSeconds, tileM)) {
			dir = (dir == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
		}
		x += ((dir == Direction.LEFT) ? -1 : 1) * diffSeconds * speed;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.green);
		graphics.fillRoundRect((int) x, (int) y, size, size, 4, 4);
	}
}
