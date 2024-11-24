package io.github.some_example_name;

public class GotaMalaStrategy implements GotaStrategy {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.dañar(); // Resta una vida
    }
}

