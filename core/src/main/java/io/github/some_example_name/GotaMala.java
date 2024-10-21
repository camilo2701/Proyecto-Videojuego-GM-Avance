package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends LluviaAbstract{

	public GotaMala(float x, float y, Texture tex) {
		super(x, y, tex);
	}

	@Override
	public void checkCollision(Player tarro) {
		tarro.dañar();
	}

}
