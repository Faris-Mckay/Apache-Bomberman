package com.apache;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.apache.scenes.MenuScene;
import com.apache.scenes.SceneManager;

public class Game extends BasicGame {

    public static SceneManager manager;

    private Input displayStats;

    public Game(String game) {
        super(game);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        manager.render(gc, g);
        if (Settings.DEBUG) {
            if (displayStats != null) {
                g.drawString("Mouse X: " + displayStats.getMouseX(), 10, 25);
                g.drawString("Mouse Y: " + displayStats.getMouseY(), 10, 40);
            }
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        displayStats = gc.getInput();
        manager = new SceneManager(gc);
        manager.addScene(new MenuScene());
    }

    @Override
    public void update(GameContainer gc, int t) throws SlickException {
        manager.update(gc, t);
    }

}
