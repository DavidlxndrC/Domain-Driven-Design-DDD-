package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    // ---------- Justificacion ----------

    @Test
    void justificacionNulaDebeFallar() {
        assertThrows(ReglaNegocioException.class, () -> new Justificacion(null));
    }

    @Test
    void justificacionConMenosDeQuinceCaracteresDebeFallar() {
        assertThrows(ReglaNegocioException.class, () -> new Justificacion("Muy corta"));
    }

    @Test
    void justificacionCuentaCaracteresSinEspaciosDeLosExtremos() {
        assertThrows(ReglaNegocioException.class, () -> new Justificacion("   corta   "));
    }

    @Test
    void justificacionConPalabraProhibidaDebeFallar() {
        assertThrows(ReglaNegocioException.class,
                () -> new Justificacion("La respuesta correcta es siempre la opción B."));
    }

    @Test
    void justificacionValidaDebeCrearseYRecortarse() {
        Justificacion varJustificacion = new Justificacion("  Texto valido de justificacion.  ");
        assertEquals("Texto valido de justificacion.", varJustificacion.getTexto());
    }

    @Test
    void justificacionesConElMismoTextoSonIguales() {
        assertEquals(new Justificacion("Texto valido de justificacion."),
                new Justificacion("Texto valido de justificacion."));
    }

    // ---------- Enunciado ----------

    @Test
    void enunciadoNuloDebeFallar() {
        assertThrows(ReglaNegocioException.class, () -> new Enunciado(null));
    }

    @Test
    void enunciadoVacioDebeFallar() {
        assertThrows(ReglaNegocioException.class, () -> new Enunciado("   "));
    }

    @Test
    void enunciadoValidoDebeCrearse() {
        assertEquals("¿Qué es un agregado?", new Enunciado("¿Qué es un agregado?").getTexto());
    }

    // ---------- OpcionRespuesta ----------

    @Test
    void opcionConTextoVacioDebeFallar() {
        assertThrows(ReglaNegocioException.class, () -> new OpcionRespuesta("", true));
    }

    @Test
    void opcionDebeConservarTextoYClave() {
        OpcionRespuesta varOpcion = new OpcionRespuesta("Opción A", true);
        assertEquals("Opción A", varOpcion.getTexto());
        assertTrue(varOpcion.isEsCorrecta());
    }

    @Test
    void opcionesConIgualTextoYClaveSonIguales() {
        assertEquals(new OpcionRespuesta("A", false), new OpcionRespuesta("A", false));
        assertNotEquals(new OpcionRespuesta("A", false), new OpcionRespuesta("A", true));
    }
}
