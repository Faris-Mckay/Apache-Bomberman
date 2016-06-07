package com.apache.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

import com.apache.Entity;
import com.apache.Position;
import com.apache.Settings;

public class Player extends Entity{

	private Input input;
	
	private Animation animation;
	
	public Player(Position pos, SpriteSheet spriteSheet, Input input) {
		super(pos);
		this.animation = new Animation(spriteSheet, Settings.ANIM_DURATION);
		this.input = input;
	}

	@Override
	public void render(Graphics g) {
		//g.fillRect(pos.getX(), pos.getY(), width, height);
		animation.draw(pos.getX(), pos.getY());
		//g.drawImage(image, pos.getX(), pos.getY(), new Color(145, 132, 39));
	}
	
	@Override
	public void update(int time) {
		animation.update(time);
		float speed = (float) (0.2 * time);
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

}
