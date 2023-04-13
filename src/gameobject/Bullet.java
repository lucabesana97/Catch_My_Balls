package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import fieldobject.TileManager;
import objState.Direction;

public class Bullet extends GameObject{
	public double startingX;
	
	public Bullet(Player player) {
		//damage = 20;
		x = player.x + ((player.dir == Direction.RIGHT)? player.size : 0);
		y = player.y + player.size * 1/4;
		startingX = x;
		dir = player.dir;
		speed = 100;
		size = 10;
		margin = 1.5;
	}
	
	public void move(double diffSeconds) {
		setTile();
		x += speed * diffSeconds * ((dir == Direction.RIGHT)? 1 : -1);
	}
	
	public boolean mapCollision(double diffSeconds, TileManager tileM) {
		if(tileM.body[tile_x_1][tile_y_1].iD != -1) {return true;}
		return false;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval((int)x, (int)y, size, size);
	}
}
