package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class PoderEscudo extends Poder{
    public PoderEscudo(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.activarEscudo(10); // activar escudo por 10 segundos
    }
}
