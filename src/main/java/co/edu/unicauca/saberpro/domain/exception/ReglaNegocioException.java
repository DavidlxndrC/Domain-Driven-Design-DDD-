package co.edu.unicauca.saberpro.domain.exception;

// Se lanza cuando se intenta violar una invariante o regla de negocio del dominio.
public class ReglaNegocioException extends DomainException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}