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
	public int tile_x, tile_y;

	public double speed, vertical_speed;
	public double vertical_acceleration = 20;
	public Direction dir;
	public HorizontalState horizontalState;
	public VerticalState verticalState;

	public void draw(Graphics graphics) {
	}

	public double distance(GameObject go) {
		double ret;
		double x_comp = go.x + go.size / 2 - x - size / 2;
		double y_comp = go.y + go.size / 2 - y - size / 2;
		ret = Math.sqrt((x_comp) * (x_comp) + (y_comp) * (y_comp));
		return ret;
	}

	public void takeDmg(int dmg) {
		this.health -= dmg;
	}

	public void heal(int healAmount) {
		this.health += healAmount;
	}

	public void setTile() {
		tile_x = (int) x / Game_Frame.TILE_SIZE;
		tile_y = (int) y / Game_Frame.TILE_SIZE;
	}

	public void move(double diffSeconds, TileManager tileM) {
	}

	public boolean collision(GameObject player) {
		if (this.distance(player) <= size / 2 + player.size / 2) {
			return true;
		}
		return false;
	}

	public boolean bottomCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) (x / Game_Frame.TILE_SIZE);
		int map_x2 = (int) ((x + size) / Game_Frame.TILE_SIZE);
		int map_y = (int) (y + size + vertical_speed * diffSeconds + 1) / Game_Frame.TILE_SIZE;
		if (tileM.mapTileNum[map_x1][map_y].iD != -1 || tileM.mapTileNum[map_x2][map_y].iD != 1) {
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
		if (tileM.mapTileNum[map_x][map_y1].iD != -1 || tileM.mapTileNum[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean leftCollision(TileManager tileM, int map_y1, int map_y2, int map_x) {
		if (tileM.mapTileNum[map_x][map_y1].iD != 1 || tileM.mapTileNum[map_x][map_y2].iD != -1) {
			return true;
		}
		return false;
	}

	public boolean verticalCollision(double diffSeconds, TileManager tileM) {
		int map_x1 = (int) ((x + 2) / Game_Frame.TILE_SIZE);
		int map_x2 = (int) ((x + size - 2) / Game_Frame.TILE_SIZE);
		int map_y = (int) (y + size + vertical_speed * diffSeconds + 1) / Game_Frame.TILE_SIZE;
		if (tileM.mapTileNum[map_x1][map_y - 1].iD != 1 || tileM.mapTileNum[map_x2][map_y - 1].iD != 1) {
			return true;
		}
		return false;
	}
}
