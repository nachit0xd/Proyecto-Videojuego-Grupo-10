package io.github.some_example_name;

public class PoderVidaExtraStrategy implements GotaStrategy {
    private static final int VIDAS_EXTRA = 1; // Cantidad de vidas adicionales

    public void aplicarEfecto(Tarro tarro) {
        tarro.incrementarVida(VIDAS_EXTRA); // Incrementa las vidas del tarro
    }
}
