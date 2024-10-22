package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemigo {
	
	public Zombie(float x, float y) {
		super(x, y, new Texture(Gdx.files.internal("zombie.png")));
		this.vida = 1;
	}
	
	@Override
	public void checkCollision(Player tarro) {
		tarro.dañar();
	}
	
}
