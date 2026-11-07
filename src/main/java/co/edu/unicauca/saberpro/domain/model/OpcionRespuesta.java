package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

import java.util.Objects;

// VALUE OBJECT inmutable: texto de la opcion + indicador de clave.
public final class OpcionRespuesta {

    private final String texto;
    private final boolean esCorrecta;

    public OpcionRespuesta(String texto, boolean esCorrecta) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ReglaNegocioException("El texto de la opción no puede ser nulo ni vacío.");
        }
        this.texto = texto.trim();
        this.esCorrecta = esCorrecta;
    }

    public String getTexto() {
        return texto;
    }

    // Nombre exigido por la guia del taller (OpcionRespuesta::isEsCorrecta)
    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpcionRespuesta)) return false;
        OpcionRespuesta varOtra = (OpcionRespuesta) o;
        return esCorrecta == varOtra.esCorrecta && texto.equals(varOtra.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texto, esCorrecta);
    }

    @Override
    public String toString() {
        return texto + (esCorrecta ? " (clave)" : "");
    }
}
