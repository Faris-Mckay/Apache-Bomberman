package com.apache;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

public class Game extends BasicGame{

	private Input displayStats;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public static int gameTime;
	
	public Game(String game) {
		super(game);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for(Entity entity : entities)
			entity.render();
		if(displayStats != null){
			g.drawString("Mouse X: " + displayStats.getMouseX(), 10, 25);
			g.drawString("Mouse Y: " + displayStats.getMouseY(), 10, 40);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		displayStats = gc.getInput();
		entities.add(new Entity(new Position(0, 0), gc.getInput(), new Image("res/image.png")));
		entities.add(new Entity(new Position(100, 100), null, new Image("res/image.png")));
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		gameTime = t;
		for(int index = 0; index < entities.size(); index++){
			Entity entity = entities.get(index);
			entity.checkInput();
			for(int index2 = 0; index2 < entities.size(); index2++){
				if(index == index2)
					continue;
				Entity entityTwo = entities.get(index2);
				float x = entity.getPos().getX();
				float y = entity.getPos().getY();
				float x2 = entityTwo.getPos().getX();
				float y2 = entityTwo.getPos().getY();
				float distance = (float) (Math.pow((x - x2), 2) + Math.pow((y - y2), 2));
				float radiusOne = entity.getImage().getHeight()/2;
				float radiusTwo = entityTwo.getImage().getHeight()/2;
				float targetDistance = (float) Math.pow((radiusOne + radiusTwo), 2);
				if(distance < targetDistance){
					Random random = new Random();
					entityTwo.getPos().setX(random.nextInt(700));
					entityTwo.getPos().setY(random.nextInt(500));
					entities.set(index2, entityTwo);
				}
			}
		}
		
	}

}
