package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

// VALUE OBJECT inmutable: minimo 15 caracteres y sin palabras prohibidas.
public final class Justificacion {

    private static final List<String> PALABRAS_PROHIBIDAS = List.of(
            "siempre", "nunca", "todas las anteriores", "ninguna de las anteriores");

    private final String texto;

    public Justificacion(String texto) {
        if (texto == null || texto.trim().length() < 15) {
            throw new ReglaNegocioException("La justificación debe contener al menos 15 caracteres.");
        }
        String varNormalizado = texto.trim().toLowerCase(Locale.ROOT);
        for (String varPalabra : PALABRAS_PROHIBIDAS) {
            if (varNormalizado.contains(varPalabra)) {
                throw new ReglaNegocioException(
                        "La justificación contiene una palabra prohibida: \"" + varPalabra + "\".");
            }
        }
        this.texto = texto.trim();
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Justificacion)) return false;
        return texto.equals(((Justificacion) o).texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texto);
    }

    @Override
    public String toString() {
        return texto;
    }
}
