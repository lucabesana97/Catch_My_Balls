package gameplay;

import java.util.Set;
import java.util.HashSet;
import java.util.Set;

import gameobject.Direction;
import gameobject.Player;
import gameobject.State;
import gui.Panel;
import io.KeyHandler;
import io.Keys;

public class Play {
	final Panel panel;
	final KeyHandler keyHandler;

	Player player;

	public Play(Panel panel, KeyHandler keyHandler) {
		this.panel = (Panel) panel;
		this.keyHandler = keyHandler;
	}

	public void init() {
		player = new Player();
	}

	public void run() {

		long lastTick = System.currentTimeMillis();

		while (true) {

			long currentTick = System.currentTimeMillis();
			double diffSeconds = (currentTick - lastTick) / 1000.0;
			lastTick = currentTick;

			handleUserInput();
			update(diffSeconds);
			// get user input
			// do something with user input (update)
			panel.clear();
			drawElements();
			panel.redraw();
			System.out.flush();

		}
	}
	private void update(double diffSeconds) {

		player.move(diffSeconds);
	}

	private void drawElements() {
		panel.draw(player);
	}

	private void handleUserInput() {
		final Set<Keys> pressedKeys = keyHandler.getKeys();
		if(pressedKeys.size() == 0) {
			player.setState(State.STILL);
		}
		else {
			for (Keys keyCode : pressedKeys) {
				switch (keyCode) {
				case NONE:
					player.setState(State.STILL);
					break;
				case LEFT:
					player.setDirectionMovement(Direction.LEFT);
					player.setState(State.MOVING);
					break;
				case RIGHT:
					player.setDirectionMovement(Direction.RIGHT);
					player.setState(State.MOVING);
					break;
				case SPACE:
					player.setState(State.JUMPING);
					break;
				}
			}
		}
	}
}
