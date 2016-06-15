package com.apache.gui.impl;

import org.newdawn.slick.geom.Rectangle;

import com.apache.entities.Position;
import com.apache.gui.Interface;
import com.apache.gui.components.Button;
import com.apache.gui.components.TextInput;

public class LoginMenu extends Interface {

	public LoginMenu(Position position) {
		super(position);
		components.add(new TextInput(new Rectangle(0, 0, 200, 50), "Username Here"));
		components.add(new Button(new Rectangle(0 ,55, 200, 50), "Play"));
	}

}
