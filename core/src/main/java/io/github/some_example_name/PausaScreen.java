package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class PausaScreen implements Screen {

	private final GameLluviaMenu game;
	private GameScreen juego;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Texture skull;
	private Texture resumeButton;
	private Texture resumeButtonInactive;

	public PausaScreen (final GameLluviaMenu game, GameScreen juego) {
		this.game = game;
        this.juego = juego;  
        this.batch = game.getBatch();
        // camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// se cargan las imagenes de la pantalla de pausa además de los dos estados del
		// botón "resume"
		skull = new Texture(Gdx.files.internal("skull.png"));
		resumeButton = new Texture(Gdx.files.internal("resume.png"));
		resumeButtonInactive = new Texture(Gdx.files.internal("resumeIn.png"));
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
		batch.draw(skull, 67, 80);
		
		// se define x para controlar posición del botón
		int x = 320 - buttonWidth / 2;
		// se condiciona para que cuando el puntero se posicione por encima del botón,
		// este cambie de estado
		if (Gdx.input.getX() < x + buttonWidth && Gdx.input.getX() > x && 480 - Gdx.input.getY() < 40 + buttonHeight && 480 - Gdx.input.getY() > 40) {
			batch.draw(resumeButtonInactive, 275, 40, buttonWidth, buttonHeight);
			if (Gdx.input.isTouched()) {
				game.setScreen(juego);
				dispose();
			}
		} else {
			batch.draw(resumeButton, 275, 40, buttonWidth, buttonHeight);
		}
		
		batch.end();

		
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

	@Override
	public void dispose() {}

}

