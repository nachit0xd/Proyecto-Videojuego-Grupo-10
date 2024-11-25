package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class Producto {
    private String tipo;
    private Texture textura;
    private float velocidad;
    private int puntuacion;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTipo() {
        return tipo;
    }

    public Texture getTextura() {
        return textura;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
