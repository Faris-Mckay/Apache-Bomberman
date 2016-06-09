package com.apache.game;

/**
 * Possible game states that a player can be in.
 * 
 * @author JP
 */
public enum GameState {

	UPDATE(0), LOGIN(1), LOBBY(2), GAME(3);

	private int value;

	private GameState(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
