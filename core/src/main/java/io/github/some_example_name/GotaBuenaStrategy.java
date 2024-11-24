package io.github.some_example_name;
import com.badlogic.gdx.audio.Sound;

public class GotaBuenaStrategy implements GotaStrategy {
    private Sound dropSound;

    public GotaBuenaStrategy(Sound dropSound) {
        this.dropSound = dropSound;
    }

    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10); // Incrementa puntos
        dropSound.play(); // Reproduce sonido
    }
}
