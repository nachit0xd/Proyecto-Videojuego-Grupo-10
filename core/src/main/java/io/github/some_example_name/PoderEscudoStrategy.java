package io.github.some_example_name;

public class PoderEscudoStrategy implements PoderStrategy {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.activarEscudo(10); // Activa un escudo por 10 segundos
    }
}
