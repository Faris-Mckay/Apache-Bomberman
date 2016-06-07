package com.apache.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.apache.Entity;
import com.apache.Position;
import com.apache.Settings;

public class Player extends Entity{

	private Input input;
	private Animation animation;
	private SpriteSheet[] sheets = new SpriteSheet[4];
    private boolean inGame = false;
    private boolean up, down, right, left;
	
	public Player(Position pos, Input input) throws SlickException {
		super(pos);
		for(int index = 0; index < sheets.length; index++){
			sheets[index] = new SpriteSheet("res/sheet"+ index + ".png", 32, 32);
		}
		this.animation = new Animation(sheets[1], Settings.ANIM_DURATION);
		this.input = input;
	}

	@Override
	public void render(Graphics g) {
		animation.draw(pos.getX(), pos.getY());
	}
	
	@Override
	public void update(int time) {
		animation.update(time);
		float speed = (float) (0.2 * time);
		if (input != null){
			if(!colliding) {
				if (input.isKeyDown(Input.KEY_UP)) {
					if(!up){
						resetDirection();
						up = true;
						changeDirection(sheets[0]);
					}
					pos.setY((float) (pos.getY() - speed));
				}
				if (input.isKeyDown(Input.KEY_DOWN)) {
					if(!down){
						resetDirection();
						down = true;
						changeDirection(sheets[1]);
					}
					pos.setY((float) (pos.getY() + speed));
				}
				if (input.isKeyDown(Input.KEY_RIGHT)) {
					if(!right){
						resetDirection();
						right = true;
						changeDirection(sheets[2]);
					}
					pos.setX((float) (pos.getX() + speed));
				}
				if (input.isKeyDown(Input.KEY_LEFT)) {
					if(!left){
						resetDirection();
						left = true;
						changeDirection(sheets[3]);
					}
					pos.setX((float) (pos.getX() - speed));
				}
			}
		}
	}
	
	private void resetDirection() {
		left = right = up = down = false;
	}
	
	private void changeDirection(SpriteSheet sheet){
		animation = new Animation(sheet, Settings.ANIM_DURATION);
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

}
