package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public interface Builder {
    void setTipo(String tipo);
    void setTextura(Texture textura);
    void setVelocidad(float velocidad);
    void setPuntuacion(int puntuacion);
    Producto build();
}
