package com.apache;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;


public class Entity {

	private Position pos;
	private Input input;
	private Image image;
	
	private int width, height;

	public Entity(Position pos, Input input, Image image) {
		this.pos = pos;
		this.input = input;
		this.image = image;
	}

	public void render(Graphics g) {
		this.width = 30;
		this.height = 30;
		g.fillRect(pos.getX(), pos.getY(), width, height);
		//image.draw(pos.getX(), pos.getY());
		// image.rotate(1);
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public void checkInput(int time, TiledMap map) {
		float speed = (float) (0.1 * time);
		if(map != null){
			int objectLayer = map.getLayerIndex("Objects");
			if (input != null) {
				if (input.isKeyDown(Input.KEY_UP)) {
					if(map.getTileId((int) (pos.getX())/32, (int) (pos.getY() - 1)/32, objectLayer) == 0){
						pos.setY((float) (pos.getY() - speed));
					}
				}
				if (input.isKeyDown(Input.KEY_DOWN)) {
					if(map.getTileId((int) (pos.getX() + width)/32, ((int) pos.getY() + 1)/32, objectLayer) == 0){
						pos.setY((float) (pos.getY() + speed));
					}
				}
				if (input.isKeyDown(Input.KEY_RIGHT)) {
					if(map.getTileId((int) (pos.getX() + 1)/32, (int) (pos.getY())/32, objectLayer) == 0){
						pos.setX((float) (pos.getX() + speed));
					}
				}
				if (input.isKeyDown(Input.KEY_LEFT)) {
					if(map.getTileId((int) (pos.getX() - 1)/32, (int) (pos.getY())/32, objectLayer) == 0){
						pos.setX((float) (pos.getX() - speed));
					}
				}
			}
		}
	}

	public Image getImage() {
		return image;
	}

}
