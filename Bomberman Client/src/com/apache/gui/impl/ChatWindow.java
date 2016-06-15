package com.apache.gui.impl;

import org.newdawn.slick.geom.Rectangle;

import com.apache.entities.Position;
import com.apache.gui.Interface;
import com.apache.gui.components.Button;
import com.apache.gui.components.TextInput;

public class ChatWindow extends Interface {

	public ChatWindow(Position position) {
		super(position);
		components.add(new Button(new Rectangle(0, 0, 100, 40), "text"));
		components.add(new TextInput(new Rectangle(0, 40, 100, 40), "text"));
	}
	
}
