package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota{
    public GotaMala(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.dañar();
    }
}
