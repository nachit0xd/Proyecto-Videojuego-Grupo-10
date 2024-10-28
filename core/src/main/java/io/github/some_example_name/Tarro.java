package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Tarro {
	   private Rectangle bucket;
	   private Texture bucketImage;
	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
       private boolean escudoActivo = false;
       private float tiempoEscudoRestante = 0;


	   public Tarro(Texture tex, Sound ss) {
		   bucketImage = tex;
		   sonidoHerido = ss;
	   }

		public int getVidas() {
			return vidas;
		}

		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return bucket;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}


	   public void crear() {
		      bucket = new Rectangle();
		      bucket.x = 800 / 2 - 64 / 2;
		      bucket.y = 20;
		      bucket.width = 64;
		      bucket.height = 64;
	   }
	   public void dañar() {
           if (!escudoActivo){
               vidas--;
               herido = true;
               tiempoHerido=tiempoHeridoMax;
               sonidoHerido.play();
           }
	   }
	   public void dibujar(SpriteBatch batch) {
		 if (!herido)
		   batch.draw(bucketImage, bucket.x, bucket.y);
		 else {

		   batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   }


	   public void actualizarMovimiento() {
           // Actualizar el tiempo del escudo
           if (escudoActivo) {
               tiempoEscudoRestante -= Gdx.graphics.getDeltaTime();
               if (tiempoEscudoRestante <= 0) {
                   escudoActivo = false;
               }
           }
           // movimiento desde mouse/touch
		   /*if(Gdx.input.isTouched()) {
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      bucket.x = touchPos.x - 64 / 2;
			}*/
		   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
		   // que no se salga de los bordes izq y der
		   if(bucket.x < 0) bucket.x = 0;
		   if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	   }

    public void activarEscudo(float duracion) {
        escudoActivo = true;
        tiempoEscudoRestante = duracion;
    }

    public boolean isEscudoActivo() {
        return escudoActivo;
    }


	public void destruir() {
		    bucketImage.dispose();
	   }

   public boolean estaHerido() {
	   return herido;
   }

}
