package classes;

import com.badlogic.gdx.audio.Sound;
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
	
	// declaración de objeto de estrategia de movimiento
	protected MovementStrategy movementStrategy;
	
	// método para cambiar la estrategia dinámicamente
	public void setMovementStrategy(MovementStrategy mvtStrgy) {
		this.movementStrategy = mvtStrgy;
	}
	
	// método plantilla Template
	public final void actualizacionEnemigo(float delta, SpriteBatch batch) {
		update(delta);
		render(batch);
	}
	
	public boolean estaVivo() {
		return vida > 0;
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
	
	// método abstracto update que las subclases deben implementar para la plantilla Template
	public abstract void update(float delta);
	
	// método abstracto render que las subclases deben implementar para la plantilla Template
	public abstract void render(SpriteBatch batch);
}
