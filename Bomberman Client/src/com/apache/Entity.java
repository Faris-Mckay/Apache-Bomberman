package com.apache;

import org.newdawn.slick.Graphics;


public abstract class Entity {

	protected Position pos;
	
	protected int width, height;
	
    private float lastX, lastY;

	protected boolean colliding;
	
	protected boolean moving;

	public Entity(Position pos) {
		this.pos = pos;
		height = width = Settings.TILE_SIZE_DEFAULT;
		lastX = pos.getX();
		lastY = pos.getY();
	}

	public boolean inSameTile(Entity entity) {
		int x1Tile = Math.round(entity.getPos().getX()/Settings.TILE_SIZE_DEFAULT);
		int x2Tile = Math.round(pos.getX()/Settings.TILE_SIZE_DEFAULT);
		int y1Tile = Math.round(entity.getPos().getY()/Settings.TILE_SIZE_DEFAULT);
		int y2Tile = Math.round(pos.getY()/Settings.TILE_SIZE_DEFAULT);
		if(x1Tile == x2Tile && y1Tile == y2Tile)
			return true;
		return false;
	}
	
	public Position getTilePos() {
		int xTile = Math.round(getPos().getX()/Settings.TILE_SIZE_DEFAULT);
		int yTile = Math.round(getPos().getY()/Settings.TILE_SIZE_DEFAULT);
		return new Position(xTile, yTile);
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

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public float getLastY() {
		return lastY;
	}

	public void setLastY(float lastY) {
		this.lastY = lastY;
	}

	public float getLastX() {
		return lastX;
	}

	public void setLastX(float lastX) {
		this.lastX = lastX;
	}
	
}
