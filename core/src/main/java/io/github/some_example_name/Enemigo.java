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
	protected TextureRegion[][] tmp;
	protected Animation<TextureRegion> animacion;
	protected float tiempo = 0f;
	
	public Enemigo(float x, float y, Texture tex) {
		// se carga sonido de zombie herido
		this.hurtSound = Gdx.audio.newSound(Gdx.files.internal("zombieHit.ogg"));
		// se hace un split para lograr diferenciar distintas animaciones de la sheet
		tmp = TextureRegion.split(tex, tex.getWidth()/4, tex.getHeight());
		// se crea "frames" para almacenar cada imagen de la animación
		TextureRegion[] frames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			TextureRegion frameActual = tmp[0][i];
			frames[i] = frameActual;
	    }
		
		animacion = new Animation<TextureRegion>(0.2f, frames);
		// se asigna la primera imagen al enemigo
		spr = new Sprite(frames[0]);
		// se setea su posicion de aparición
		spr.setPosition(x, y);
		
	}
	
	public void recibirDaño() {
		// disminuye 1 a la vida del enemigo y reproduce sonido de herido
		vida--;
		hurtSound.play();
	}
	
	public boolean estaVivo() {
		return vida > 0;
	}
	
	public void update(float delta) {
		// movimiento del enemigo, cambiando la posicion considerando su velocidad
		// y tiempo transcurrido
		spr.setX(spr.getX() - 500 * delta);
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	public void render(SpriteBatch batch) {
		// se almacena tiempo transcurrido en "tiempo"
		tiempo += Gdx.graphics.getDeltaTime();
		// se instancia variable para almacenar las imagenes de las animaciones en bucle
		TextureRegion frameActual = animacion.getKeyFrame(tiempo, true);
		// se setea la animación en el sprite
		spr.setRegion(frameActual);
		// se dibuja la animación
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
