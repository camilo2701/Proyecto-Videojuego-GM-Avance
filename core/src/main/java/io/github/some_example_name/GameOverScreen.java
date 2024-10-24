package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	
	private final GameLluviaMenu game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private final Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameOver.ogg"));
	
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Texture gameOver;
	private Texture restartButton;
	private Texture restartButtonInactive;

	public GameOverScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        // camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// se cargan las imagenes de la pantalla de game over además de los dos estados del
		// botón "restart"
		gameOver = new Texture(Gdx.files.internal("gameOver.png"));
		restartButton = new Texture(Gdx.files.internal("restart.png"));
		restartButtonInactive = new Texture(Gdx.files.internal("restartIn.png"));
	}

	@Override
	public void render(float delta) {
		// limpia la pantalla con color verde oscuro
		ScreenUtils.clear(0f, 0.1f, 0f, 1);
		// actualizar matrices de la cámara
		camera.update();
		// actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// dibujar background
		batch.draw(gameOver, 40, 80);
		
		// se define x para controlar posición del botón
		int x = 320 - buttonWidth / 2;
		// se condiciona para que cuando el puntero se posicione por encima del botón,
		// este cambie de estado
		if (Gdx.input.getX() < x + buttonWidth && Gdx.input.getX() > x && 480 - Gdx.input.getY() < 40 + buttonHeight && 480 - Gdx.input.getY() > 40) {
			batch.draw(restartButtonInactive, 275, 40, buttonWidth, buttonHeight);
			if (Gdx.input.isTouched()) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
		} else {
			batch.draw(restartButton, 275, 40, buttonWidth, buttonHeight);
		}
		
		batch.end();

	}

	@Override
	public void show() {
		// cuando se muestra la pantalla gameOver el efecto de sonido
		// se reproduce
		gameOverSound.play();
	}

	@Override
	public void hide() {
		// cuando gameOver deja de ser la pantalla actual, el efecto
		// de sonido se detiene
		gameOverSound.stop();
	}

	@Override
	public void dispose() {
		// se le hace dispose() al efecto de sonido de la pantalla gameOver
		gameOverSound.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}


}
