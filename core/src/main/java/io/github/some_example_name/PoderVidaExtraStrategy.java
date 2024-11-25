package io.github.some_example_name;

public class PoderVidaExtraStrategy implements PoderStrategy {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.incrementarVida(1); // Incrementa una vida al tarro
    }
}
