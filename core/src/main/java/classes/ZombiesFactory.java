package classes;

import com.badlogic.gdx.math.MathUtils;

import interfaces.EnemyFactory;

public class ZombiesFactory implements EnemyFactory {

	@Override
	public Enemigo crearZombie() {
		// creación de Zombie
		return new Zombie(800, MathUtils.random(0, 480-96));
	}

	@Override
	public Enemigo crearZombieOP() {
		// creación de ZombieOP
		return new ZombieOP(800, MathUtils.random(0, 480-96));
	}
	
}
