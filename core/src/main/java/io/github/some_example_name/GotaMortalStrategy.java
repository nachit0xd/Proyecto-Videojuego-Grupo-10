package io.github.some_example_name;

public class GotaMortalStrategy implements GotaStrategy {
    public void aplicarEfecto(Tarro tarro) {
        tarro.setVidas(0); // Reduce las vidas a 0 (game over)
    }
}
