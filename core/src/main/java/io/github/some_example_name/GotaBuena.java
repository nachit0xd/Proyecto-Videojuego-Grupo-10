package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends Gota{
    public GotaBuena(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }
}
