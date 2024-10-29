package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import interfaces.Collisions;

public class Zombie extends Enemigo {
	
	public Zombie(float x, float y) {
		// se setea la vida de zombie en 1 y se carga la textura a través del constructor
		// de la clase abstracta utilizando super
		super(x, y, new Texture(Gdx.files.internal("zomSpriteSheet.png")));
		this.vida = 1;
	}
	
	@Override
	public void manejarColision(Collisions obj) {
		// si zombie colisionó con una bala, el zombie recibe daño
		if (obj instanceof Bala) {
			recibirDaño();
		}
	}

	@Override
	public int darPuntos() {
		// cantidad de puntos que retorna zombie al ser eliminado
		return 10;
	}
	
}
