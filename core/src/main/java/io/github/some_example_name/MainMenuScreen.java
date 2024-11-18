package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class MainMenuScreen extends BaseScreen {

    private Texture background;

    public MainMenuScreen(final GameLluviaMenu game) {
        super(game);
        this.background = new Texture(Gdx.files.internal("background.png"));
    }

	@Override
	public void renderScreen(float delta) {
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.getFont().getData().setScale(2, 2);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(game.getFont(), "¡Bienvenido a Recolecta las Gotas!");
        float textWidth = layout.width;
        game.getFont().draw(batch, layout, (camera.viewportWidth - textWidth) / 2, camera.viewportHeight / 2 + 50);

        layout.setText(game.getFont(), "¡Toca en cualquier lugar para comenzar!");
        textWidth = layout.width;
        game.getFont().draw(batch, layout, (camera.viewportWidth - textWidth) / 2, camera.viewportHeight / 2 - 50);

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
        super.dispose();
        background.dispose();
	}

}
