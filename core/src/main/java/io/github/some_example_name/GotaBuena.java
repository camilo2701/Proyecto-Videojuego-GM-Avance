package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends LluviaAbstract {
	
	public GotaBuena(float x, float y, Texture tex) {
		super(x, y, tex);
	}
	
	@Override
	public void checkCollision(Player tarro) {
		tarro.sumarPuntos(500);
	}
	
}
