package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class Directora {
    private Builder builder;

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public Producto construir(String tipo, Texture textura, float velocidad, int puntuacion) {
        builder.setTipo(tipo);
        builder.setTextura(textura);
        builder.setVelocidad(velocidad);
        builder.setPuntuacion(puntuacion);
        return builder.build();
    }
}
