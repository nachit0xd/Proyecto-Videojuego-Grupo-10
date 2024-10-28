package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota implements EntidadCaida{
    protected Rectangle posicion;
    protected Texture textura;

    public Gota(Texture textura) {
        this.textura = textura;
        this.posicion = new Rectangle();
        this.posicion.width = 64;
        this.posicion.height = 64;
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
    public abstract void efecto(Tarro tarro);

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.posicion.x, this.posicion.y);
    }
}
