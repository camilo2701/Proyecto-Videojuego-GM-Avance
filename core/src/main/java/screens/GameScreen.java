package screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import classes.Bala;
import classes.Enemigo;
import classes.Player;
import classes.Spawner;

public class GameScreen implements Screen {
	
	final GameLluviaMenu game;
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Player player;
	private Spawner spawner;
	
	private ArrayList<Bala> balas = new ArrayList<>();
	private ArrayList<Enemigo> zombies = new ArrayList<>();

	private final Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
	private Texture background;

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        // batch
        batch = new SpriteBatch();
        // background
        background = new Texture(Gdx.files.internal("background.png"));
        // creacion del player
        player = new Player();   
        // creacion del spawner
        spawner = new Spawner();
	}

	@Override
	public void render(float delta) {
		// limpia la pantalla con color gris claro.
		ScreenUtils.clear(0.7529f, 0.7529f, 0.7529f, 1);
		// actualizar matrices de la cámara
		camera.update();
		// actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// dibujar background
		batch.draw(background, 0, 0);
		// dibujar textos
		font.draw(batch, "Puntos : " + player.getPuntos(), 25, 475);
		font.draw(batch, "Vidas : " + player.getVidas(), 720, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);
		
		if (!player.estaHerido()) {
			// verificar si existen colisiones entre balas y zombies
			this.checkBulletCollision();
			// movimiento del player desde teclado
			player.actualizarMovimiento(this);    
			// si el player perdió, se setea Highscore en caso de corresponder
	        if (!player.estaVivo()) {
	        	if (game.getHigherScore() < player.getPuntos()) {
	        		game.setHigherScore(player.getPuntos());
	        	}
	        	game.setScreen(new GameOverScreen(game));
	        	dispose();
	        }
		}
		
		// spawn de zombies aleatorios
		spawner.zombieSpawner(zombies);
		// verificar si existen colisiones entre zombies y el player
		this.checkPlayerCollision();
		// si existen balas disparadas, se dibujan
		for (Bala b : balas) b.draw(batch);
		// se dibuja al player, actualizando su animacion
		player.dibujar(batch, this);
		
		// verificar si se presionó la tecla de pausa (ESC)
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PausaScreen(game, this));
		}
		
		batch.end();
	}
	
	public void checkPlayerCollision() {
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
	}
	
	public void checkBulletCollision() {
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
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	  player.destruir();
	  background.dispose();
      gameMusic.dispose();
	}

}
