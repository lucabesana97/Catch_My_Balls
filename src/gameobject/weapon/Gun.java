package gameobject.weapon;

import gui.Game_Frame;

public class Gun extends Weapon{
	public Gun() {
		maxTimer = 4;
		damage = 20;
		magazine = 6;
		travelDistance = 10 * Game_Frame.TILE_SIZE;
		reloadTime = 12;
		soundLocation = "/audio/Bang.wav";
	}
}
