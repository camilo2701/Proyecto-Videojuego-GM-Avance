package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen{
	
	final GameLluviaMenu game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Music menuMusic;
	private Texture keys;
	private Texture intro;
	private Texture startButton;
	private Texture startButtonInactive;
	
	public TutorialScreen(final GameLluviaMenu game, Music menuMusic) {
		this.game = game;
        this.batch = game.getBatch();
        // se conserva la música de la pantalla MainMenu
        this.menuMusic = menuMusic;
        // camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// se cargan las imagenes de la pantalla de tutorial además de los dos estados del
		// botón "start"
		keys = new Texture(Gdx.files.internal("tutorialKeys.png"));
		intro = new Texture(Gdx.files.internal("introduction.png"));
		startButton = new Texture(Gdx.files.internal("start.png"));
		startButtonInactive = new Texture(Gdx.files.internal("startIn.png"));
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
		// dibujar texto explicativo para cada control
		batch.draw(intro, 40, 100);
        batch.draw(keys, 20, 120);

        // se define x para controlar posición del botón
		int x = 320 - buttonWidth / 2;
		// se condiciona para que cuando el puntero se posicione por encima del botón,
		// este cambie de estado
		if (Gdx.input.getX() < x + buttonWidth && Gdx.input.getX() > x && 480 - Gdx.input.getY() < 40 + buttonHeight && 480 - Gdx.input.getY() > 40) {
			batch.draw(startButtonInactive, 275, 40, buttonWidth, buttonHeight);
			if (Gdx.input.justTouched()) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
		} else batch.draw(startButton, 275, 40, buttonWidth, buttonHeight);
		
		batch.end();
	}
	
	@Override
	public void dispose() {
		// se le hace dispose() a la música y botones
		menuMusic.dispose();
		startButton.dispose();
		startButtonInactive.dispose();
	}
	
	@Override
	public void show() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
