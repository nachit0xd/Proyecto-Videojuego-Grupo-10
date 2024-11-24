package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GotaImpl implements EntidadCaida {
    private Rectangle posicion; // Posición y tamaño de la gota
    private GotaStrategy estrategia; // Estrategia asignada a la gota
    private Texture textura; // Textura para dibujar la gota

    // Constructor para inicializar la posición, estrategia y textura
    public GotaImpl(float x, float y, GotaStrategy estrategia, Texture textura) {
        this.posicion = new Rectangle(x, y, 64, 64); // Tamaño de la gota
        this.estrategia = estrategia;
        this.textura = textura;
    }

    @Override
    public void setPosition(float x, float y) {
        posicion.x = x;
        posicion.y = y;
    }

    @Override
    public Rectangle getPosicion() {
        return posicion;
    }

    @Override
    public void efecto(Tarro tarro) {
        estrategia.aplicarEfecto(tarro); // Delegar el efecto a la estrategia
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, posicion.x, posicion.y); // Dibujar la textura en pantalla
    }
}
