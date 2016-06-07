package com.apache.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.apache.Entity;
import com.apache.Position;

public class Player extends Entity{

	private Input input;
	private Image image;
        private boolean inGame = false;
	
	public Player(Position pos, Image image, Input input) {
		super(pos);
		this.image = image;
		this.input = input;
		if(image != null){
			height = image.getHeight();
			width = image.getWidth();
		}
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(pos.getX(), pos.getY(), width, height);
	}
	
	@Override
	public void update(int time) {
		float speed = (float) (0.1 * time);
		if (input != null){
			if(!colliding) {
				if (input.isKeyDown(Input.KEY_UP)) {
					pos.setY((float) (pos.getY() - speed));
				}
				if (input.isKeyDown(Input.KEY_DOWN)) {
					pos.setY((float) (pos.getY() + speed));
				}
				if (input.isKeyDown(Input.KEY_RIGHT)) {
					pos.setX((float) (pos.getX() + speed));
				}
				if (input.isKeyDown(Input.KEY_LEFT)) {
					pos.setX((float) (pos.getX() - speed));
				}
			}
		}
	}
	
	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
