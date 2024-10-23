package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class PausaScreen implements Screen {

	private final GameLluviaMenu game;
	private GameScreen juego;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private OrthographicCamera camera;
	private final int buttonHeight = 65;
	private final int buttonWidth = 250;
	private Texture resumeButton;
	private Texture resumeButtonInactive;

	public PausaScreen (final GameLluviaMenu game, GameScreen juego) {
		this.game = game;
        this.juego = juego;  
        this.batch = game.getBatch();
        this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		resumeButton = new Texture(Gdx.files.internal("resume.png"));
		resumeButtonInactive = new Texture(Gdx.files.internal("resumeIn.png"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 1.0f, 0.5f);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		
		/*font.draw(batch, "Juego en Pausa ", 100, 150);
		font.draw(batch, "Toca en cualquier lado para continuar !!!", 100, 100);*/
		
		int x = 320 - buttonWidth / 2;
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
		// TODO Auto-generated method stub
		
	}

}

