package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class GotaValiosa extends Gota {

    public GotaValiosa(Texture textura) {
        super(textura);
    }

    @Override
    public void efecto(Tarro tarro) {
        tarro.sumarPuntos(50);
    }
}
