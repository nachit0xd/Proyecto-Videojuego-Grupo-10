package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Clase principal del juego
public class GameLluviaMenu extends Game {

    private static GameLluviaMenu instance;

    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;

    // Constructor privado para el patrón Singleton
    private GameLluviaMenu() {
    }

    // Método estático para obtener la instancia única
    public static GameLluviaMenu getInstance() {
        if (instance == null) {
            instance = new GameLluviaMenu();
        }
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Usa la fuente predeterminada de libGDX
        this.setScreen(new MainMenuScreen(this)); // Establece la pantalla inicial
    }

    @Override
    public void render() {
        super.render(); // Llama al renderizado de la pantalla actual
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHigherScore() {
        return higherScore;
    }

    public void setHigherScore(int higherScore) {
        this.higherScore = higherScore;
    }

}
