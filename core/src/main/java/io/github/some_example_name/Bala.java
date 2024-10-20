package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala {
	
	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	
	public Bala(float x, float y, int xSpeed, int ySpeed, Texture tex) {
		spr = new Sprite(tex);
		spr.setPosition(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void update() {
		spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	public void draw(SpriteBatch batch) {
		spr.draw(batch);
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
}
