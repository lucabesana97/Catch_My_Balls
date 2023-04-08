package fieldobject;

public class Map {
	public int iD;
	public int rotation;
	public int collision;

	/*
	public void draw(Graphics graphics) {
		graphics.setColor(Color.yellow);
		for (int i = 0; i < Game_Frame.TILE_NUM_X; i++) {
			for (int j = 0; j < Game_Frame.TILE_NUM_Y; j++) {
				if (tile[i][j] == 1) {
					graphics.fillRect(i * Game_Frame.TILE_SIZE, j * Game_Frame.TILE_SIZE, Game_Frame.TILE_SIZE,
							Game_Frame.TILE_SIZE);
				}
			}
		}
	}
	*/
	
	public void rotateCollision(int rotation) {
		int tmp = collision;
		switch (rotation) {
		case -3:
			tmp = collision >> 1;
			if (tmp << 1 != collision) {
				tmp += 0b1000;
			}
			collision = tmp;
			break;
		case -2:
			rotateCollision(-3);
			rotateCollision(-3);
			break;
		case 3:
			rotateCollision(-3);
			rotateCollision(-3);
			rotateCollision(-3);
			break;
		}
	}
}
