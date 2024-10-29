package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<EntidadCaida> entidadesCaidas;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaValiosa;
    private Texture poderEscudo;
    private Texture poderVidaExtra;
    private Sound dropSound;
    private Sound dropValiosaSound;
    private Sound powerUpSound;
    private Music rainMusic;

	public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaValiosa, Texture poderEscudo, Texture poderVidaExtra, Sound ss, Sound dropValiosaSound, Sound powerUpSound, Music mm) {
		rainMusic = mm;
		dropSound = ss;
        this.dropValiosaSound = dropValiosaSound;
        this.powerUpSound = powerUpSound;
        this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
        this.gotaValiosa = gotaValiosa;
        this.poderEscudo = poderEscudo;
        this.poderVidaExtra = poderVidaExtra;
	}

	public void crear() {
        entidadesCaidas = new Array<>();
		crearEntidadCaida();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}

	private void crearEntidadCaida() {
        EntidadCaida entidad;
        int tipoEntidad = MathUtils.random(1, 1000);
        //Probabilidades de entidades
        if (tipoEntidad <= 40) {
            entidad = new GotaValiosa(gotaValiosa); // 4% de probabilidad
        } else if (tipoEntidad <= 640) {
            entidad = new GotaBuena(gotaBuena); // 60% de probabilidad
        } else if (tipoEntidad <= 990) {
            entidad = new GotaMala(gotaMala); // 35% de probabilidad
        } else if (tipoEntidad <= 995){
            entidad = new PoderEscudo(poderEscudo); // Poder escudo (0.5% de probabilidad)
        } else {
            entidad = new PoderVidaExtra(poderVidaExtra); // Poder vida extra (0.5% de probabilidad)
        }
        entidad.setPosition(MathUtils.random(0, 800 - 64), 480);
        entidadesCaidas.add(entidad);
        lastDropTime = TimeUtils.nanoTime();
	   }



   public boolean actualizarMovimiento(Tarro tarro) {
       if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearEntidadCaida();

       for (int i = 0; i < entidadesCaidas.size; i++) {
           EntidadCaida entidad = entidadesCaidas.get(i);
           entidad.getPosicion().y -= (entidad instanceof Poder ? 150 : 300) * Gdx.graphics.getDeltaTime();
           if (entidad.getPosicion().y + 64 < 0) {
               entidadesCaidas.removeIndex(i);
           }
           if (entidad.getPosicion().overlaps(tarro.getArea())) {
               entidad.efecto(tarro);
               if (entidad instanceof GotaBuena) {
                   dropSound.play();
               } else if (entidad instanceof GotaValiosa) {
                   dropValiosaSound.play();
               } else if (entidad instanceof Poder) {
                   powerUpSound.play();
               }
               entidadesCaidas.removeIndex(i);
               if (tarro.getVidas() <= 0) return false;
           }
       }
       return true;
   }

   public void actualizarDibujoLluvia(SpriteBatch batch) {
         for (EntidadCaida entidad : entidadesCaidas) {
              entidad.dibujar(batch);
         }
   }
   public void destruir() {
      dropSound.dispose();
      rainMusic.dispose();
   }
   public void pausar() {
	  rainMusic.stop();
   }
   public void continuar() {
	  rainMusic.play();
   }

}
