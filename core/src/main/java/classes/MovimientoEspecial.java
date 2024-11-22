package classes;

import com.badlogic.gdx.graphics.g2d.Sprite;

import interfaces.MovementStrategy;

public class MovimientoEspecial implements MovementStrategy{
    private float time = 0;
    
	@Override
	public void movimiento(Sprite spr, float delta) {
		// se almacena tiempo transcurrido
        time += delta;
        // movimiento horizontal
        float newX = spr.getX() - 450 * delta;
        // movimiento especial
        float newY = (float) (spr.getY() + Math.sin(15 * time));
        // set de nueva posición
        spr.setPosition(newX, newY);
    }
}
