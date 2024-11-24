package io.github.some_example_name;

public class GotaMalaStrategy implements GotaStrategy {
    public void aplicarEfecto(Tarro tarro) {
        tarro.perderVida(); // Reduce la vida del jugador
    }
}
