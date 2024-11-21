package interfaces;

import classes.Enemigo;

public interface EnemyFactory {
	Enemigo crearZombie();
	Enemigo crearZombieOP();
}
