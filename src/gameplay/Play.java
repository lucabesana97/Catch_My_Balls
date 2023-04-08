package gameplay;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fieldobject.TileManager;
import gameobject.Enemy;
import gameobject.GameObject;
import gameobject.Objective;
import gameobject.Obstacle;
import gameobject.Player;
import gui.Game_Frame;
import gui.Panel;
import hud.HudObject;
import hud.Loss;
import hud.Victory;
import io.KeyHandler;
import io.Keys;
import objState.Direction;
import objState.HorizontalState;
import objState.VerticalState;
import sounds.Sound;

public class Play {
	final Panel panel;
	final KeyHandler keyHandler;
	Sound sound = new Sound();
	
	private BufferedImage backBuffer;
	HudObject[] hudBuffer;
	List<GameObject> enemies;
	Player player;
	TileManager tileM;
	List<Objective> objectives;
	Objective remover = new Objective();
	GameState gState, lastState;

	public Play(Panel panel, KeyHandler keyHandler) {
		this.panel = (Panel) panel;
		this.keyHandler = keyHandler;
	}

	public void init() {
		backBuffer = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		hudBuffer = new HudObject[1];
		player = new Player();
		
		tileM = new TileManager();
		
		objectives = new ArrayList<>();
		enemies = new ArrayList<>();
		gState = GameState.RUNNING;
		lastState = gState;

		objectives.add(new Objective(10, 15));
		objectives.add(new Objective(1, 2));

		enemies.add(new Obstacle(11, 13, 6));
		enemies.add(new Obstacle(29, 1, 6));
		enemies.add(new Enemy(9, Game_Frame.TILE_NUM_Y - 2, Direction.LEFT));
	}

	public void run() {

		long lastTick = System.currentTimeMillis();

		while (true) {
			long currentTick = System.currentTimeMillis();
			double diffSeconds = (currentTick - lastTick) / 60.0;
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
		for (Objective go : objectives) {
			if (player.distance(go) < Game_Frame.TILE_SIZE / 2) {
				remover = go;
			}
		}
		if (remover != null) {
			objectives.remove(remover);
		}

		// If you collected all the objectives, you win
		if (objectives.isEmpty()) {
			victory();
		}

		// Move all the enemies and check for collision with the player
		for (GameObject go : enemies) {
			go.move(diffSeconds, tileM);
			
			if (go.collision(player) && player.horizontalState != HorizontalState.DAMAGED) {
				player.takeDmg(go.damage);
				// if your health is 0 or lower, game over
				if (player.health <= 0) {
					loss();
				}
			}
			// System.out.println(player.health + "\t" + player.horizontalState);
			// System.out.println(gState);
		}
	}

	private void loss() {
		gState = GameState.LOSS;
		hudBuffer[0] = new Loss();
		playSoundEffect(1);
	}

	private void victory() {
		gState = GameState.VICTORY;
		hudBuffer[0] = new Victory();
		playSoundEffect(0);
	}

	private void drawElements() {
		Graphics g = (Graphics) backBuffer.getGraphics();
		panel.draw(player);
		panel.draw(tileM);
		for (GameObject go : objectives) {
			panel.draw(go);
		}
		for (GameObject go : enemies) {
			panel.draw(go);
		}
		if (gState != GameState.RUNNING && hudBuffer[0] != null) {
			panel.draw(hudBuffer[0]);
		}
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
				case SPACE:
					if (player.verticalState == VerticalState.STILL) {
						player.verticalState = VerticalState.JUMPING;
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
	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSoundEffect(int i) {
		sound.setFile(i);
		sound.play();
	}
}
