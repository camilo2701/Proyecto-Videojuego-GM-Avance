package io.github.some_example_name;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	private ArrayList<LluviaAbstract> gotas = new ArrayList<>();
	private float lastSpawn = 0f;
	private float spawnInterval = 1.0f;
	Texture gotaTex = new Texture(Gdx.files.internal("drop.png"));
	Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
	private Music rainMusic;
	private Sound dropSound;

	   
	//boolean activo = true;

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		  // load the images for the droplet and the bucket, 64x64 pixels each 	     
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		  player = new Player(hurtSound);
         
	      // load the drop sound effect and the rain background "music" 
         
         
         
         dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        
	     rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	     
	     rainMusic.setLooping(true);
	     rainMusic.play();
	     
	     
	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del tarro
	      //tarro.crear();
	      	      
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
				
				for (LluviaAbstract gota : gotas) {
					if (bala.getArea().overlaps(gota.getArea())) {
						// suma puntos si le pega
						player.sumarPuntos(10);
						// la gota recibe daño y se verifica si se murio
						gota.recibirDaño();
						if (!gota.estaVivo()) {
							gotas.remove(gota); // si murio, se remueve
						}
						// se remueve la bala y se reduce el indice de balas
						balas.remove(bala);
						i--;
						
						
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
	        
	        if (player.getVidas() <= 0) {
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
				gotas.add(new GotaBuena(800, MathUtils.random(0, 480-64)));
			} else {
				gotas.add(new GotaMala(800, MathUtils.random(0, 480-64)));
			}
			
			lastSpawn = 0f;
		}
		
		for (int i = 0 ; i < gotas.size() ; i++) {
			LluviaAbstract gota = gotas.get(i);
			gota.update(Gdx.graphics.getDeltaTime());
			gota.render(batch);
		}
		
		checkCollision(player);
		
		
		
		for (Bala b : balas) {
			b.draw(batch);
		}
		
		player.dibujar(batch, this);
		
		
		batch.end();
	}
	
	public boolean agregarBala(Bala bala) {
		return balas.add(bala)
;	}
	
	public void checkCollision(Player tarro) {
		for (LluviaAbstract gota : gotas) {
			if (gota.getArea().overlaps(tarro.getArea())) {
				gota.checkCollision(tarro);
				gotas.remove(gota);
				break;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	  // continuar con sonido de lluvia
	  rainMusic.play();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		rainMusic.stop();
		game.setScreen(new PausaScreen(game, this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	  player.destruir();
      rainMusic.dispose();
      dropSound.dispose();
	}

}
