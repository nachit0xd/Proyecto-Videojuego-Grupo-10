package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaMortal extends Gota {

    public GotaMortal(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.setVidas(0); // Mata al jugador inmediatamente
    }
}
