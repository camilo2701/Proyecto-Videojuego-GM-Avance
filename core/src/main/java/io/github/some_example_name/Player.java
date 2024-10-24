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


public class Player implements Collisions, Areas{

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
		   // se carga la imagen de la bala
		   texBala = new Texture(Gdx.files.internal("bullet.png"));
		   // se carga la sheet animada del player
		   imagen = new Texture(Gdx.files.internal("playerSpriteSheet.png"));
		   // se hace un split para lograr diferenciar distintas animaciones
		   tmp = TextureRegion.split(imagen, imagen.getWidth()/4, imagen.getHeight()/2);
		   // se crea "frames" para almacenar cada imagen de la animación
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
		   // se asigna la primera imagen al player
		   player = new Sprite(frames[0]);
		   // se setea su posicion de aparición
		   player.setPosition(20, (480 / 2 - 92 / 2));
		   
	   }
	   
	   public int getVidas() {
		   // getter de vidas
		   return vida;
	   }
	   
	   public void recibirDaño() {
		   // disminuye 1 a la vida del player, activa el estado de herido
		   // y reproduce el sonido de éste
		   vida--;
		   herido = true;
		   tiempoHerido=tiempoHeridoMax;
		   sonidoHerido.play();
	   }
	   
	   public boolean estaVivo() {
		   // si no le quedan vidas, retorna false
		   return vida > 0;
	   }
	
	   public int getPuntos() {
		   // getter de puntos
		   return puntos;
	   }
		
	   public void setPuntos(int pp) {
		   // setter sumador de puntos
		   this.puntos+=pp;
	   }
	   
	   public void disparar(GameScreen game) {
		   // se setea estado disparando true para control de la animacion
		   disparando = true;
		   // se setea tiempoDisparo 0 ya que player acaba de dispara
		   tiempoDisparo = 0f;
		   // se crea la nueva bala en la posición correspondiente de acorde a la animación
		   Bala bala = new Bala(player.getX()+player.getWidth()/2-5,player.getY()+player.getHeight()-50,3,0,texBala);
		   // se agrega al arreglo de balas
		   game.agregarBala(bala);
		   // se activa el sonido del disparo
		   sonidoDisparo.play();
	   }
	   
	   public void dibujar(SpriteBatch batch, GameScreen game) {
		   // se almacena tiempo transcurrido en "tiempo"
		   tiempo += Gdx.graphics.getDeltaTime();
		   // se declara variable para almacenar las imagenes de las animaciones
		   TextureRegion frameActual;
		   // se verifica si el player está disparando
		   if (disparando) {
			   // se almacena tiempo transcurrido en "tiempoDisparo"
			   tiempoDisparo += Gdx.graphics.getDeltaTime();
			   // se obtienen los frames de la animación de disparo
			   frameActual = animacionShooting.getKeyFrame(tiempoDisparo, false);
			   // se setea la animación en el sprite
			   player.setRegion(frameActual);
			   // se dibuja la animación
			   player.draw(batch);
			   // si la animación terminó, se cambia el estado de disparando a false
			   if (animacionShooting.isAnimationFinished(tiempoDisparo)) disparando = false;
		   } else {
			   // si el player no está disparando, su animación será normal y en bucle
			   frameActual = animacion.getKeyFrame(tiempo, true);
			   if (!herido){
				   player.setRegion(frameActual);
			   } else {
				   // si el player está herido, su posición será alterada aleatoriamente en el
				   // eje Y, con un rango de 10 posiciones.
				   player.setY(player.getY()+MathUtils.random(-5,5));
				   tiempoHerido--;
				   if (tiempoHerido<=0) herido = false;
			   }
			   player.draw(batch);
		   }

	   } 

	   public void actualizarMovimiento(GameScreen game) { 
		   // movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.setY(player.getY() - velx * Gdx.graphics.getDeltaTime());
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.setY(player.getY() + velx * Gdx.graphics.getDeltaTime());
		   // accion de disparo
		   if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) disparar(game);
		   // que no se salga de los bordes izq y der
		   if(player.getY() < 0) player.setY(0);
		   if(player.getY() > 480 - 92) player.setY(480-92);
	   }
	    

	   public void destruir() {
		   // se le hace dispose() a la sheet animada
		   imagen.dispose();
	   }
	   
	   public boolean estaHerido() {
		   // retorna true si el player se encuentra en estado herido
		   return herido;
	   }
	   
	   @Override
	   public Rectangle getArea() {
		   // retorna el Rectangle del Sprite
		   return player.getBoundingRectangle();
	   }
	   
	   @Override
	   public void manejarColision(Collisions obj) {
		   // si player colisionó con un cualquier tipo de enemigo (zombie o zombieOP),
		   // el player recibe daño (poliformismo)
		   if (obj instanceof Enemigo) {
			   recibirDaño();
		   }
	   }
	   
}
