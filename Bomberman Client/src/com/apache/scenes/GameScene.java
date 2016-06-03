package com.apache.scenes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.apache.Entity;
import com.apache.Game;
import com.apache.Position;

public class GameScene extends Scene {

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private TiledMap map;
	
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
		float radiusOne = entity.getImage().getHeight() / 2;
		float radiusTwo = entityTwo.getImage().getHeight() / 2;
		float targetDistance = (float) Math.pow((radiusOne + radiusTwo), 2);
		if (distance < targetDistance)
			return true;
		return false;
	}

	public boolean rectanglesCollide(Entity entity, Entity entityTwo) {
		Rectangle rectangleOne = new Rectangle((int) entity.getPos().getX(), (int) entity.getPos().getY(),
				entity.getImage().getWidth(), entity.getImage().getHeight());
		Rectangle rectangleTwo = new Rectangle((int) entityTwo.getPos().getX(), (int) entityTwo.getPos().getY(),
				entityTwo.getImage().getWidth(), entityTwo.getImage().getHeight());
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
			entity.checkInput(t, map);
			for (int index2 = 0; index2 < entities.size(); index2++) {
				if (index == index2)
					continue;
				if (rectanglesCollide(entity, entities.get(index2))) {
					Random random = new Random();
					entities.get(index2).getPos().setX(random.nextInt(700));
					entities.get(index2).getPos().setY(random.nextInt(500));
				}
			}
		}
	}

	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap("res/bomberman.tmx");
		entities.add(new Entity(new Position(32, 32), gc.getInput(), new Image("res/square.png")));
	}

	public String toString() {
		return "Game";
	}
}
