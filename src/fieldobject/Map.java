package fieldobject;

public class Map {
	public int iD;
	public int rotation;
	public int collision;
	
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
