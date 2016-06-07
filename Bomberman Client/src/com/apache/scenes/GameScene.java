package com.apache.scenes;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import com.apache.Entity;
import com.apache.Game;
import com.apache.GenericMap;
import com.apache.Position;
import com.apache.Settings;
import com.apache.entities.Object;
import com.apache.entities.Player;

public class GameScene extends Scene {

	private ArrayList<Entity> entities = new ArrayList<Entity>();

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
			float lastX = entity.getPos().getX();
			float lastY = entity.getPos().getY();
			entity.update(t);
			for (int index2 = 0; index2 < entities.size(); index2++) {
				if (index == index2)
					continue;
				if (rectanglesCollide(entity, entities.get(index2))) {
					entity.setColliding(true);
					if(entity.getPos().getX() != lastX)
						entity.getPos().setX(lastX);
					if(entity.getPos().getY() != lastY)
						entity.getPos().setY(lastY);
					entity.setColliding(false);
				}
			}
		}
	}

	public void init(GameContainer gc) throws SlickException {
		map = new GenericMap();
		entities.add(new Player(new Position(33, 33), new SpriteSheet("res/you.png", 32, 32), gc.getInput()));
		System.out.println("rectangles: " + map.getTiles().size());
		for(int index = 0; index < map.getTiles().size(); index ++){
			Rectangle tile = map.getTiles().get(index);
			entities.add(new Object(new Position(tile.getX(), tile.getY()), null));
		}
	}

	public String toString() {
		return "Game";
	}
}
