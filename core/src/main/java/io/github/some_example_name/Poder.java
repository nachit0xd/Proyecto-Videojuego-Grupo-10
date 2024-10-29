package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Poder implements EntidadCaida{
    protected Rectangle posicion;
    protected Texture textura;

    public Poder(Texture textura) {
        this.textura = textura;
        posicion = new Rectangle();
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
    public abstract void efecto(Tarro tarro);

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, posicion.x, posicion.y);
    }
}
