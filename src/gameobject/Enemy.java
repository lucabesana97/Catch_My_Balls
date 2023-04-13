package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;

public class Enemy extends GameObject {

	public Enemy(int tile_x, int tile_y, Direction dir) {
		this.tile_x_1 = tile_x;
		this.tile_y_1 = tile_y;
		x = this.tile_x_1 * Game_Frame.TILE_SIZE + (Game_Frame.TILE_SIZE - size)/2 - 1;
		y = this.tile_y_1 * Game_Frame.TILE_SIZE + (Game_Frame.TILE_SIZE - size)/2 - 1;
		this.dir = dir;
		speed = 15;
		damage = 40;
		margin = 0.5;
		health = 100;
	}
	
	public Enemy() {}
	
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
	
	public boolean bottomCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) ((x + 4) / Game_Frame.TILE_SIZE) + ((dir == Direction.RIGHT)? 1 : -1);
		int map_x2 = (int) ((x + size - 4) / Game_Frame.TILE_SIZE) + ((dir == Direction.RIGHT)? 1 : -1);
		int map_y = (int) (y + size + vertical_speed * diffSeconds + 1) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x1][map_y].iD != -1 || tileM.body[map_x2][map_y].iD != -1) {
			return true;
		}
		return false;
	}
}
