package classes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class Spawner {
	
	private int prob;
	private float lastSpawn;
	private float spawnInterval;
	
	public Spawner() {
		this.prob = 3;
		this.lastSpawn = 0f;
		this.spawnInterval = 0.8f;
	}
	
	public void zombieSpawner(ArrayList<Enemigo> zomList) {
		lastSpawn += Gdx.graphics.getDeltaTime();
		if (lastSpawn >= spawnInterval) {
			if (MathUtils.random(1, 10) < prob) {
				zomList.add(new Zombie(800, MathUtils.random(0, 480-96)));
			} else {
				zomList.add(new ZombieOP(800, MathUtils.random(0, 480-96)));
			}
			
			lastSpawn = 0f;
		}
	}
}
