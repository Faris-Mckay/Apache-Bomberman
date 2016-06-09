package com.apache.gui.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

import com.apache.gui.Component;

public class TextInput extends Component{

	private String text, placeHolder;
	private boolean hovered;
	private boolean focused;
	
	private TextField textField;
	
	public TextInput(Rectangle rectangle, String text) {
		super(rectangle);
		this.placeHolder = this.text = text;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(focused ? new Color(56, 194, 245) : (hovered ? Color.cyan : Color.blue));
		g.fillRect(rectangle.getX() - rectangle.getWidth() / 2, rectangle.getY() - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight());
		g.setColor(focused ? Color.black : (hovered ? Color.black : Color.white));
		drawCenteredString(g, text, new Rectangle(rectangle.getX() - rectangle.getWidth() / 2, rectangle.getY() - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight()));
		g.setColor(Color.white);
	}

	@Override
	public void init(GameContainer gc) {
		textField = new TextField(gc, gc.getDefaultFont(), (int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
	}

	@Override
	public void update(GameContainer gc) {
		textField.setFocus(focused);
		if (gc.getInput().getMouseX() > rectangle.getX() - rectangle.getWidth() / 2 && gc.getInput().getMouseX() < rectangle.getX() + rectangle.getWidth() / 2
				&& gc.getInput().getMouseY() > rectangle.getY() - rectangle.getHeight() / 2 && gc.getInput().getMouseY() < rectangle.getY() + rectangle.getHeight() / 2) {
			hovered = true;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				focused = true;
			}
		} else {
			hovered = false;
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				focused = false;
			}
		}
		if(textField != null){
			if(focused){
				text = textField.getText();
			}
		}
	}

}
