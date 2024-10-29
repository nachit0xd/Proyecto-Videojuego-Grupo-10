package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class PoderVidaExtra extends Poder {

    public PoderVidaExtra(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.incrementarVida(1);
    }
}
