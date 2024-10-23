package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemigo implements Collisions, Areas{
	protected boolean destroyed = false;
	protected int vida;
	protected Sound hurtSound;
	
	protected Sprite spr;
	private TextureRegion[][] tmp;
	private Animation<TextureRegion> animacion;
	private float tiempo = 0f;
	
	public Enemigo(float x, float y, Texture tex) {
		
		tmp = TextureRegion.split(tex, tex.getWidth()/4, tex.getHeight());
		TextureRegion[] frames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			TextureRegion frameActual = tmp[0][i];
			frames[i] = frameActual;
	    }
		
		animacion = new Animation<TextureRegion>(0.2f, frames);
		spr = new Sprite(frames[0]);
		spr.setPosition(x, y);
		this.hurtSound = Gdx.audio.newSound(Gdx.files.internal("zombieHit.ogg"));
	}
	
	public void recibirDaño() {
		vida--;
		hurtSound.play();
	}
	
	public boolean estaVivo() {
		return vida > 0;
	}
	
	public void update(float delta) {
		spr.setX(spr.getX() - 500 * delta);
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	public void render(SpriteBatch batch) {
		TextureRegion frameActual = animacion.getKeyFrame(tiempo, true);
		tiempo += Gdx.graphics.getDeltaTime();
		spr.setRegion(frameActual);
		spr.draw(batch);
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	@Override
	public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}
	
	public abstract int darPuntos();
}
