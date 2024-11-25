package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class BuilderConcreto implements Builder {
    private Producto producto;

    public BuilderConcreto() {
        producto = new Producto(); // Crear un nuevo Producto al iniciar
    }

    @Override
    public void setTipo(String tipo) {
        producto.setTipo(tipo);
    }

    @Override
    public void setTextura(Texture textura) {
        producto.setTextura(textura);
    }

    @Override
    public void setVelocidad(float velocidad) {
        producto.setVelocidad(velocidad);
    }

    @Override
    public void setPuntuacion(int puntuacion) {
        producto.setPuntuacion(puntuacion);
    }

    @Override
    public Producto build() {
        return producto; // Retorna el Producto configurado
    }
}
