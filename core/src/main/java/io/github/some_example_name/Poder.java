package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Poder implements EntidadCaida {
    protected Rectangle posicion;
    protected Texture textura;
    private PoderStrategy strategy; // Estrategia para definir el efecto del poder

    public Poder(Texture textura, PoderStrategy strategy) {
        this.textura = textura;
        this.strategy = strategy;
        this.posicion = new Rectangle();
        this.posicion.width = 48;
        this.posicion.height = 48;
    }

    @Override
    public void setPosition(float x, float y) {
        this.posicion.x = x;
        this.posicion.y = y;
    }

    @Override
    public Rectangle getPosicion() {
        return this.posicion;
    }

    @Override
    public void efecto(Tarro tarro) {
        if (strategy != null) {
            strategy.aplicarEfecto(tarro); // Delegar el efecto a la estrategia
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, posicion.x, posicion.y);
    }
}
