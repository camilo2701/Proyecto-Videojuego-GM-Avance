package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Player implements Collisions{

	   
	   private int vida = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private boolean disparando = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   private final Sound sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
	   private final Sound sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("gunshot.ogg"));
	   
	   private Sprite player;
	   private Texture texBala;
	   private Texture imagen;
	   private TextureRegion[][] tmp;
	   private Animation<TextureRegion> animacion;
	   private Animation<TextureRegion> animacionShooting;
	   private float tiempo = 0f;
	   private float tiempoDisparo = 0f;
	  
	   
	   public Player() {
		   
		   imagen = new Texture(Gdx.files.internal("playerSpriteSheet.png"));
		    
		   tmp = TextureRegion.split(imagen, imagen.getWidth()/4, imagen.getHeight()/2);
		   TextureRegion[] frames = new TextureRegion[4];
	       for (int i = 0; i < 4; i++) {
	    	   TextureRegion frameActual = tmp[0][i];
	    	   frames[i] = frameActual;
	       }
	       
	       TextureRegion[] framesShooting = new TextureRegion[4];
	       for (int i = 0; i < 4; i++) {
	    	   TextureRegion frameActual = tmp[1][i];
	    	   framesShooting[i] = frameActual;
	       }
	       
	       animacion = new Animation<TextureRegion>(0.5f, frames);
	       animacionShooting = new Animation<TextureRegion>(0.1f, framesShooting);
		   
		   player = new Sprite(frames[0]);
		   player.setPosition(20, (480 / 2 - 92 / 2));
		   texBala = new Texture(Gdx.files.internal("bullet.png"));
	   }
	   
	   public int getVidas() {
		   return vida;
	   }
	   
	   public void dañar() {
		   vida--;
		   herido = true;
		   tiempoHerido=tiempoHeridoMax;
		   sonidoHerido.play();
	   }
	   
	   public boolean estaVivo() {
		   return vida > 0;
	   }
	
	   public int getPuntos() {
		   return puntos;
	   }
		
	   public void sumarPuntos(int pp) {
		   puntos+=pp;
	   }
	   
	   public void disparar(GameScreen game) {
		   disparando = true;
		   tiempoDisparo = 0f;
		   Bala bala = new Bala(player.getX()+player.getWidth()/2-5,player.getY()+player.getHeight()-50,3,0,texBala);
		   game.agregarBala(bala);
		   sonidoDisparo.play();
	   }
	   
	   public void dibujar(SpriteBatch batch, GameScreen game) {
		   tiempo += Gdx.graphics.getDeltaTime();
		   TextureRegion frameActual;
		   
		   if (disparando) {
			   tiempoDisparo += Gdx.graphics.getDeltaTime();
			   frameActual = animacionShooting.getKeyFrame(tiempoDisparo, false);
			   player.setRegion(frameActual);
			   player.draw(batch);
			   
			   if (animacionShooting.isAnimationFinished(tiempoDisparo)) disparando = false;
		   } else {
			   frameActual = animacion.getKeyFrame(tiempo, true);
			   if (!herido){
				   player.setRegion(frameActual);
			   } else {
				   player.setY(player.getY()+MathUtils.random(-5,5));
				   tiempoHerido--;
				   if (tiempoHerido<=0) herido = false;
			   }
			   player.draw(batch);
		   }

		   if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			   frameActual = animacionShooting.getKeyFrame(tiempo, false);
			   player.setRegion(frameActual);
			   disparar(game);
		   }
		   
	   } 

	   public void actualizarMovimiento() { 
		   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.setY(player.getY() - velx * Gdx.graphics.getDeltaTime());
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.setY(player.getY() + velx * Gdx.graphics.getDeltaTime());
		   // que no se salga de los bordes izq y der
		   if(player.getY() < 0) player.setY(0);
		   if(player.getY() > 480 - 92) player.setY(480-92);
	   }
	    

	   public void destruir() {
		   imagen.dispose();
	   }
	   
	   public boolean estaHerido() {
		   return herido;
	   }
	   
	   @Override
	   public Rectangle getArea() {
		   return player.getBoundingRectangle();
	   }
	   
	   @Override
	   public void manejarColision(Collisions obj) {
		   if (obj instanceof Enemigo) {
			   dañar();
		   }
	   }
	   
}
