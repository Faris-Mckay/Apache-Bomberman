package com.apache.entities.impl;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.apache.entities.Entity;
import com.apache.entities.Position;

public class Object extends Entity {

    private Image image;

    private boolean breakable;

    public Object(Position pos, Image image, boolean breakable) {
        super(pos);
        this.breakable = breakable;
        this.image = image;
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, pos.getX(), pos.getY());
        }
    }

    @Override
    public void update(int time) {

    }

    public Image getImage() {
        return image;
    }

}
