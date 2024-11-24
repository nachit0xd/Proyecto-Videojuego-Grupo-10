package io.github.some_example_name;

public class GotaBuenaStrategy implements GotaStrategy {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10); // Incrementa los puntos
    }
}
