package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import interfaces.MovementStrategy;

public class Zombie extends Enemigo {
	
	public Zombie(float x, float y, MovementStrategy mvtStrgy) {
		// se carga la estrategia por parámetro
		this.movementStrategy = mvtStrgy;
		// se carga sonido de zombie herido
		this.hurtSound = Gdx.audio.newSound(Gdx.files.internal("zombieHit.ogg"));
		// se carga la textura de ZombieOP
		Texture tex = new Texture(Gdx.files.internal("zomSpriteSheet.png"));
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
		// se settea su posicion de aparición
		spr.setPosition(x, y);
		// se settea la vida en 1
		this.vida = 1;
	}
	
	@Override
	protected void update(float delta) {
		// movimiento del enemigo dependiendo de la estrategia de este
        movementStrategy.movimiento(spr, delta);
        // comprobar que no se sale de los bordes, en caso de ser así, se destruye
		if (spr.getX() < 0 || spr.getX() + spr.getWidth() > 800) destroyed = true;
		if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) destroyed = true;
	}
	
	@Override
	protected void render(SpriteBatch batch) {
		// se almacena tiempo transcurrido en "tiempo"
		tiempo += Gdx.graphics.getDeltaTime();
		// se instancia variable para almacenar las imagenes de las animaciones en bucle
		TextureRegion frameActual = animacion.getKeyFrame(tiempo, true);
		// se setea la animación en el sprite
		spr.setRegion(frameActual);
		// se dibuja la animación
		spr.draw(batch);
	}

	@Override
	protected int darPuntos() {
		// cantidad de puntos que retorna zombie al ser eliminado
		return 10;
	}
	
}
