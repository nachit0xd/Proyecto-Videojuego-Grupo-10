package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	private final GameLluviaMenu game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
    private Texture background;

	public GameOverScreen(final GameLluviaMenu game) {
		this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.background = new Texture(Gdx.files.internal("background.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        font.getData().setScale(2, 2);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "GAME OVER");
        float textWidth = layout.width;
        font.draw(batch, layout, (camera.viewportWidth - textWidth) / 2, camera.viewportHeight / 2 + 50);

        layout.setText(font, "¡Toca en cualquier lugar para reiniciar!");
        textWidth = layout.width;
        font.draw(batch, layout, (camera.viewportWidth - textWidth) / 2, camera.viewportHeight / 2 - 50);
		batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        background.dispose();
	}

}
