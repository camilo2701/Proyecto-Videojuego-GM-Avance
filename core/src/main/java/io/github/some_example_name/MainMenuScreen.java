package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen {

	final GameLluviaMenu game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private final Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuMusic.mp3"));
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Texture cover;
	private Texture playButton;
	private Texture playButtonInactive;

	public MainMenuScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        // camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// se cargan las imagenes de la pantalla de menú principal además de los dos estados del
		// botón "play"
		cover = new Texture(Gdx.files.internal("cover.png"));
		playButton = new Texture(Gdx.files.internal("play.png"));
		playButtonInactive = new Texture(Gdx.files.internal("playIn.png"));
	}

	@Override
	public void render(float delta) {
		// limpia la pantalla con color negro
		ScreenUtils.clear(0f, 0f, 0f, 1);
		// actualizar matrices de la cámara
		camera.update();
		// actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// dibujar cover
		batch.draw(cover, 40, 140);
		
		// se define x para controlar posición del botón
		int x = 320 - buttonWidth / 2;
		// se condiciona para que cuando el puntero se posicione por encima del botón,
		// este cambie de estado
		if (Gdx.input.getX() < x + buttonWidth && Gdx.input.getX() > x && 480 - Gdx.input.getY() < 40 + buttonHeight && 480 - Gdx.input.getY() > 40) {
			batch.draw(playButtonInactive, 275, 40, buttonWidth, buttonHeight);
			if (Gdx.input.isTouched()) {
				game.setScreen(new TutorialScreen(game, menuMusic));
				dispose();
			}
		} else batch.draw(playButton, 275, 40, buttonWidth, buttonHeight);
		
		batch.end();

	}

	@Override
	public void show() {
		// se le da play a la música en bucle
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.10f);
		menuMusic.play();
	}

	@Override
	public void dispose() {
		// se le hace dispose() solo a los botones
		playButton.dispose();
		playButtonInactive.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
