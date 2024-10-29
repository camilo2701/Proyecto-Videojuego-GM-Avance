package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import interfaces.Collisions;

public class Bala implements Collisions{
	
	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	
	public Bala(float x, float y, int xSpeed, int ySpeed, Texture tex) {
		// crear Sprite con Texture, setear posicion y velocidad
		spr = new Sprite(tex);
		spr.setPosition(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public void update() {
		// movimiento de la bala, cambiando la posicion considerando su velocidad
		spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
		// en caso de salir de los bordes, se destruye la bala
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	public void draw(SpriteBatch batch) {
		// dibujo de la baja
		spr.draw(batch);
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}

	@Override
	public void manejarColision(Collisions obj) {
		// si bala colisionó con un cualquier tipo de enemigo (zombie o zombieOP),
		// la bala se destruye (polimorfismo)
		if (obj instanceof Enemigo) {
			destroyed = true;
		}
	}
}
