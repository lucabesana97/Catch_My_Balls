package gameobject;

import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;

public class Crawler_Clock extends Enemy{
	public int bottom_tile, left_tile, top_tile, right_tile;
	public Crawler_Clock(int tile_x, int tile_y, Direction dir) {
		super(tile_x, tile_y, dir);
		speed = 10;
		size = Game_Frame.TILE_SIZE - 10;
		y += 10;
	}

	public void move(double diffSeconds, TileManager tileM) {
		setTile();
		bottom_tile = (int) (y + 1 + size + speed * diffSeconds)/Game_Frame.TILE_SIZE;
		right_tile = (int) (x + size + 10 + speed * diffSeconds)/Game_Frame.TILE_SIZE;
		left_tile = (int) (x - 1 - speed * diffSeconds)/Game_Frame.TILE_SIZE;
		top_tile = (int) (y - 1 - speed * diffSeconds)/Game_Frame.TILE_SIZE;
		
		switch(dir) {
		case DOWN:
			y += speed * diffSeconds;
			if(tileM.body[tile_x_1 - 1][tile_y_1].iD == -1 && tileM.body[tile_x_1 - 1][bottom_tile].iD == -1) {dir = Direction.LEFT;}
			else if(tileM.body[tile_x_1][bottom_tile].iD != -1){dir = Direction.RIGHT;}
			break;
		case LEFT:			
			x -= speed * diffSeconds;
			if(tileM.body[tile_x_2][tile_y_1 - 1].iD == -1 && tileM.body[left_tile][tile_y_1 - 1].iD == -1) {dir = Direction.UP;}
			else if(tileM.body[tile_x_1 - 1][tile_y_1].iD != -1){dir = Direction.DOWN;}
			break;
		case RIGHT:
			x += speed * diffSeconds;
			if(tileM.body[tile_x_1][tile_y_1 + 1].iD == -1 && tileM.body[right_tile][tile_y_1 + 1].iD == -1) {
				dir = Direction.DOWN;}
			else if(tileM.body[tile_x_1 + 1][tile_y_1].iD != -1){dir = Direction.UP;}
			break;
		case UP:
			y -= speed * diffSeconds;
			if(tileM.body[tile_x_1 + 1][tile_y_2].iD == -1 && tileM.body[tile_x_1 + 1][top_tile].iD == -1) {dir = Direction.RIGHT;}
			else if(tileM.body[tile_x_1][tile_y_1 + 1].iD != -1){dir = Direction.LEFT;}
			break;
		default:
			break;

		}
	}
}
