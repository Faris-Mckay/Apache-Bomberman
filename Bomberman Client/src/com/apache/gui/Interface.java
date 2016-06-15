package com.apache.gui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.apache.entities.Position;

public abstract class Interface {

	protected ArrayList<Component> components = new ArrayList<Component>();
	
	protected Position position;
	
	public Interface(Position position){
		this.setPosition(position);
	}
	
	public void render(Graphics g){
		for(Component component : components){
			component.render(g);
		}
	}
	
	public void update(GameContainer gc){
		for(Component component : components){
			component.update(gc);
		}
	}

	public void init(GameContainer gc){
		for(Component component : components){
			component.getRectangle().setX(position.getX() + component.getRectangle().getX());
			component.getRectangle().setY(position.getY() + component.getRectangle().getY());
			component.init(gc);
		}
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}
	
}
