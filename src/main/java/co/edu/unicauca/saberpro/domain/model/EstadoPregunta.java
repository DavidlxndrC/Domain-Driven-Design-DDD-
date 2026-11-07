package co.edu.unicauca.saberpro.domain.model;

// Estados del ciclo de vida de una pregunta (Bounded Context: Ciclo de Vida).
public enum EstadoPregunta {
    BORRADOR,
    PENDIENTE_REVISION,
    EN_REVISION,
    APROBADA,
    RECHAZADA
}
