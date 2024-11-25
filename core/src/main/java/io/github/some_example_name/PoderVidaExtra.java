package io.github.some_example_name;

public class PoderVidaExtra implements Poder {
    @Override
    public void efecto(Tarro tarro) {
        tarro.incrementarVida(1); // Incrementa una vida al tarro
    }
}
