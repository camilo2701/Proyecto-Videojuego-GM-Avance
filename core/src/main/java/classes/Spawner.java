package classes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import interfaces.EnemyFactory;

public class Spawner {
	
	private int prob;
	private float lastSpawn;
	private float spawnInterval;
	
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Enemigo> zombies = new ArrayList<>();
	
	// instancia para fabricar zombies (Abstract Factory)
	private EnemyFactory fabricaZombies;
	
	public Spawner() {
		this.prob = 3;
		this.lastSpawn = 0f;
		this.spawnInterval = 0.8f;
		this.fabricaZombies = new ZombiesFactory();
	}
	
	public boolean agregarBala(Bala bala) {
		return balas.add(bala)
;	}
	
	public void bulletSpawner(SpriteBatch batch) {
		for (Bala b : balas) b.draw(batch);
	}
	
	public void zombieSpawner() {
		// se usa lastSpawn para verificar el lapso de tiempo transcurrido
		lastSpawn += Gdx.graphics.getDeltaTime();
		// si ya pasó el tiempo suficiente, se spawnea otro zombie
		if (lastSpawn >= spawnInterval) {
			// alta probabilidad de que aparezca un zombieOP
			if (MathUtils.random(1, 10) < prob) {
				// se añade al arreglo de zombies la instancia fabricada de Zombie
				zombies.add(fabricaZombies.crearZombie());
			} else {
				// se añade al arreglo de zombies la instancia fabricada de ZombieOP
				zombies.add(fabricaZombies.crearZombieOP());
			}
			
			lastSpawn = 0f;
		}
	}
	
	public void checkPlayerCollision(SpriteBatch batch, Player player) {
		for (int i = 0 ; i < zombies.size() ; i++) {
			Enemigo zombie = zombies.get(i);
			zombie.actualizacionEnemigo(Gdx.graphics.getDeltaTime(), batch);
			//zombie.update(Gdx.graphics.getDeltaTime());
			//zombie.render(batch);
			// verificar si colisionó un zombie con el player
			if (zombie.getArea().overlaps(player.getArea())) {
				// el player recibe daño y el zombie se elimina
				player.manejarColision(zombie);
				zombies.remove(zombie);
				break;
			}
		}
	}
	
	public void checkBulletCollision(Player player) {
		for(int i = 0 ; i < balas.size() ; i++) {
			Bala bala = balas.get(i);
			bala.update();
			
			for (int k = 0 ; k < zombies.size() ; k++) {
				Enemigo zombie = zombies.get(k);
				// verificar si colisiono una bala con un zombie
				if (bala.getArea().overlaps(zombie.getArea())) {
					// el zombie recibe daño y se verifica si se murio
					bala.manejarColision(zombie);
					zombie.manejarColision(bala);
					// verificar si el zombie quedó vivo
					if (!zombie.estaVivo()) { 
						player.setPuntos(zombie.darPuntos());
						zombies.remove(zombie); // si murio, se remueve
					}
					// se remueve la bala
					balas.remove(bala);
					break;
				}
			}
			// verificar si se destruyeron por salir del area
			if (bala.isDestroyed()) {
				balas.remove(bala);
			}
		}
	}
}
