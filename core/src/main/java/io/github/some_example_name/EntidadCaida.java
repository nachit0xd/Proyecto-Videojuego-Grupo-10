package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface EntidadCaida {
    void setPosition(float x, float y);
    Rectangle getPosicion();
    void efecto(Tarro tarro);
    void dibujar(SpriteBatch batch);
}
