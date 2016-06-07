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
		this.animation.setPingPong(true);
		this.input = input;
	}

	@Override
	public void render(Graphics g) {
		animation.draw(pos.getX(), pos.getY());
	}
	
	@Override
	public void update(int time) {
		animation.update(time);
		if(moving){
			animation.stop();
			animation.setLooping(true);
		}
		System.out.println(moving);
		float speed = (float) (0.2 * time);
		if (input != null){
			if(!colliding) {
				if (input.isKeyDown(Input.KEY_UP)) {
					if(!up){
						resetVars();
						up = true;
						changeDirection(sheets[0]);
					}
					pos.setY((float) (pos.getY() - speed));
				}
				if (input.isKeyDown(Input.KEY_DOWN)) {
					if(!down){
						resetVars();
						down = true;
						changeDirection(sheets[1]);
					}
					pos.setY((float) (pos.getY() + speed));
				}
				if (input.isKeyDown(Input.KEY_RIGHT)) {
					if(!right){
						resetVars();
						right = true;
						changeDirection(sheets[2]);
					}
					pos.setX((float) (pos.getX() + speed));
				}
				if (input.isKeyDown(Input.KEY_LEFT)) {
					if(!left){
						resetVars();
						left = true;
						changeDirection(sheets[3]);
					}
					pos.setX((float) (pos.getX() - speed));
				}
			}
		}
	}
	
	private void resetVars() {
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
