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
        this.menuMusic = menuMusic;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		keys = new Texture(Gdx.files.internal("tutorialKeys.png"));
		intro = new Texture(Gdx.files.internal("introduction.png"));
		startButton = new Texture(Gdx.files.internal("start.png"));
		startButtonInactive = new Texture(Gdx.files.internal("startIn.png"));
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0f, 0.1f, 0f, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(intro, 40, 100);
		// dibujar texto explicativo para cada control
        batch.draw(keys, 20, 120);

		int x = 320 - buttonWidth / 2;
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
	public void show() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		menuMusic.dispose();
		startButton.dispose();
		startButtonInactive.dispose();
	}

}
