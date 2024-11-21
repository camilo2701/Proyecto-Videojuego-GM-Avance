package interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface MovementStrategy {
	public void movimiento(Sprite spr, float delta);
}
