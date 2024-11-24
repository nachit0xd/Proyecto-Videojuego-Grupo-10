package io.github.some_example_name;

public class PoderEscudoStrategy implements GotaStrategy {
    private static final float DURACION_ESCUDO = 10f; // Duración del escudo en segundos

    public void aplicarEfecto(Tarro tarro) {
        tarro.activarEscudo(DURACION_ESCUDO); // Activa el escudo por la duración definida
    }
}
