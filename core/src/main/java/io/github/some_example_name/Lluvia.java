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

    // Constructor
    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaValiosa, Texture gotaMortal,
                  Texture poderEscudo, Texture poderVidaExtra, Sound ss, Sound dropValiosaSound,
                  Sound powerUpSound, Music mm) {
        rainMusic = mm;
        dropSound = ss;
        this.dropValiosaSound = dropValiosaSound;
        this.powerUpSound = powerUpSound;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaValiosa = gotaValiosa;
        this.gotaMortal = gotaMortal;
        this.poderEscudo = poderEscudo;
        this.poderVidaExtra = poderVidaExtra;
        this.startTime = TimeUtils.nanoTime();
    }

    // Inicializa la lluvia
    public void crear() {
        entidadesCaidas = new Array<>();
        crearEntidadCaida();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    // Genera una nueva entidad caída con estrategias
    private void crearEntidadCaida() {
        float x = MathUtils.random(0, 800 - 64); // Posición aleatoria en el eje X
        float y = 480; // Altura inicial

        GotaStrategy estrategia;
        Texture textura;

        int tipoEntidad = MathUtils.random(1, 1000);
        long elapsedTime = TimeUtils.timeSinceNanos(startTime);

        if (tipoEntidad <= 40) { // 4% de probabilidad
            estrategia = new GotaValiosaStrategy();
            textura = gotaValiosa;
        } else if (tipoEntidad <= 640) { // 60% de probabilidad
            estrategia = new GotaBuenaStrategy(dropSound);
            textura = gotaBuena;
        } else if (tipoEntidad <= 990) { // 35% de probabilidad
            estrategia = new GotaMalaStrategy();
            textura = gotaMala;
        } else if (tipoEntidad <= 995) { // 0.5% de probabilidad
            estrategia = new PoderEscudoStrategy();
            textura = poderEscudo;
        } else { // 0.5% de probabilidad
            estrategia = new PoderVidaExtraStrategy();
            textura = poderVidaExtra;
        }

        // Generar gotas mortales después de 30 segundos
        if (elapsedTime > 30_000_000_000L) {
            if (!mostrarAlerta) {
                mostrarAlerta = true;
                tiempoInicioAlerta = TimeUtils.nanoTime();
            }
            if (tipoEntidad > 995) {
                estrategia = new GotaMortalStrategy();
                textura = gotaMortal;
            }
        }

        // Crear entidad genérica con estrategia asignada
        GotaImpl entidad = new GotaImpl(x, y, estrategia, textura);
        entidadesCaidas.add(entidad);

        lastDropTime = TimeUtils.nanoTime();
    }

    // Actualiza el movimiento y colisiones de las entidades caídas
    public boolean actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000L) {
            crearEntidadCaida(); // Genera nuevas gotas cada 0.1 segundos
        }

        for (int i = 0; i < entidadesCaidas.size; i++) {
            EntidadCaida entidad = entidadesCaidas.get(i);
            entidad.setPosition(entidad.getPosicion().x, entidad.getPosicion().y - 300 * Gdx.graphics.getDeltaTime());

            // Elimina las gotas fuera de la pantalla
            if (entidad.getPosicion().y + 64 < 0) {
                entidadesCaidas.removeIndex(i);
                i--;
                continue;
            }

            // Maneja la colisión con el tarro
            if (entidad.getPosicion().overlaps(tarro.getArea())) {
                entidad.efecto(tarro); // Aplica el efecto al tarro
                entidadesCaidas.removeIndex(i);
                i--;

                // Verificar si el juego debe terminar
                if (tarro.getVidas() <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // Dibuja todas las gotas
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (EntidadCaida entidad : entidadesCaidas) {
            entidad.dibujar(batch);
        }
    }

    // Métodos auxiliares
    public boolean isMostrarAlerta() {
        return mostrarAlerta && TimeUtils.timeSinceNanos(tiempoInicioAlerta) < DURACION_ALERTA;
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
