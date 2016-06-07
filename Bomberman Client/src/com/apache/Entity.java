package com.apache;

import org.newdawn.slick.Graphics;


public abstract class Entity {

	protected Position pos;
	
	protected int width, height;

	protected boolean colliding;

	public Entity(Position pos) {
		this.pos = pos;
		height = width = Settings.TILE_SIZE_DEFAULT;
	}

	public abstract void render(Graphics g);

	public abstract void update(int time);

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}
	
}
