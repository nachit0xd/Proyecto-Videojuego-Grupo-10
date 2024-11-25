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

    // Texturas de las gotas
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaValiosa;
    private Texture gotaMortal;

    // Texturas de los poderes
    private Texture poderEscudo;
    private Texture poderVidaExtra;

    // Sonidos
    private Sound dropSound;
    private Sound dropValiosaSound;
    private Sound powerUpSound;
    private Music rainMusic;

    // Variables para alertas
    private long startTime;
    private boolean mostrarAlerta = false;
    private long tiempoInicioAlerta;
    private static final long DURACION_ALERTA = 5_000_000_000L;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaValiosa, Texture gotaMortal,
                  Texture poderEscudo, Texture poderVidaExtra, Sound dropSound, Sound dropValiosaSound,
                  Sound powerUpSound, Music rainMusic) {
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaValiosa = gotaValiosa;
        this.gotaMortal = gotaMortal;
        this.poderEscudo = poderEscudo;
        this.poderVidaExtra = poderVidaExtra;
        this.dropSound = dropSound;
        this.dropValiosaSound = dropValiosaSound;
        this.powerUpSound = powerUpSound;
        this.rainMusic = rainMusic;
        this.startTime = TimeUtils.nanoTime();
    }

    public void crear() {
        entidadesCaidas = new Array<>();
        crearEntidadCaida();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearEntidadCaida() {
        Directora directora = new Directora();
        BuilderConcreto builder = new BuilderConcreto();
        directora.setBuilder(builder);

        Producto producto;
        EntidadCaida entidad; // Aquí instanciamos la entidad final
        int tipoEntidad = MathUtils.random(1, 100);
        long elapsedTime = TimeUtils.timeSinceNanos(startTime);

        if (elapsedTime > 30_000_000_000L) { // Después de 30 segundos
            if (!mostrarAlerta) {
                mostrarAlerta = true;
                tiempoInicioAlerta = TimeUtils.nanoTime();
            }

            if (tipoEntidad <= 5) { // 5% probabilidad para gotas mortales
                producto = directora.construir("Mortal", gotaMortal, 500, 0);
                entidad = new GotaMortal(producto.getTextura()); // Usamos textura configurada
            } else if (tipoEntidad <= 15) { // 10% probabilidad para gotas valiosas
                producto = directora.construir("Valiosa", gotaValiosa, 400, 50);
                entidad = new GotaValiosa(producto.getTextura());
            } else if (tipoEntidad <= 75) { // 60% probabilidad para gotas buenas
                producto = directora.construir("Buena", gotaBuena, 300, 10);
                entidad = new GotaBuena(producto.getTextura());
            } else if (tipoEntidad <= 95) { // 20% probabilidad para gotas malas
                producto = directora.construir("Mala", gotaMala, 200, -1);
                entidad = new GotaMala(producto.getTextura());
            } else if (tipoEntidad == 96) { // 1% probabilidad para poder escudo
                producto = directora.construir("Escudo", poderEscudo, 150, 0);
                entidad = new Poder(producto.getTextura(), new PoderEscudoStrategy());
            } else { // 1% probabilidad para poder vida extra
                producto = directora.construir("Vida Extra", poderVidaExtra, 150, 0);
                entidad = new Poder(producto.getTextura(), new PoderVidaExtraStrategy());
            }
        } else { // Antes de 30 segundos (sin gotas mortales)
            if (tipoEntidad <= 10) { // 10% probabilidad para gotas valiosas
                producto = directora.construir("Valiosa", gotaValiosa, 400, 50);
                entidad = new GotaValiosa(producto.getTextura());
            } else if (tipoEntidad <= 70) { // 60% probabilidad para gotas buenas
                producto = directora.construir("Buena", gotaBuena, 300, 10);
                entidad = new GotaBuena(producto.getTextura());
            } else if (tipoEntidad <= 95) { // 35% probabilidad para gotas malas
                producto = directora.construir("Mala", gotaMala, 200, -1);
                entidad = new GotaMala(producto.getTextura());
            } else if (tipoEntidad == 96) { // 1% probabilidad para poder escudo
                producto = directora.construir("Escudo", poderEscudo, 150, 0);
                entidad = new Poder(producto.getTextura(), new PoderEscudoStrategy());
            } else { // 1% probabilidad para poder vida extra
                producto = directora.construir("Vida Extra", poderVidaExtra, 150, 0);
                entidad = new Poder(producto.getTextura(), new PoderVidaExtraStrategy());
            }
        }

        // Configurar posición y agregar la entidad
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
                if (entidad instanceof GotaMortal && tarro.isEscudoActivo()) {
                    // Ignorar la gota mortal si el escudo está activo
                    entidadesCaidas.removeIndex(i);
                    continue;
                }

                entidad.efecto(tarro); // Aplicar el efecto normal
                if (entidad instanceof Poder) {
                    powerUpSound.play();
                } else if (entidad instanceof GotaValiosa) {
                    dropValiosaSound.play();
                } else if (entidad instanceof GotaBuena) {
                    dropSound.play();
                }

                entidadesCaidas.removeIndex(i);

                // Terminar el juego si las vidas son cero
                if (tarro.getVidas() <= 0) return false;
            }
        }
        return true;
    }
    
    //Métodos de alertas
    public boolean isMostrarAlerta() {
        return mostrarAlerta && TimeUtils.timeSinceNanos(tiempoInicioAlerta) < DURACION_ALERTA;
    }  
    
    //Actualiza el dibujo de las entidades caídas
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
