package com.apache.scenes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.apache.Game;
import com.apache.entities.Position;
import com.apache.gui.Interface;
import com.apache.gui.impl.LoginMenu;

public class MenuScene extends Scene {

    public MenuScene() {
        super();
        setPriority(1);
    }

    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        for (Interface ui : interfaces) {
            ui.render(g);
        }
    }

    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {

            Game.manager.clear();
            Game.manager.addScene(new GameScene());
        }
        for (Interface ui : interfaces) {
            ui.update(gc);
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        interfaces.add(new LoginMenu(new Position(400, 300)));
        for (Interface ui : interfaces) {
            ui.init(gc);
        }
    }

    public String toString() {
        return "Menu";
    }
}
