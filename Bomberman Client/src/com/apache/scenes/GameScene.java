package com.apache.scenes;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.apache.Game;
import com.apache.Position;
import com.apache.Settings;
import com.apache.entities.Entity;
import com.apache.entities.impl.Object;
import com.apache.entities.impl.Player;
import com.apache.maps.GenericMap;

public class GameScene extends Scene {

	private static ArrayList<Entity> entities = new ArrayList<Entity>();

	private GenericMap map;
	
	public GameScene() {
		super();
		setPriority(2);
	}

	protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
		map.render(0, 0);
		for (Entity entity : entities)
			entity.render(g);
	}

	public boolean circleIntersects(Entity entity, Entity entityTwo) {
		float x = entity.getPos().getX();
		float y = entity.getPos().getY();
		float x2 = entityTwo.getPos().getX();
		float y2 = entityTwo.getPos().getY();
		float distance = (float) (Math.pow((x - x2), 2) + Math.pow((y - y2), 2));
		float radiusOne = entity.getHeight() / 2;
		float radiusTwo = entityTwo.getHeight() / 2;
		float targetDistance = (float) Math.pow((radiusOne + radiusTwo), 2);
		if (distance < targetDistance)
			return true;
		return false;
	}

	public boolean rectanglesCollide(Entity entity, Entity entityTwo) {
		final int padding = Settings.COLLISION_PADDING;
		Rectangle rectangleOne = new Rectangle((int) entity.getPos().getX() + padding, (int) entity.getPos().getY() + padding,
				entity.getWidth() - (padding*2), entity.getHeight() - (padding*2));
		Rectangle rectangleTwo = new Rectangle((int) entityTwo.getPos().getX() + padding, (int) entityTwo.getPos().getY() + padding,
				entityTwo.getWidth() - (padding*2), entityTwo.getHeight() - (padding*2));
		if (rectangleOne.intersects(rectangleTwo))
			return true;
		return false;
	}
	
	protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			Game.manager.newScene(new MenuScene());
		}
		for (int index = 0; index < entities.size(); index++) {
			Entity entity = entities.get(index);
			entity.update(t);
			entity.setMoving(!(entity.getPos().getX() == entity.getLastX() && entity.getPos().getY() == entity.getLastY()));
			for (int index2 = 0; index2 < entities.size(); index2++) {
				if (index == index2)
					continue;
				if (rectanglesCollide(entity, entities.get(index2))) {
					entity.setColliding(true);
					if(entity.getPos().getX() != entity.getLastX())
						entity.getPos().setX(entity.getLastX());
					if(entity.getPos().getY() != entity.getLastY())
						entity.getPos().setY(entity.getLastY());
					entity.setColliding(false);
				}
			}
			entity.setLastX(entity.getPos().getX());
			entity.setLastY(entity.getPos().getY());
		}
	}

	private final static int[][] cantSpawnLoc = {{1,1},{1,2}, {2,1}, {3,1},{1,3} };
	
	public boolean freeTile(int x, int y){
		for(int index = 0; index < cantSpawnLoc.length; index++){
			if(x == cantSpawnLoc[index][0] && x == cantSpawnLoc[index][1])
				return false;
		}
		for(Entity entity : entities){
			if(entity.getTilePos().getX() == x && entity.getTilePos().getY() == y)
				return false;
		}
		return true;
	}
	
	public void init(GameContainer gc) throws SlickException {
		map = new GenericMap();
		entities.add(new Player(new Position(65, 33), gc.getInput()));
		//System.out.println("rectangles: " + map.getTiles().size());
		Image breakableSprite = new Image("res/breakable.png");
		
		for(int index = 0; index < map.getTiles().size(); index ++){
			Rectangle tile = map.getTiles().get(index);
			entities.add(new Object(new Position(tile.getX(), tile.getY()), null, false));
		}
		for(int i = 0; i < 300; i++){
			Random random  = new Random();
			int x = random.nextInt(17);
			int y = random.nextInt(17);
			if(freeTile(x, y)){
				entities.add(new Object(new Position(x * Settings.TILE_SIZE_DEFAULT, y * Settings.TILE_SIZE_DEFAULT), breakableSprite, true));
			}
		}
	}

	public String toString() {
		return "Game";
	}
}
