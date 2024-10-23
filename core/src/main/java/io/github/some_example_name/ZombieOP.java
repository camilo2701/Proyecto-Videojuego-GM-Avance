package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ZombieOP extends Enemigo{

	public ZombieOP(float x, float y) {
		super(x, y, new Texture(Gdx.files.internal("zom2SpriteSheet.png")));
		this.vida = 2;
	}

	@Override
	public void manejarColision(Collisions obj) {
		if (obj instanceof Bala) {
			recibirDaño();
		}
	}

	@Override
	public int darPuntos() {
		return 30;
	}

}
