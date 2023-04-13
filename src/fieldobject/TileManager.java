package fieldobject;
import java.awt.*;
//import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import calc.Funcs;
import gui.Game_Frame;

public class TileManager {

	public Tile[] tile;
	public Map[][] body;
	public Map[][] base;
	public Map[][] detail;
	
	//private AffineTransform transform;

	public TileManager() {

		tile = new Tile[47];
		getTileImage();
		body = new Map[Game_Frame.TILE_NUM_X][Game_Frame.TILE_NUM_Y];
		base = new Map[Game_Frame.TILE_NUM_X][Game_Frame.TILE_NUM_Y];
		detail = new Map[Game_Frame.TILE_NUM_X][Game_Frame.TILE_NUM_Y];
		loadMap("/maps/plains_1_base.csv", base);
		loadMap("/maps/plains_1_body.csv", body);
		loadMap("/maps/plains_1_detail.csv", detail);
		//transform = new AffineTransform();
	}

	public void getTileImage(){
		try {
			for(int i = 0; i <= 46; i++) {
				String str = "/plains/plains_" + Integer.toString(i) + ".png";
				tile[i] = new Tile();
				tile[i].image = ImageIO.read(getClass().getResourceAsStream(str));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String s, Map[][] map) {
		int row = 0;
		int col = 0;
		try {
			InputStream is = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for(int i = 0; i < Game_Frame.TILE_NUM_X; i++) {
				for(int j = 0; j < Game_Frame.TILE_NUM_Y; j++) {
					map[i][j] = new Map();
				}
			}

			for(row = 0; row < Game_Frame.TILE_NUM_Y; row++) {
				String line = br.readLine();
				String numbers[] = line.split(",");
				for(col = 0; col < Game_Frame.TILE_NUM_X; col++) {
					int num = Integer.parseInt(numbers[col]);
					if(num != -1) {
						map[col][row].iD = (num << 3) >> 3;
						map[col][row].rotation = num >> 29;
						map[col][row].collision = tile[map[col][row].iD].collision;
						if(map[col][row].rotation != 0) {
							map[col][row].rotateCollision(map[col][row].rotation);
						}
					}
					else {
						map[col][row].iD = -1;
						map[col][row].collision = 0;
						map[col][row].rotation = 0;
					}
				}
			}
			br.close();
		}catch(Exception e){
			//System.out.println("You dumb fuck, don't make body maps with -1 in them:" + "\t" + col + "\t" + row + "\t" + s);
		}
	}

	public void draw(Graphics g) {
		/*
		for(int k = 0; k < 2; ++k) {
			switch(k) {
				case 0:
					drawMap(g, base);
					break;
				case 1:
					drawMap(g, body);
					break;
				case 2:
					drawMap(g, detail);
					break;
			}
		}
		*/

		drawMap(g, body);
	}


	public void drawMap(Graphics g, Map[][] map) {


		for(int i = 0; i < Game_Frame.TILE_NUM_X; i ++) {
			for(int j = 0; j < Game_Frame.TILE_NUM_Y; j ++) {

				int x = i * Game_Frame.TILE_SIZE;
				int y = j * Game_Frame.TILE_SIZE;

				if(map[i][j].iD != -1) {
					int theta = rotation(map[i][j].rotation);
					BufferedImage tmp = tile[map[i][j].iD].image;

					if(theta > 0) {
						tmp = Funcs.rotate(tmp, Math.PI * (double)theta/180, theta);
					}

					g.drawImage(tmp, x, y, Game_Frame.TILE_SIZE, Game_Frame.TILE_SIZE, null);
				}
			}
		}
	}

	public int rotation(int n) {
		int ret = 0;
		switch(n) {
			case 0:
				ret = 0;
				break;
			case -3:
				ret = 270;
				break;
			case -2:
				ret = 180;
				break;
			case 3:
				ret = 90;
				break;
		}
		return ret;
	}
}
