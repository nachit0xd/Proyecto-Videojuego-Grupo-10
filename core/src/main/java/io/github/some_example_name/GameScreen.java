package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends BaseScreen {
    private Tarro tarro;
    private Lluvia lluvia;
    private Texture background;
    private Texture circuloEscudo;
    private GotaContext gotaContext; // Contexto para manejar las estrategias

    public GameScreen(final GameLluviaMenu game) {
        super(game);
        this.background = new Texture(Gdx.files.internal("background2.png"));
        this.circuloEscudo = new Texture(Gdx.files.internal("circleShield.png"));

        // Cargar sonidos e imágenes
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaValiosa = new Texture(Gdx.files.internal("dropGolden.png"));
        Texture gotaMortal = new Texture(Gdx.files.internal("dropDeath.png"));
        Texture poderEscudo = new Texture(Gdx.files.internal("shield.png"));
        Texture poderVidaExtra = new Texture(Gdx.files.internal("life.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Sound dropValiosaSound = Gdx.audio.newSound(Gdx.files.internal("golden.ogg"));
        Sound powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerUp.ogg"));

        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(gota, gotaMala, gotaValiosa, gotaMortal, poderEscudo, poderVidaExtra, dropSound, dropValiosaSound, powerUpSound, rainMusic);

        // Crear el tarro
        tarro.crear();

        // Crear la lluvia
        lluvia.crear();

        // Inicializar el contexto con una estrategia inicial (por ejemplo, GotaBuenaStrategy)
        gotaContext = new GotaContext(new GotaBuenaStrategy());
    }

    @Override
    protected void renderScreen(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);

        game.getFont().draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        game.getFont().draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        game.getFont().draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();
            
            // Aplicar la estrategia actual
            gotaContext.ejecutarEstrategia(tarro);

            if (!lluvia.actualizarMovimiento(tarro)) {
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);

        if (tarro.isEscudoActivo()) {
            float circleSize = 120;
            batch.draw(circuloEscudo, tarro.getArea().x - (circleSize - tarro.getArea().width) / 2, tarro.getArea().y - (circleSize - tarro.getArea().height) / 2, circleSize, circleSize);
        }

        if (lluvia.isMostrarAlerta()) {
            String mensajeAlerta = "¡Cuidado con las gotas mortales!";
            float textoAncho = game.getFont().getRegion().getRegionWidth();
            float textoAlto = game.getFont().getRegion().getRegionHeight();
            float x = (camera.viewportWidth - textoAncho) / 2;
            float y = (camera.viewportHeight + textoAlto) / 2;
            game.getFont().draw(batch, mensajeAlerta, x, y);
        }
    }

    public void cambiarEstrategia(String tipoEstrategia) {
        // Cambiar la estrategia del contexto según el tipo
        switch (tipoEstrategia) {
            case "BUENA":
                gotaContext.setStrategy(new GotaBuenaStrategy());
                break;
            case "MALA":
                gotaContext.setStrategy(new GotaMalaStrategy());
                break;
            case "VALIOSA":
                gotaContext.setStrategy(new GotaValiosaStrategy());
                break;
            case "MORTAL":
                gotaContext.setStrategy(new GotaMortalStrategy());
                break;
            default:
                System.out.println("Estrategia no válida");
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // continuar con sonido de lluvia
        lluvia.continuar();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        super.dispose();
        tarro.destruir();
        lluvia.destruir();
        background.dispose();
        circuloEscudo.dispose();
    }
}
