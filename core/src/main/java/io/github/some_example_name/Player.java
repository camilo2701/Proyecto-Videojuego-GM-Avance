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


public class Player {

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
	   private Animation animation;
	   private float tiempo;
	   private TextureRegion[] regionAnimation;
	   private TextureRegion frameActual;
	  
	   
	   public Player(Sound ss) {
		   
		   bucket = new Sprite(new Texture(Gdx.files.internal("clear.png")));
		   sonidoHerido = ss;
		   
		   imagen = new Texture(Gdx.files.internal("cdcSpriteSheet64.png"));
		   
		   TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth()/4, imagen.getHeight());
		   
		   regionAnimation = new TextureRegion[4];
		   for (int i = 0 ; i < 4 ; i++) {
			   regionAnimation[i] = tmp[0][i];
		   }
		   animation = new Animation(1, regionAnimation);
		   tiempo = 0.5f;
		   texBala = new Texture(Gdx.files.internal("bullet.png"));
		   frameActual = regionAnimation[0];
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
		    return bucket.getBoundingRectangle();
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
		
		public void crear() {
			bucket.setCenterY(480 / 2 - 96 / 2);
			bucket.setX(20);
	   }
		
	   public void dañar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   public void dibujar(SpriteBatch batch, GameScreen game) {
		   tiempo += Gdx.graphics.getDeltaTime();
		   frameActual = (TextureRegion) animation.getKeyFrame(tiempo, true);
		   batch.draw(frameActual, bucket.getX(), bucket.getY());
		   if (!herido){
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
	   
}
