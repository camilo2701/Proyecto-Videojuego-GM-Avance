package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ZombieOP extends Enemigo{

	public ZombieOP(float x, float y) {
		// se setea la vida de zombieOP en 2, se carga la textura y estrategia de movimiento a
		// través del constructor de la clase abstracta utilizando super
		super(x, y, new Texture(Gdx.files.internal("zom2SpriteSheet.png")), new MovimientoEspecial());
		this.vida = 2;
	}

	@Override
	public int darPuntos() {
		// cantidad de puntos que retorna zombieOP al ser eliminado
		return 30;
	}

}
