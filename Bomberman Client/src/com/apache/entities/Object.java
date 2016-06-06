package com.apache.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.apache.Entity;
import com.apache.Position;

public class Object extends Entity{

	private Image image;
	
	public Object(Position pos, Image image) {
		super(pos);
		this.image = image;
	}

	@Override
	public void render(Graphics g) {
		if(image != null)
			g.drawImage(image, pos.getX(), pos.getY());
	}

	@Override
	public void update(int time) {
		
	}
	
	public Image getImage() {
		return image;
	}

}
