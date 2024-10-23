package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen {

	final GameLluviaMenu game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
	private final Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuMusic.mp3"));
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Texture playButton;
	private Texture playButtonInactive;

	public MainMenuScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		playButton = new Texture(Gdx.files.internal("play.png"));
		playButtonInactive = new Texture(Gdx.files.internal("playIn.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		
		/*font.getData().setScale(2, 2);
		font.draw(batch, "Bienvenido a Recolecta Gotas!!! ", 100, camera.viewportHeight/2+50);
		font.draw(batch, "Toca en cualquier lugar para comenzar!", 100, camera.viewportHeight/2-50);
		*/
		
		int x = 320 - buttonWidth / 2;
		if (Gdx.input.getX() < x + buttonWidth && Gdx.input.getX() > x && 480 - Gdx.input.getY() < 40 + buttonHeight && 480 - Gdx.input.getY() > 40) {
			batch.draw(playButtonInactive, 275, 40, buttonWidth, buttonHeight);
			if (Gdx.input.isTouched()) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
		} else {
			batch.draw(playButton, 275, 40, buttonWidth, buttonHeight);
		}
		

		batch.end();

		
	}

	@Override
	public void show() {
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.10f);
		menuMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		menuMusic.stop();
	}

	@Override
	public void dispose() {
		menuMusic.dispose();
	}

}
