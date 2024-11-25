package io.github.some_example_name;

public class PoderContext {
    private PoderStrategy strategy; // Atributo privado de tipo Strategy

    // Constructor que recibe una estrategia inicial
    public PoderContext(PoderStrategy strategy) {
        this.strategy = strategy;
    }

    // Método para cambiar la estrategia
    public void setStrategy(PoderStrategy strategy) {
        this.strategy = strategy;
    }

    // Método que utiliza la estrategia actual para aplicar el poder
    public void aplicarPoder(Tarro tarro) {
        if (strategy != null) {
            strategy.aplicarEfecto(tarro);
        }
    }
}
