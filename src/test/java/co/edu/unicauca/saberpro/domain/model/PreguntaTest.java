package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreguntaTest {

    private Pregunta crearPreguntaCompleta() {
        Pregunta varPregunta = new Pregunta(1L);
        varPregunta.definirEnunciado("¿Cuál es la capital de Colombia?");
        varPregunta.definirJustificacion("Bogotá es la capital y sede del gobierno nacional.");
        varPregunta.agregarOpcion("Bogotá", true);
        varPregunta.agregarOpcion("Medellín", false);
        varPregunta.agregarOpcion("Cali", false);
        varPregunta.agregarOpcion("Popayán", false);
        return varPregunta;
    }

    // Prueba sugerida en la guia del taller
    @Test
    void noDebePermitirEnviarARevisionSinCuatroOpciones() {
        Pregunta pregunta = new Pregunta();
        pregunta.agregarOpcion("Opción A", true);
        assertThrows(ReglaNegocioException.class, pregunta::enviarARevision);
    }

    @Test
    void noDebePermitirEnviarARevisionSinExactamenteUnaClave() {
        Pregunta pregunta = new Pregunta();
        pregunta.definirEnunciado("Enunciado de prueba");
        pregunta.definirJustificacion("Justificación suficientemente larga.");
        pregunta.agregarOpcion("A", false);
        pregunta.agregarOpcion("B", false);
        pregunta.agregarOpcion("C", false);
        pregunta.agregarOpcion("D", false);
        assertThrows(ReglaNegocioException.class, pregunta::enviarARevision);
    }

    @Test
    void noDebePermitirDosClaves() {
        Pregunta pregunta = new Pregunta();
        pregunta.agregarOpcion("A", true);
        assertThrows(ReglaNegocioException.class, () -> pregunta.agregarOpcion("B", true));
    }

    @Test
    void noDebePermitirMasDeCuatroOpciones() {
        Pregunta pregunta = crearPreguntaCompleta();
        assertThrows(ReglaNegocioException.class, () -> pregunta.agregarOpcion("Quinta", false));
    }

    @Test
    void noDebePermitirOpcionesRepetidas() {
        Pregunta pregunta = new Pregunta();
        pregunta.agregarOpcion("A", false);
        assertThrows(ReglaNegocioException.class, () -> pregunta.agregarOpcion("a", false));
    }

    @Test
    void noDebePermitirEnviarSinEnunciado() {
        Pregunta pregunta = new Pregunta();
        pregunta.definirJustificacion("Justificación suficientemente larga.");
        pregunta.agregarOpcion("A", true);
        pregunta.agregarOpcion("B", false);
        pregunta.agregarOpcion("C", false);
        pregunta.agregarOpcion("D", false);
        assertThrows(ReglaNegocioException.class, pregunta::enviarARevision);
    }

    @Test
    void noDebePermitirEnviarSinJustificacion() {
        Pregunta pregunta = new Pregunta();
        pregunta.definirEnunciado("Enunciado de prueba");
        pregunta.agregarOpcion("A", true);
        pregunta.agregarOpcion("B", false);
        pregunta.agregarOpcion("C", false);
        pregunta.agregarOpcion("D", false);
        assertThrows(ReglaNegocioException.class, pregunta::enviarARevision);
    }

    @Test
    void debeEnviarARevisionCuandoCumpleTodasLasInvariantes() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, pregunta.getEstado());
    }

    @Test
    void noDebePermitirModificarOpcionesFueraDeBorrador() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        assertThrows(ReglaNegocioException.class, () -> pregunta.agregarOpcion("Otra", false));
        assertThrows(ReglaNegocioException.class, () -> pregunta.definirEnunciado("Nuevo enunciado"));
    }

    @Test
    void noDebePermitirEnviarDosVeces() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        assertThrows(ReglaNegocioException.class, pregunta::enviarARevision);
    }

    @Test
    void debeRecorrerElCicloDeVidaCompleto() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        pregunta.iniciarRevision();
        assertEquals(EstadoPregunta.EN_REVISION, pregunta.getEstado());
        pregunta.aprobar();
        assertEquals(EstadoPregunta.APROBADA, pregunta.getEstado());
    }

    @Test
    void noDebePermitirAprobarSinHaberIniciadoRevision() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        assertThrows(ReglaNegocioException.class, pregunta::aprobar);
    }

    @Test
    void preguntaRechazadaDebePoderVolverABorrador() {
        Pregunta pregunta = crearPreguntaCompleta();
        pregunta.enviarARevision();
        pregunta.iniciarRevision();
        pregunta.rechazar();
        pregunta.devolverABorrador();
        assertEquals(EstadoPregunta.BORRADOR, pregunta.getEstado());
    }

    @Test
    void laListaDeOpcionesExpuestaNoDebeSerModificable() {
        Pregunta pregunta = crearPreguntaCompleta();
        assertThrows(UnsupportedOperationException.class,
                () -> pregunta.getOpciones().add(new OpcionRespuesta("X", false)));
    }
}
