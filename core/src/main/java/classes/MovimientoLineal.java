package classes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import interfaces.MovementStrategy;

public class MovimientoLineal implements MovementStrategy{

	@Override
	public void movimiento(Sprite spr, float delta) {
		// set de nuevas posiciones respecto al movimiento horizontal
		spr.setX(spr.getX() - 600 * delta);
	}
	
}
