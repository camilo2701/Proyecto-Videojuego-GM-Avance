package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemigo {
	protected boolean destroyed = false;
	protected Sprite spr;
	protected int vida;
	protected Sound hurtSound;
	
	public Enemigo(float x, float y, Texture tex) {
		spr = new Sprite(tex);
		spr.setPosition(x, y);
		this.hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
	}
	
	public void recibirDaño() {
		vida--;
		hurtSound.play();
	}
	
	public boolean estaVivo() {
		return vida > 0;
	}
	
	public void update(float delta) {
		spr.setX(spr.getX() - 200 * delta);
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	public void render(SpriteBatch batch) {
		spr.draw(batch);
	}
	
	public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public abstract void checkCollision(Player tarro);
}
