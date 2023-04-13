package hud;

import java.awt.Color;
import java.awt.Graphics;

import gameobject.Player;
import gui.Game_Frame;

public class Hud extends HudObject{
	
	Color color_1 = Color.black;
	Color color_2 = new Color(89, 86, 77);
	Player player;
	
	public Hud(Player player) {
		this.player = player;
	}
	
	public void draw(Graphics g){
		g.setColor(color_1);
		g.fillRoundRect(Game_Frame.TILE_SIZE, 8, Game_Frame.TILE_SIZE * 6, 24, 6, 12);
		g.setColor(color_2);
		g.fillRoundRect(Game_Frame.TILE_SIZE + 2, 10, Game_Frame.TILE_SIZE * 6 - 4, 20, 6, 12);
		g.setColor(Color.red);
		g.fillRoundRect(Game_Frame.TILE_SIZE + 4, 12,(int)((Game_Frame.TILE_SIZE * 6 - 8) * player.health / Player.MAX_HEALTH), 16, 6, 12);
		}
}
