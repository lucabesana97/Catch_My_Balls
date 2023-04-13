package gameplay;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.Set;

import fieldobject.TileManager;
import gameobject.Bullet;
import gameobject.Crawler_Clock;
import gameobject.Enemy;
import gameobject.GameObject;
import gameobject.Medikit;
import gameobject.Objective;
import gameobject.Obstacle;
import gameobject.Player;
import gui.Game_Frame;
import gui.Panel;
import hud.Hud;
import hud.HudObject;
import hud.Loss;
import hud.Victory;
import io.KeyHandler;
import io.Keys;
import objState.Direction;
import objState.HorizontalState;
import objState.ShootingState;
import objState.VerticalState;
import sounds.Sound;

public class Play {

	int pos_x, pos_y;
	
	final Panel panel;
	final KeyHandler keyHandler;
	Sound soundtrack = new Sound();
	Sound effects = new Sound();

	TileManager tileM;
	private BufferedImage backBuffer;
	HudObject[] hudBuffer;

	List<GameObject> enemies;
	Player player;
	List<Objective> objectives;
	List<Bullet> bullets;
	List<Medikit> medikit;
	
	GameObject enemy_remover = new Enemy();

	GameState gState, lastState;
	
	public Play(Panel panel, KeyHandler keyHandler) {
		this.panel = (Panel) panel;
		this.keyHandler = keyHandler;
	}

	public void init() {
		backBuffer = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);

		hudBuffer = new HudObject[2];
		player = new Player();

		tileM = new TileManager();

		objectives = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		medikit = new ArrayList<>();
		
		gState = GameState.RUNNING;
		lastState = gState;

		hudBuffer[1] = new Hud(player);
		
		objectives.add(new Objective(10, 15));
		objectives.add(new Objective(1, 2));
		
		do {
			pos_x = (int)(Math.random() * Game_Frame.TILE_NUM_X);
			pos_y = (int)(Math.random() * Game_Frame.TILE_NUM_Y);
		}while(tileM.body[pos_x][pos_y].iD != -1);
		medikit.add(new Medikit(pos_x, pos_y));
		
		enemies.add(new Obstacle(11, 13, 6));
		enemies.add(new Obstacle(29, 1, 6));
		enemies.add(new Enemy(9, Game_Frame.TILE_NUM_Y - 2, Direction.LEFT));
		enemies.add(new Crawler_Clock(4, 4, Direction.RIGHT));
		
