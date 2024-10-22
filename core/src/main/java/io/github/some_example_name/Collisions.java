package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;

public interface Collisions {
	public Rectangle getArea();
	public void checkCollision(Collisions obj);
}
