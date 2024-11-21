package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import interfaces.Collisions;
import interfaces.DamageControl;
import interfaces.MovementStrategy;

public abstract class Enemigo implements Collisions, DamageControl{
	
	protected boolean destroyed = false;
	protected int vida;
	protected Sound hurtSound;
	
	protected Sprite spr;
	protected TextureRegion[][] tmp;
	protected Animation<TextureRegion> animacion;
	protected float tiempo = 0f;
	
	protected MovementStrategy movementStrategy;
	
	public Enemigo(float x, float y, Texture tex, MovementStrategy mvtStrgy) {
		// se carga la estrategia por parámetro
		this.movementStrategy = mvtStrgy;
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
	
	public void setMovementStrategy(MovementStrategy mvtStrgy) {
		this.movementStrategy = mvtStrgy;
	}
	
	public boolean estaVivo() {
		return vida > 0;
	}
	
	public void update(float delta) {
		// movimiento del enemigo dependiendo de la estrategia de este
        movementStrategy.movimiento(spr, delta);
        // comprobar que no se sale de los bordes, en caso de ser así, se destruye
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
	
	public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}
	
	@Override
	public void manejarColision(Collisions obj) {
		// si zombie colisionó con una bala, el zombie recibe daño
		if (obj instanceof Bala) {
			recibirDaño();
		}
	}
	
	@Override
	public void recibirDaño() {
		// disminuye 1 a la vida del enemigo y reproduce sonido de herido
		vida--;
		hurtSound.play();
	}
	
	public abstract int darPuntos();
}
