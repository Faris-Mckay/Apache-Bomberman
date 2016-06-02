package com.apache;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Entity {

	private Position pos;
	private Input input;
	private Image image;
	
	public Entity(Position pos, Input input, Image image){
		this.pos = pos;
		this.input = input;
		this.image = image;
	}
	
	public void render(){
		image.draw(pos.getX(), pos.getY());
		image.rotate(1);
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

	public void checkInput() {
		if(input.isKeyDown(Input.KEY_UP)){
			pos.setY((float) (pos.getY() - (0.1 * Game.gameTime)));
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			pos.setY((float) (pos.getY() + (0.1 * Game.gameTime)));
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			pos.setX((float) (pos.getX() + (0.1 * Game.gameTime)));
		}
		if(input.isKeyDown(Input.KEY_LEFT)){
			pos.setX((float) (pos.getX() - (0.1 * Game.gameTime)));
		}
	}
	
}
