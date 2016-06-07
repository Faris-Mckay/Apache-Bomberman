package com.apache.entities;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.apache.Entity;
import com.apache.Position;
import com.apache.Settings;
import com.apache.scenes.Scene;

public class Bomb extends Entity {

	private long timeLeft;

	private Animation bomb;
	private Animation explosion;

	private boolean exploding;

	private Player player;

	public Bomb(Player player, Position pos, int timerTime) {
		super(pos);
		this.player = player;
		loadAssets();
		setTimer(timerTime);
	}

	private void setTimer(int timerTime) {
		TimerTask task = new TimerTask() {
			int i = 0;
			@Override
			public void run() {
				i++;
				if (i % timerTime == 0) {
					exploding = true;
					this.cancel();
					final int duration = 50;
					TimerTask task = new TimerTask() {
						int i = 0;
						@Override
						public void run() {
							i++;
							if (i % duration == 0) {
								this.cancel();
								player.getBombs().remove(0);
							}
						}
					};
					Timer timer = new Timer();
					timer.schedule(task, 0, duration);
				} else {
					timeLeft = (timerTime - (i % timerTime));
					exploding = false;
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 5, timerTime);
	}

	private void loadAssets() {
		try {
			this.bomb = new Animation(
					new SpriteSheet("res/bomb.png", Settings.TILE_SIZE_DEFAULT, Settings.TILE_SIZE_DEFAULT),
					Settings.ANIM_DURATION);
			this.explosion = new Animation(
					new SpriteSheet("res/explosions.png", Settings.TILE_SIZE_DEFAULT, Settings.TILE_SIZE_DEFAULT),
					Settings.ANIM_DURATION);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		if (!exploding) {
			bomb.draw(pos.getX(), pos.getY(), player.getColour());
			Scene.drawCenteredString(g, "" + timeLeft,
					new Rectangle((int) pos.getX(), (int) pos.getY(), width, height));
		} else {
			explosion.draw(pos.getX(), pos.getY(), player.getColour());
		}
	}

	@Override
	public void update(int time) {
		if (exploding) {
			explosion.update(time);
		} else {
			bomb.update(time);
		}
	}

}
