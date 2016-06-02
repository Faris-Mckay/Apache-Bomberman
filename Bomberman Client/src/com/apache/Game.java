package com.apache;

import org.newdawn.slick.*;

public class Game extends BasicGame{

	private Input displayStats;
	
	private Entity player;
	
	public static int gameTime;
	
	public Game(String game) {
		super(game);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		player.render();
		if(displayStats != null){
			g.drawString("Mouse X: " + displayStats.getMouseX(), 10, 25);
			g.drawString("Mouse Y: " + displayStats.getMouseY(), 10, 40);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		displayStats = gc.getInput();
		player = new Entity(new Position(0, 0), gc.getInput(), new Image("res/image.png"));
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		gameTime = t;
		player.checkInput();
	}

}
