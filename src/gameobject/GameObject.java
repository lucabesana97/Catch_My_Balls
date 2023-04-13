package gameobject;

import java.awt.Graphics;

import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;
import objState.HorizontalState;
import objState.VerticalState;

public abstract class GameObject {
	public int health;
	public int damage;

	public double x, y;
	public int size = Game_Frame.TILE_SIZE;
	public int tile_x_1, tile_x_2, tile_y_1, tile_y_2;
	public double margin; 
	
	public double speed, vertical_speed;
	public double vertical_acceleration = 20;
	public Direction dir;
	public HorizontalState horizontalState;
	public VerticalState verticalState;

	public void draw(Graphics graphics) {
	}

	public double distance(GameObject go) {

		double x_comp = go.x + (go.size / 2) - (x + (size / 2));
		double y_comp = go.y + (go.size / 2) - (y + (size / 2));
		return Math.sqrt(((x_comp) * (x_comp)) + ((y_comp) * (y_comp)));

	}

	public void takeDmg(int dmg) {
		this.health -= dmg;
	}

	public void heal(int healAmount) {
		this.health += healAmount;
	}

	public void setTile() {
		tile_x_1 = (int) x / Game_Frame.TILE_SIZE;
		tile_y_1 = (int) y / Game_Frame.TILE_SIZE;
		tile_x_2 = (int) (x + size) / Game_Frame.TILE_SIZE;
		tile_y_2 = (int) (y + size) / Game_Frame.TILE_SIZE;
	}

	public void move(double diffSeconds, TileManager tileM) {
	}

	public boolean collision(GameObject go) {
		if (this.distance(go) <=  (size + go.size) / Math.sqrt(2) * (margin + go.margin) / 2) {
			return true;
		}
		return false;
	}
	
	public boolean mapCollision(double diffSeconds, TileManager tileM) {
		if (bottomCollision(diffSeconds, tileM) || verticalCollision(diffSeconds, tileM) || sideCollision(diffSeconds, tileM)) {
			return true;
		}
		return false;
	}

	public boolean bottomCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) ((x + 4) / Game_Frame.TILE_SIZE);
		int map_x2 = (int) ((x + size - 4) / Game_Frame.TILE_SIZE);
		int map_y = (int) (y + size + vertical_speed * diffSeconds + 1) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x1][map_y].iD != -1 || tileM.body[map_x2][map_y].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean sideCollision(double diffSeconds, TileManager tileM) {
		int map_y1 = (int) (y / Game_Frame.TILE_SIZE);
		int map_y2 = (int) ((y + size) / Game_Frame.TILE_SIZE);
		int map_x = (dir == Direction.RIGHT) ? (int) (x + size + speed * diffSeconds) / Game_Frame.TILE_SIZE
				: (int) (x - speed * diffSeconds) / Game_Frame.TILE_SIZE;
		if (rightCollision(tileM, map_y1, map_y2, map_x) || leftCollision(tileM, map_y1, map_y2, map_x))
			return true;
		return false;
	}

	public boolean rightCollision(TileManager tileM, int map_y1, int map_y2, int map_x) {
		if (tileM.body[map_x][map_y1].iD != -1 || tileM.body[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean leftCollision(TileManager tileM, int map_y1, int map_y2, int map_x) {
		if (tileM.body[map_x][map_y1].iD != -1 || tileM.body[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean verticalCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) ((x + 4) / Game_Frame.TILE_SIZE);
		int map_x2 = (int) ((x + size - 4) / Game_Frame.TILE_SIZE);
		int map_y = (int) (y + vertical_speed * diffSeconds + 1) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x1][map_y].iD != -1 || tileM.body[map_x2][map_y].iD != -1) {
			return true;
		}
		return false;
	}
	
	public boolean rightCollision(double diffSeconds, TileManager tileM) {
		int map_y1 = (int) ((y + 4) / Game_Frame.TILE_SIZE);
		int map_y2 = (int) ((y + size - 4) / Game_Frame.TILE_SIZE);
		int map_x = (int) (x + size + speed * diffSeconds) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x][map_y1].iD != -1 || tileM.body[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean leftCollision(double diffSeconds, TileManager tileM) {
		int map_y1 = (int) ((y + 4) / Game_Frame.TILE_SIZE);
		int map_y2 = (int) ((y + size - 4) / Game_Frame.TILE_SIZE);
		int map_x = (int) (x - speed * diffSeconds) / Game_Frame.TILE_SIZE;
		if (tileM.body[map_x][map_y1].iD != -1 || tileM.body[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}
}
