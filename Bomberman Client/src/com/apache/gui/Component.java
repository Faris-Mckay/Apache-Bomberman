package com.apache.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.apache.Position;

public abstract class Component {

	protected Position position;
	protected Rectangle rectangle;
	
	public Component(Rectangle rectangle){
		this.setRectangle(rectangle);
		this.setPosition(new Position(rectangle.getX(), rectangle.getY()));
	}
	
	public abstract void render(Graphics g);
	
	public abstract void update(GameContainer gc);

	public abstract void init(GameContainer container);

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public static void drawCenteredString(Graphics g, String string, Rectangle r) {
		int width = g.getFont().getWidth(string);
		int height = g.getFont().getHeight(string);
		g.drawString(string, (r.getX() + r.getWidth() / 2) - (width / 2), (r.getY() + r.getHeight()/ 2) - (height / 2));
	}

	
}
