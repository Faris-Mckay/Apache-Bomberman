package com.apache.scenes;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.apache.Game;

public class MenuScene extends Scene {

	private static final int width = 200, height = 75;

	private boolean hovered;

	public MenuScene() {
		super();
		setPriority(1);
	}

	protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(hovered ? Color.cyan : Color.blue);
		g.fillRect(400 - width / 2, 300 - height / 2, width, height);
		g.setColor(hovered ? Color.black : Color.white);
		drawCenteredString(g, "Play", new Rectangle(400 - width / 2, 300 - height / 2, width, height));
	}


	protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			Game.manager.clear();
			Game.manager.addScene(new GameScene());
		}
		if (gc.getInput().getMouseX() > 400 - width / 2 && gc.getInput().getMouseX() < 400 + width / 2
				&& gc.getInput().getMouseY() > 300 - height / 2 && gc.getInput().getMouseY() < 300 + height / 2) {
			hovered = true;
		} else {
			hovered = false;
		}
	}

	public void init(GameContainer gc) throws SlickException {

	}

	public String toString() {
		return "Menu";
	}
}
