package com.apache.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.apache.Position;
import com.apache.Settings;


public abstract class Entity {

	protected Position pos;
	
	protected Rectangle hitBox; 
	
	protected int width, height;
	
    private float lastX, lastY;

	protected boolean colliding;
	
	protected boolean moving;

	public Entity(Position pos) {
		this.pos = pos;
		this.height = this.width = Settings.TILE_SIZE_DEFAULT - Settings.COLLISION_PADDING*2;
		this.hitBox = new Rectangle(pos.getX(), pos.getY(), width, height);
		this.lastX = pos.getX();
		this.lastY = pos.getY();
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
	
	public boolean circleIntersects(Entity entity) {
		float x = entity.getPos().getX();
		float y = entity.getPos().getY();
		float distance = (float) (Math.pow((pos.getX() - x), 2) + Math.pow((pos.getY() - y), 2));
		float radius = entity.getHeight() / 2;
		float targetDistance = (float) Math.pow(((height / 2) + radius), 2);
		if (distance < targetDistance)
			return true;
		return false;
	}

	public boolean rectanglesCollide(Entity entity) {
		if (hitBox.intersects(entity.hitBox))
			return true;
		return false;
	}
	
	public void updateYPosition(float y) {
		pos.setY(y);
		hitBox.setY(y);
	}
	
	public void updateXPosition(float x) {
		pos.setX(x);
		hitBox.setX(x);
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
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
