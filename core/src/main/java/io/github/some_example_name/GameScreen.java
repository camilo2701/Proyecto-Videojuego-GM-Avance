package io.github.some_example_name;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final GameLluviaMenu game;
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Player player;
	
	
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Enemigo> zombies = new ArrayList<>();
	private float lastSpawn = 0f;
	private float spawnInterval = 1.0f;
	private final Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del player
	      player = new Player();
	      	      
	}

	@Override
	public void render(float delta) {
		//limpia la pantalla con color gris claro.
		ScreenUtils.clear(0.7529f, 0.7529f, 0.7529f, 1);
		//actualizar matrices de la cámara
		camera.update();
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//dibujar textos
		font.draw(batch, "Gotas totales: " + player.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + player.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);
		
		if (!player.estaHerido()) {
			for(int i = 0 ; i < balas.size() ; i++) {
				Bala bala = balas.get(i);
				bala.update();
				
				for (int k = 0 ; k < zombies.size() ; k++) {
					Enemigo zombie = zombies.get(k);
					if (bala.getArea().overlaps(zombie.getArea())) {
						// la gota recibe daño y se verifica si se murio
						bala.manejarColision(zombie);
						zombie.manejarColision(bala);
						
						if (!zombie.estaVivo()) { 
							player.sumarPuntos(zombie.darPuntos());
							zombies.remove(zombie); // si murio, se remueve
						}
						// se remueve la bala y se reduce el indice de balas
						balas.remove(bala);
						
						break;
					}
					
				}
				// verificar si se destruyeron por salir del area
				if (bala.isDestroyed()) {
					balas.remove(bala);
					i--;
				}
				
			}
			// movimiento del tarro desde teclado
			player.actualizarMovimiento();    
	        
	        if (!player.estaVivo()) {
	        	if (game.getHigherScore() < player.getPuntos()) {
	        		game.setHigherScore(player.getPuntos());
	        	}
	        	game.setScreen(new GameOverScreen(game));
	        	dispose();
	        }
		}
		
		lastSpawn += Gdx.graphics.getDeltaTime();
		if (lastSpawn >= spawnInterval) {
			if (MathUtils.random(1, 10) < 3) {
				zombies.add(new Zombie(800, MathUtils.random(0, 480-96)));
			} else {
				zombies.add(new ZombieOP(800, MathUtils.random(0, 480-96)));
			}
			
			lastSpawn = 0f;
		}
		
		for (int i = 0 ; i < zombies.size() ; i++) {
			Enemigo zombie = zombies.get(i);
			zombie.update(Gdx.graphics.getDeltaTime());
			zombie.render(batch);
			if (zombie.getArea().overlaps(player.getArea())) {
				player.manejarColision(zombie);
				zombies.remove(zombie);
				break;
			}
		}
		
		for (Bala b : balas) {
			b.draw(batch);
		}
		
		player.dibujar(batch, this);
		
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PausaScreen(game, this));
		}
		
		batch.end();
	}
	
	public boolean agregarBala(Bala bala) {
		return balas.add(bala)
;	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
		gameMusic.setLooping(true);
	    gameMusic.setVolume(0.10f);
	    gameMusic.play();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		gameMusic.stop();
		game.setScreen(new PausaScreen(game, this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	  player.destruir();
      gameMusic.dispose();
	}

}
