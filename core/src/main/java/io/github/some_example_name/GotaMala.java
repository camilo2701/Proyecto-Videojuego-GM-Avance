package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends LluviaAbstract{

	public GotaMala(float x, float y) {
		super(x, y, new Texture(Gdx.files.internal("dropBad.png")));
		this.vida = 2;
	}

	@Override
	public void checkCollision(Player tarro) {
		tarro.dañar();
	}

}