		soundtrack.stopMusic();
		soundtrack.playMusic(2);
		soundtrack.changeVolume(-20);
	}

	public void run() {

		long lastTick = System.currentTimeMillis();

		while (true) {
			long currentTick = System.currentTimeMillis();
			double diffSeconds = (currentTick - lastTick) / 100.0;
			lastTick = currentTick;

			panel.clear();

			try {
				handleUserInput();
			} catch (Exception e) {
				System.out.println(e.getCause());
			}

			drawElements();

			if (gState == GameState.RUNNING) {
				update(diffSeconds);
			}


			panel.redraw();
			System.out.flush();

			panel.getGraphics().drawImage(backBuffer, 0, 0, null);
		}
	}

	private void update(double diffSeconds) {
		// Move the player
		player.move(diffSeconds, tileM);

		// Check every objective and if the player is touching one, set the remover as
		// it and remove it after the for loop
		ListIterator<Objective> iterator_3 = objectives.listIterator();
		while (iterator_3.hasNext()) { 
			Objective go = iterator_3.next();
			if (player.distance(go) < Game_Frame.TILE_SIZE / 2) {
				iterator_3.remove();
				do {
					pos_x = (int)(Math.random() * Game_Frame.TILE_NUM_X);
					pos_y = (int)(Math.random() * Game_Frame.TILE_NUM_Y);
				}while(tileM.body[pos_x][pos_y].iD != -1);
				//iterator_3.add(new Objective(pos_x, pos_y));
				effects.playSoundEffect(3);
			}
		}

		// If you collected all the objectives, you win
		if (objectives.isEmpty()) {
			victory();
		}
		
		//Check medikits
		ListIterator<Medikit> iterator_4 = medikit.listIterator();
		while (iterator_4.hasNext()) { 
			Medikit med = iterator_4.next();
			if (player.collision(med)) {
				iterator_4.remove();
				player.heal(med.damage);
				do {
					pos_x = (int)(Math.random() * Game_Frame.TILE_NUM_X);
					pos_y = (int)(Math.random() * Game_Frame.TILE_NUM_Y);
				}while(tileM.body[pos_x][pos_y].iD != -1);
				iterator_4.add(new Medikit(pos_x, pos_y));
			}
		}
		
		//Move all the bullets
		Iterator<Bullet> iterator = bullets.iterator();
		while (iterator.hasNext()) { 
			Bullet bu = iterator.next();
			bu.move(diffSeconds);
			for (GameObject go : enemies) {
				if(go.collision(bu)) {
					if(go.getClass() != Obstacle.class) {
						go.takeDmg(player.weapon.damage);
						if((go.health) <= 0) {
							enemy_remover = go;
						}
					}
					iterator.remove();
				}
			}
			if(((bu != null && bu.mapCollision(diffSeconds, tileM)) || Math.abs(bu.x-bu.startingX) > player.weapon.travelDistance) && iterator != null) {
				iterator.remove();
			}
		}

		// Move all the enemies and check for collision with the player
		Iterator<GameObject> iterator_2 = enemies.iterator();
		while (iterator_2.hasNext()) { 
			GameObject go = iterator_2.next();
			if(go.health <= 0) {
				iterator_2.remove();
				effects.playSoundEffect(5);
			}
			else {
				go.move(diffSeconds, tileM);

				if (go.collision(player) && player.horizontalState != HorizontalState.DAMAGED) {
					player.takeDmg(go.damage);
					// if your health is 0 or lower, game over
					if (player.health <= 0) {
						loss();
					}
				}
			}
		}
	}

	private void loss() {
		gState = GameState.LOSS;
		hudBuffer[0] = new Loss();
		//stopMusic();
		//sound.changeVolume(0);
		effects.playSoundEffect(1);
	}

	private void victory() {
		gState = GameState.VICTORY;
		hudBuffer[0] = new Victory();
		//stopMusic();
		//sound.changeVolume(0);
		effects.playSoundEffect(0);
	}

	private void drawElements() {
		Graphics g = (Graphics) backBuffer.getGraphics();
		panel.draw(tileM);
		panel.draw(player);
		for (GameObject go : objectives) {
			panel.draw(go);
		}
		for (GameObject go : medikit) {
			panel.draw(go);
		}
		for (GameObject go : enemies) {
			panel.draw(go);
		}
		if(bullets != null) {
			for (GameObject go : bullets) {
				panel.draw(go);
			}
		}
		if (gState != GameState.RUNNING && hudBuffer[0] != null) {
			panel.draw(hudBuffer[0]);
		}
		
		panel.draw(hudBuffer[1]);
		g.dispose();
	}

	private void handleUserInput() {

		final Set<Keys> pressedKeys = keyHandler.getKeys();
		if (player.horizontalState != HorizontalState.DAMAGED || gState == GameState.LOSS) {
			boolean horStill = true;
			for (Keys keyCode : pressedKeys) {
				switch (keyCode) {
				case PAUSE:
					if (gState == GameState.LOSS || gState == GameState.VICTORY) {
						init();
					}
					break;
				case LEFT:
					player.dir = Direction.LEFT;
					player.horizontalState = HorizontalState.MOVING;
					horStill = false;
					break;
				case RIGHT:
					player.dir = Direction.RIGHT;
					player.horizontalState = HorizontalState.MOVING;
					horStill = false;
					break;
				case JUMP:
					if (player.verticalState == VerticalState.STILL) {
						player.verticalState = VerticalState.JUMPING;
					}
					break;
				case SHOOT:
					//System.out.println("BANG!\t" + (player.shootState == ShootingState.READY));
					if(player.shootState == ShootingState.READY) {
						bullets.add(new Bullet(player));
						effects.playSoundEffect(4);
						player.shootState = ShootingState.RELOADING;
					}
					break;
				default:
					break;
				}
				// System.out.println(player.horizontalState + "\t" + player.verticalState +
				// "\t" + player.vertical_speed);
			}
			// System.out.println();
			if (horStill) {
				player.horizontalState = HorizontalState.STILL;
			}
		}
	}
}
