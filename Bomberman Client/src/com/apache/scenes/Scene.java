package com.apache.scenes;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Scene implements Comparable<Scene> {
	
	// The states a scene can be in
	public enum STATE {
		ON,
		FREEZE,
		FREEZE_NEXT,
		INVISIBLE
	};

	// The current state is saved in this variable
	private STATE state;
	// The render priority - We will need this to decide whitch scene is renderd
	// first
	private int priority = 0;

	// An Image "Buffer" to hold the last active frame when our scene get
	// freezed
	private Image scene;
	
	public static void drawCenteredString(Graphics g, String string, Rectangle r) {
		int width = g.getFont().getWidth(string);
		int height = g.getFont().getHeight(string);
		g.drawString(string, (r.x + r.width / 2) - (width / 2), (r.y + r.height / 2) - (height / 2));
	}

	public Scene() {
		// Default state is on
		state = STATE.ON;
		try {
			// Change to your resolution
			// Init the buffer
			scene = new Image(800, 600);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// These methods will be used by the "real" scenes that inherit from this
	// scene
	protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {

	}

	protected void CustomUpdate(GameContainer gc, int t) throws SlickException {

	}

	public void init(GameContainer gc) throws SlickException {

	}
	// These methods will be used by the "real" scenes that inherit from this
	// scene

	// This render method get called by the Scene-Manager
	// It handles if the scene is visible/frozen/on
	// This method calls our "CustomRender"
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (state != STATE.INVISIBLE) {
			if (state == STATE.ON) {
				CustomRender(gc, g);
			}
			if (state == STATE.FREEZE_NEXT) {
				scene.getGraphics().clear();
				CustomRender(gc, scene.getGraphics());
				state = STATE.FREEZE;
			}
			if (state == STATE.FREEZE) {
				g.drawImage(scene, 0, 0);
			}
		}
	}

	// Update method that is called by the Scene-Manager
	// This method calls our "CustomUpdate"
	public void update(GameContainer gc, int t) throws SlickException {
		if (state == STATE.ON) {
			CustomUpdate(gc, t);
		}
	}

	// Set the render priority
	public void setPriority(int p) {
		priority = p;
	}

	// Get the name of the scene to get a scene by name
	public String toString() {
		return "default";
	}

	// Returns the render priority
	public int getPriority() {
		return priority;
	}

	// Used to compare two objects in an Collection
	public int compareTo(Scene compareObject) {
		if (getPriority() < compareObject.getPriority())
			return -1;
		else if (getPriority() == compareObject.getPriority())
			return 0;
		else
			return 1;
	}

	// Set the current state of the scene
	public void setState(STATE s) {
		state = s;
	}

}
