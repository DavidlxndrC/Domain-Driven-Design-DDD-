package co.edu.unicauca.saberpro.domain.exception;

// Excepcion base de todas las violaciones del dominio. No depende de ningun framework.
public class DomainException extends RuntimeException {

    public DomainException(String mensaje) {
        super(mensaje);
    }
}
