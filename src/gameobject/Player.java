package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import fieldobject.TileManager;
import gameobject.weapon.Weapon;
import gameobject.weapon.Gun;
import gameobject.weapon.SMG;
import gui.Game_Frame;
import objState.Direction;
import objState.HorizontalState;
import objState.ShootingState;
import objState.VerticalState;

public class Player extends GameObject {
	private final static int JUMP_POWER = -80;
	public final static int MAX_HEALTH = 100;
	private final static double DMG_MAX_TIMER = 3;
	private final static double FRAME_TIMER = 1;
	private final static double NORMAL_SPEED = 50;
	//private final static double SPRINT_SPEED = 70;
	private final static double DAMAGE_SPEED = 20;

	private double dmgCounter = 0;
	public ShootingState shootState;
	private double shootCounter = 0;
	private BufferedImage[] imageLeft = new BufferedImage[6];
	private BufferedImage[] imageRight = new BufferedImage[6];
	private double animationTimer = 0;
	private int animationCounter;
	
	public Weapon weapon;
	
	public Player() {
		x = (Game_Frame.WIDTH - Game_Frame.TILE_SIZE) / 2;
		y = (Game_Frame.HEIGHT - Game_Frame.TILE_SIZE) / 2;
		speed = 50;
		size = Game_Frame.TILE_SIZE;
		dir = Direction.RIGHT;
		horizontalState = HorizontalState.STILL;
		verticalState = VerticalState.FALLING;
		shootState = ShootingState.READY;
		health = MAX_HEALTH;
		margin = 1;
		animationCounter = 0;
		weapon = new Gun();
		load();
	}

	public void draw(Graphics graphics) {
		if(verticalState == VerticalState.JUMPING) {
			graphics.drawImage(((dir == Direction.RIGHT)? imageRight[0] : imageLeft[0]), (int)x, (int)y, size, size, null);
		}
		else if(horizontalState == HorizontalState.STILL) {
			graphics.drawImage(((dir == Direction.RIGHT)? imageRight[2] : imageLeft[2]), (int)x, (int)y, size, size, null);
		} else {		
			switch(dir) {
			case RIGHT:
				graphics.drawImage(imageRight[animationCounter], (int)x, (int)y, size, size, null);
				break;
			case LEFT:
				graphics.drawImage(imageLeft[animationCounter], (int)x, (int)y, size, size, null);
				break;
			default:
				break;
			}
		}
	}

	public void move(double diffSeconds, TileManager tileM) {
		walk(diffSeconds, tileM);
		jump();
		fall(diffSeconds, tileM);
		setTile();
		setShooting(diffSeconds);
		animation(diffSeconds);
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
				default:
					break;
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

	private void setShooting(double diffSeconds) {
		if(shootState == ShootingState.RELOADING && shootCounter < weapon.maxTimer) {
			shootCounter += diffSeconds;
		}
		else if (shootState == ShootingState.RELOADING && shootCounter >= weapon.maxTimer) {
			shootCounter = 0;
			shootState = ShootingState.READY;
		}
	}

	private void load() {
		String str;
		for(int i = 1; i <= 6; i++) {
			str = "/player/left_char_" + Integer.toString(i) + ".png";
			try {
				imageLeft[i - 1] = ImageIO.read(getClass().getResourceAsStream(str));
			} catch(Exception e) {System.out.println("Couldn't load player image: " + i + "\tReason: " + e.getCause());}
		}
		for(int i = 1; i <= 6; i++) {
			str = "/player/right_char_" + Integer.toString(i) + ".png";
			try {
				imageRight[i - 1] = ImageIO.read(getClass().getResourceAsStream(str));
			} catch(Exception e) {System.out.println("Couldn't load player image: " + i + "\tReason: " + e.getCause());}
		}
	}

	public void animation(double diffSeconds) {
		animationTimer += diffSeconds;
		if(animationTimer >= FRAME_TIMER) {
			animationTimer = 0;
			animationCounter++;
			animationCounter %= 6;
		}
	}
	
	public void heal(int i) {
		if(health + i >= MAX_HEALTH) {
			health = MAX_HEALTH;
		} else {
			health += i;
		}
	}
}
