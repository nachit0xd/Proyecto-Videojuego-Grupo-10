package io.github.some_example_name;

public class GotaContext {
    private GotaStrategy estrategia; // Atributo privado de tipo Strategy

    // Constructor para inicializar con una estrategia
    public GotaContext(GotaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    // Cambia la estrategia en tiempo de ejecución
    public void setEstrategia(GotaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    // Ejecuta la estrategia actual
    public void ejecutarEstrategia(Tarro tarro) {
        if (estrategia != null) {
            estrategia.aplicarEfecto(tarro);
        }
    }
}
