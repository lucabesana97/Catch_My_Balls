package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import fieldobject.Map;
import fieldobject.TileManager;
import gui.Game_Frame;
import objState.Direction;
import objState.HorizontalState;
import objState.VerticalState;

public class Player extends GameObject {
	private final static int JUMP_POWER = -80;
	private final static double DMG_MAX_TIMER = 3;
	private final static double NORMAL_SPEED = 50;
	private final static double SPRINT_SPEED = 70;
	private final static double DAMAGE_SPEED = 20;

	private double dmgCounter = 0;

	public Player() {
		x = (Game_Frame.WIDTH - Game_Frame.TILE_SIZE) / 2;
		y = (Game_Frame.HEIGHT - Game_Frame.TILE_SIZE) / 2;
		speed = 50;
		size = Game_Frame.TILE_SIZE;
		dir = Direction.RIGHT;
		horizontalState = HorizontalState.STILL;
		verticalState = VerticalState.FALLING;
		health = 100;
	}

	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);

		graphics.fillRoundRect((int) x, (int) y, size, size, 15, 15);
	}

	public void move(double diffSeconds, TileManager tileM) {
		walk(diffSeconds, tileM);
		jump();
		fall(diffSeconds, tileM);
		setTile();
	}

	private void walk(double diffSeconds, TileManager tileM) {
		if (horizontalState != HorizontalState.STILL) {
			if (!sideCollision(diffSeconds, tileM)) {
				switch (dir) {
				case RIGHT:
					x += speed * diffSeconds;
					break;
				case LEFT:
					x -= speed * diffSeconds;
				}
			}
			if (horizontalState == HorizontalState.DAMAGED && dmgCounter < DMG_MAX_TIMER) {
				dmgCounter += diffSeconds;
			} else if (horizontalState == HorizontalState.DAMAGED && dmgCounter >= DMG_MAX_TIMER) {
				horizontalState = HorizontalState.STILL;
				speed = NORMAL_SPEED;
				dmgCounter = 0;
			}
		}
	}

	private void jump() {
		if (verticalState == VerticalState.JUMPING) {
			vertical_speed = JUMP_POWER;
			verticalState = VerticalState.FALLING;
		}
	}

	private void fall(double diffSeconds, TileManager tileM) {

		if (bottomCollision(diffSeconds, tileM)) {
			vertical_speed = 0;
			verticalState = VerticalState.STILL;
			y += 10;
			y -= y % Game_Frame.TILE_SIZE + 1;
		} else if (verticalCollision(diffSeconds, tileM)) {
			vertical_speed = 0.1;
			verticalState = VerticalState.FALLING;
		} else {
			verticalState = VerticalState.FALLING;
		}
		if (verticalState == VerticalState.FALLING) {
			y += vertical_speed * diffSeconds;
			if (vertical_speed < -JUMP_POWER) {
				vertical_speed += vertical_acceleration * diffSeconds;
			}
		}
	}

	public void takeDmg(int dmg) {
		super.takeDmg(dmg);
		horizontalState = HorizontalState.DAMAGED;
		vertical_speed = -30;
		speed = DAMAGE_SPEED;
		if (dir == Direction.RIGHT) {
			dir = Direction.LEFT;
		} else {
			dir = Direction.RIGHT;
		}
		dmgCounter = 0;
	}
}
