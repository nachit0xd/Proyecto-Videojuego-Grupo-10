package io.github.some_example_name;

public class GotaContext {
    private GotaStrategy estrategiaActual; // Atributo privado de tipo Strategy

    // Constructor que recibe una estrategia inicial
    public GotaContext(GotaStrategy estrategia) {
        this.estrategiaActual = estrategia;
    }

    // Método setter para cambiar la estrategia
    public void setStrategy(GotaStrategy nuevaEstrategia) {
        this.estrategiaActual = nuevaEstrategia;
    }

    // Método que aplica la estrategia actual
    public void ejecutarEstrategia(Tarro tarro) {
        if (estrategiaActual != null) {
            estrategiaActual.aplicarEfecto(tarro);
        }
    }
}
