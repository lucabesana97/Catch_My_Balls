package gameobject;

import java.awt.Graphics;

public abstract class GameObject {
	public double x,y;
	public int size;
	public double speed, vertical_speed; 
	public double vertical_acceleration = 1;
	public Direction dir;
	public State state;
	public abstract void draw(Graphics graphics);
}
