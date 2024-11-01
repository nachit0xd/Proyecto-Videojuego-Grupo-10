package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final GameLluviaMenu game;
    private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private Tarro tarro;
	private Lluvia lluvia;
    private Texture background;
    private Texture circuloEscudo;


	//boolean activo = true;

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		  // load the images for the droplet and the bucket, 64x64 pixels each
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		  tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound);

	      // load the drop sound effect and the rain background "music"
         Texture gota = new Texture(Gdx.files.internal("drop.png"));
         Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
         Texture gotaValiosa = new Texture(Gdx.files.internal("dropGolden.png"));
         Texture gotaMortal = new Texture(Gdx.files.internal("dropDeath.png"));
         Texture poderEscudo = new Texture(Gdx.files.internal("shield.png"));
         Texture poderVidaExtra = new Texture(Gdx.files.internal("life.png"));
         circuloEscudo = new Texture(Gdx.files.internal("circleShield.png"));

         Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
         Sound dropValiosaSound = Gdx.audio.newSound(Gdx.files.internal("golden.ogg"));
         Sound powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerUp.ogg"));

	     Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
         lluvia = new Lluvia(gota, gotaMala, gotaValiosa, gotaMortal, poderEscudo, poderVidaExtra, dropSound, dropValiosaSound, powerUpSound, rainMusic);

	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del tarro
	      tarro.crear();

	      // creacion de la lluvia
	      lluvia.crear();
        this.background = new Texture(Gdx.files.internal("background2.png"));
	}

	@Override
	public void render(float delta) {
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//actualizar matrices de la cámara
		camera.update();
		//actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		//dibujar textos
		font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);

		if (!tarro.estaHerido()) {
			// movimiento del tarro desde teclado
	        tarro.actualizarMovimiento();
			// caida de la lluvia
	       if (!lluvia.actualizarMovimiento(tarro)) {
	    	  //actualizar HigherScore
	    	  if (game.getHigherScore()<tarro.getPuntos())
	    		  game.setHigherScore(tarro.getPuntos());
	    	  //ir a la ventana de finde juego y destruir la actual
	    	  game.setScreen(new GameOverScreen(game));
	    	  dispose();
	       }
		}

		tarro.dibujar(batch);
		lluvia.actualizarDibujoLluvia(batch);

        if (tarro.isEscudoActivo()) {
            float circleSize = 120; // Tamaño del círculo (ajústalo según sea necesario)
            batch.draw(circuloEscudo, tarro.getArea().x - (circleSize - tarro.getArea().width) / 2, tarro.getArea().y - (circleSize - tarro.getArea().height) / 2, circleSize, circleSize);
        }

        // Mostrar alerta si es necesario
        if (lluvia.isMostrarAlerta()) {
            String mensajeAlerta = "¡Cuidado con las gotas mortales!";
            float textoAncho = font.getRegion().getRegionWidth();
            float textoAlto = font.getRegion().getRegionHeight();
            float x = (camera.viewportWidth - textoAncho) / 2;
            float y = (camera.viewportHeight + textoAlto) / 2;
            font.draw(batch, mensajeAlerta, x, y);
        }

		batch.end();
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
      tarro.destruir();
      lluvia.destruir();
      background.dispose();
      circuloEscudo.dispose();
	}

}
