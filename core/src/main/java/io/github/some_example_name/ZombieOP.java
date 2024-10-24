package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ZombieOP extends Enemigo{

	public ZombieOP(float x, float y) {
		// se setea la vida de zombieOP en 2 y se carga la textura a través del constructor
		// de la clase abstracta utilizando super
		super(x, y, new Texture(Gdx.files.internal("zom2SpriteSheet.png")));
		this.vida = 2;
	}

	@Override
	public void manejarColision(Collisions obj) {
		// si zombie colisionó con una bala, el zombieOP recibe daño
		if (obj instanceof Bala) {
			recibirDaño();
		}
	}

	@Override
	public int darPuntos() {
		// cantidad de puntos que retorna zombieOP al ser eliminado
		return 30;
	}

}
