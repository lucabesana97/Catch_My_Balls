package io;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyHandler implements KeyListener {

	private Set<Keys> pressedKeys = new HashSet<>();


	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
        pressedKeys.add(toKey(keyCode));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
        pressedKeys.remove(toKey(keyCode));
	}
	
	private Keys toKey(int n) {
		Keys returner = null;
		
		switch (n) {
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP:
            returner = Keys.UP;
            break;
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN:
        	returner = Keys.DOWN;
            break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT:
        	returner = Keys.LEFT;
            break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT:
        	returner = Keys.RIGHT;
            break;
        case KeyEvent.VK_SPACE:
        	returner = Keys.SPACE;
            break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_P:
        	returner = Keys.PAUSE;
            break;
    }

    return returner;
	}
	
	public Set<Keys> getKeys(){
		return pressedKeys;
	}
}
