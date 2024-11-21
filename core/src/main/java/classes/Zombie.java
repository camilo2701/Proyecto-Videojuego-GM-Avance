package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemigo {
	
	public Zombie(float x, float y) {
		// se setea la vida de zombie en 1, se carga la textura y estrategia de movimiento a
		// través del constructor de la clase abstracta utilizando super
		super(x, y, new Texture(Gdx.files.internal("zomSpriteSheet.png")), new MovimientoLineal());
		this.vida = 1;
	}

	@Override
	public int darPuntos() {
		// cantidad de puntos que retorna zombie al ser eliminado
		return 10;
	}
	
}
