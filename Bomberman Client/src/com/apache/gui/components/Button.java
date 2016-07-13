package com.apache.gui.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.apache.Game;
import com.apache.gui.Component;
import com.apache.scenes.GameScene;

public class Button extends Component {

    private String text;
    private boolean hovered;

    public Button(Rectangle rectangle, String text) {
        super(rectangle);
        this.text = text;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(hovered ? Color.cyan : Color.blue);
        g.fillRect(rectangle.getX() - rectangle.getWidth() / 2, rectangle.getY() - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight());
        g.setColor(hovered ? Color.black : Color.white);
        drawCenteredString(g, text, new Rectangle(rectangle.getX() - rectangle.getWidth() / 2, rectangle.getY() - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight()));
        g.setColor(Color.white);
    }

    @Override
    public void update(GameContainer gc) {
        if (gc.getInput().getMouseX() > rectangle.getX() - rectangle.getWidth() / 2 && gc.getInput().getMouseX() < rectangle.getX() + rectangle.getWidth() / 2
                && gc.getInput().getMouseY() > rectangle.getY() - rectangle.getHeight() / 2 && gc.getInput().getMouseY() < rectangle.getY() + rectangle.getHeight() / 2) {
            hovered = true;
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                Game.manager.clear();
                Game.manager.addScene(new GameScene());
            }
        } else {
            hovered = false;
        }
    }

    @Override
    public void init(GameContainer container) {

    }

}
