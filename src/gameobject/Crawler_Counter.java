package gameobject;

import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;

public class Crawler_Counter extends Enemy{

	public Crawler_Counter(int tile_x, int tile_y, Direction dir) {
		super(tile_x, tile_y, dir);
		speed = 10;
		size = Game_Frame.TILE_SIZE - 10;
		y += 4;
	}

	public void move(double diffSeconds, TileManager tileM) {
		setTile();
		System.out.println(dir);
		switch(dir) {
		case LEFT:
			x -= speed * diffSeconds;
			if(!bottomCollision(diffSeconds, tileM)) {
				dir = Direction.DOWN;
				x = tile_x_1 * Game_Frame.TILE_SIZE - size;
				}
			else if(leftCollision(diffSeconds, tileM)) {
				dir = Direction.UP;
				x = tile_x_1 * Game_Frame.TILE_SIZE - size;
				}
			break;
		case RIGHT:
			x += speed * diffSeconds;
			if(!verticalCollision(diffSeconds, tileM)) {
				dir = Direction.UP;
				x = tile_x_1 * Game_Frame.TILE_SIZE - size;
				}
			else if(rightCollision(diffSeconds, tileM)) {
				dir = Direction.DOWN;
				x = tile_x_1 * Game_Frame.TILE_SIZE - size;
				}
			break;
		case DOWN:			
			y += speed * diffSeconds;
			if(!rightCollision(diffSeconds, tileM)) {
				dir = Direction.RIGHT;
				y = tile_y_1 * Game_Frame.TILE_SIZE - size;
				}
			else if(bottomCollision(diffSeconds, tileM)) {
				dir = Direction.UP;
				y = tile_y_1 * Game_Frame.TILE_SIZE - size;
				}
			break;
		case UP:			
			y -= speed * diffSeconds;
			if(!leftCollision(diffSeconds, tileM)) {
				dir = Direction.LEFT;
				y = tile_y_1 * Game_Frame.TILE_SIZE - size;
				}
			else if(verticalCollision(diffSeconds, tileM)) {
				dir = Direction.RIGHT;
				y = tile_y_1 * Game_Frame.TILE_SIZE - size;
				}
			break;
		default:
			break;

		}
	}
	
	public boolean verticalCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) ((x + 4) / Game_Frame.TILE_SIZE);
		int map_x2 = (int) ((x + size - 4) / Game_Frame.TILE_SIZE);
		int map_y = (int) (y + 1) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x1][map_y - 1].iD != -1 || tileM.body[map_x2][map_y - 1].iD != -1) {
			return true;
		}
		return false;
	}
}
