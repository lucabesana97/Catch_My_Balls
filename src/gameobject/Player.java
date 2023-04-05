package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import gui.Frame;

public class Player extends GameObject{

	public Player() {
		x = (Frame.WIDTH - Frame.TILE_SIZE)/2;
		y = (Frame.HEIGHT - Frame.TILE_SIZE)/2;
		speed = 100;
		size = Frame.TILE_SIZE;
		dir = Direction.RIGHT;
		state = State.STILL;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);

		graphics.fillRoundRect((int)x, (int)y, size, size, 4, 4);
	}

	public void move(double diffSeconds) {
		if(state != State.STILL) {
			switch(dir) {
			case RIGHT:
				x += speed * diffSeconds;
				break;
			case LEFT:
				x -= speed * diffSeconds;
			}
		}
	}

	public void setDirectionMovement(Direction dir) {
		this.dir = dir;
	}

	public void setState(State state) {
		this.state = state;
	}
}
