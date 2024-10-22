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

	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   private Sprite bucket;
	   private Texture texBala;
	   private Texture imagen;
	   private TextureRegion[][] tmp;
	   private Animation<TextureRegion> animacion;
	   private float tiempo = 0f;
	  
	   
	   public Player(Sound ss) {
		   
		   sonidoHerido = ss;
		   
		   imagen = new Texture(Gdx.files.internal("cdcSpriteSheet64.png"));
		   
		   tmp = TextureRegion.split(imagen, imagen.getWidth()/4, imagen.getHeight());
		   TextureRegion[] frames = new TextureRegion[4];
	       for (int i = 0; i < 4; i++) {
	    	   TextureRegion frameActual = tmp[0][i];
	    	   frames[i] = frameActual;
	       }
	       
	       /*TextureRegion[] framesShooting = new TextureRegion[4];
	       for (int i = 0; i < 4; i++) {
	    	   TextureRegion frameActual = tmp[0][i];
	    	   frames[i] = frameActual;
	       }*/
	       
	       animacion = new Animation<TextureRegion>(0.5f, frames);
	       
	       /*animacion = new Animation<TextureRegion>(0.5f, frames);*/
		   
		   bucket = new Sprite(frames[0]);
		   bucket.setPosition(20, (480 / 2 - 96 / 2));
		   texBala = new Texture(Gdx.files.internal("bullet.png"));
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
		
		
	   public void dañar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   public void dibujar(SpriteBatch batch, GameScreen game) {
		   TextureRegion frameActual = animacion.getKeyFrame(tiempo, true);
		   tiempo += Gdx.graphics.getDeltaTime();
		   if (!herido){
			   bucket.setRegion(frameActual);
			   bucket.draw(batch);
		   }else{
			   bucket.setY(bucket.getY()+MathUtils.random(-5,5));
			   bucket.draw(batch);
			   tiempoHerido--;
			   if (tiempoHerido<=0) herido = false;
		   }
		   if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			   Bala bala = new Bala(bucket.getX()+bucket.getWidth()/2-5,bucket.getY()+bucket.getHeight()-50,3,0,texBala);
			   game.agregarBala(bala);
		   }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   // movimiento desde mouse/touch
		   /*if(Gdx.input.isTouched()) {
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      bucket.x = touchPos.x - 64 / 2;
			}*/
		   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.setY(bucket.getY() - velx * Gdx.graphics.getDeltaTime());
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.setY(bucket.getY() + velx * Gdx.graphics.getDeltaTime());
		   // que no se salga de los bordes izq y der
		   if(bucket.getY() < 0) bucket.setY(0);
		   if(bucket.getY() > 480 - 96) bucket.setY(480-96);
	   }
	    

	public void destruir() {
		    imagen.dispose();
	   }
	
   public boolean estaHerido() {
	   return herido;
   }
   
   	public Rectangle getArea() {
	    return bucket.getBoundingRectangle();
	}

	@Override
	public void checkCollision(Collisions obj) {
		if (obj instanceof Enemigo) {
			dañar();
		}
	}
	   
}
