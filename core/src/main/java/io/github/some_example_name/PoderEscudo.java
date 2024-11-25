package io.github.some_example_name;

public class PoderEscudo implements Poder {
    @Override
    public void efecto(Tarro tarro) {
        tarro.activarEscudo(10); // Activa un escudo por 10 segundos
    }
}
