package com.apache.entities.impl;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.apache.Position;
import com.apache.Settings;
import com.apache.entities.Entity;

public class Player extends Entity{

	private Input input;
	private Animation animation;
	private SpriteSheet[] sheets = new SpriteSheet[4];
    private boolean inGame = false;
    private boolean up, down, right, left;
    
    private long lastBomb;
	
	public Player(Position pos, Input input) throws SlickException {
		super(pos);
		for(int index = 0; index < sheets.length; index++){
			sheets[index] = new SpriteSheet("res/sheet"+ index + ".png", Settings.TILE_SIZE_DEFAULT, Settings.TILE_SIZE_DEFAULT);
		}
		this.animation = new Animation(sheets[1], Settings.ANIM_DURATION);
		this.animation.setPingPong(true);
		this.input = input;
	}

	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}

	@Override
	public void render(Graphics g) {
		animation.draw(pos.getX(), pos.getY());
		for(Bomb bomb : bombs)
			bomb.render(g);
	}
	
	@Override
	public void update(int time) {
		for(Bomb bomb : bombs)
			bomb.update(time);
		animation.update(time);
		if(moving){
			animation.start();
			animation.setLooping(true);
		} else {
			animation.stop();
		}
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
				if(input.isKeyDown(Input.KEY_SPACE)){
					Bomb bomb = new Bomb(this, new Position(
							getTilePos().getX() * Settings.TILE_SIZE_DEFAULT,
							getTilePos().getY() * Settings.TILE_SIZE_DEFAULT), 100);
					if(System.currentTimeMillis() > lastBomb + 500){
						if(!cantDrop(bomb) || bombs.size() == 0){
							bombs.add(bomb);
							lastBomb = System.currentTimeMillis();
						}
					}
				}
			}
		}
	}
	
	private boolean cantDrop(Bomb bomb){
		for(Bomb bomb2 : bombs){
			if(bomb2.getTilePos().getX() == bomb.getTilePos().getX()
					&& bomb2.getTilePos().getY() == bomb.getTilePos().getY()){
				return true;
			}
		}
		return false;
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

	public Color getColour() {
		return new Color(255, 111, 80);
	}

}
