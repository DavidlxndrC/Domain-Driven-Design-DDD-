package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// AGGREGATE ROOT. Toda modificacion de opciones, enunciado, justificacion y estado
// pasa por esta clase, que garantiza las invariantes del negocio.
public class Pregunta {

    private static final int NUMERO_OPCIONES = 4;

    private Long id;
    private Enunciado enunciado;
    private Justificacion justificacion;
    private final List<OpcionRespuesta> opciones = new ArrayList<>();
    private EstadoPregunta estado = EstadoPregunta.BORRADOR;

    public Pregunta() {
    }

    public Pregunta(Long prmId) {
        this.id = prmId;
    }

    // ---------- Comandos del agregado ----------

    public void definirEnunciado(String prmTexto) {
        exigirBorrador("el enunciado");
        this.enunciado = new Enunciado(prmTexto);
    }

    public void definirJustificacion(String prmTexto) {
        exigirBorrador("la justificación");
        this.justificacion = new Justificacion(prmTexto);
    }

    public void agregarOpcion(String texto, boolean esCorrecta) {
        if (this.estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo se pueden modificar opciones en estado Borrador.");
        }
        if (this.opciones.size() >= NUMERO_OPCIONES) {
            throw new ReglaNegocioException("Una pregunta no puede tener más de 4 opciones.");
        }
        OpcionRespuesta varNueva = new OpcionRespuesta(texto, esCorrecta);
        if (this.opciones.contains(varNueva)
                || this.opciones.stream().anyMatch(o -> o.getTexto().equalsIgnoreCase(varNueva.getTexto()))) {
            throw new ReglaNegocioException("No se permiten opciones con texto repetido.");
        }
        if (esCorrecta && this.opciones.stream().anyMatch(OpcionRespuesta::isEsCorrecta)) {
            throw new ReglaNegocioException("Ya existe una opción correcta; solo puede haber 1 clave.");
        }
        this.opciones.add(varNueva);
    }

    public void enviarARevision() {
        if (this.estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo una pregunta en Borrador puede enviarse a revisión.");
        }
        validarInvariantesParaEnvio();
        this.estado = EstadoPregunta.PENDIENTE_REVISION;
    }

    public void iniciarRevision() {
        exigirEstado(EstadoPregunta.PENDIENTE_REVISION, "iniciar la revisión");
        this.estado = EstadoPregunta.EN_REVISION;
    }

    public void aprobar() {
        exigirEstado(EstadoPregunta.EN_REVISION, "aprobar");
        this.estado = EstadoPregunta.APROBADA;
    }

    public void rechazar() {
        exigirEstado(EstadoPregunta.EN_REVISION, "rechazar");
        this.estado = EstadoPregunta.RECHAZADA;
    }

    public void devolverABorrador() {
        if (this.estado != EstadoPregunta.RECHAZADA && this.estado != EstadoPregunta.PENDIENTE_REVISION) {
            throw new ReglaNegocioException("Solo una pregunta rechazada o pendiente puede volver a Borrador.");
        }
        this.estado = EstadoPregunta.BORRADOR;
    }

    // ---------- Invariantes ----------

    private void validarInvariantesParaEnvio() {
        if (this.enunciado == null) {
            throw new ReglaNegocioException("Debe tener un enunciado.");
        }
        if (this.justificacion == null) {
            throw new ReglaNegocioException("Debe tener una justificación.");
        }
        if (this.opciones.size() != NUMERO_OPCIONES) {
            throw new ReglaNegocioException("Debe tener exactamente 4 opciones de respuesta.");
        }
        long correctas = opciones.stream().filter(OpcionRespuesta::isEsCorrecta).count();
        if (correctas != 1) {
            throw new ReglaNegocioException("Debe existir exactamente 1 opción correcta.");
        }
    }

    private void exigirBorrador(String prmQue) {
        if (this.estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo se puede modificar " + prmQue + " en estado Borrador.");
        }
    }

    private void exigirEstado(EstadoPregunta prmEsperado, String prmAccion) {
        if (this.estado != prmEsperado) {
            throw new ReglaNegocioException("Para " + prmAccion + " la pregunta debe estar en estado " + prmEsperado + ".");
        }
    }

    // ---------- Consultas ----------

    public Long getId() {
        return id;
    }

    public Enunciado getEnunciado() {
        return enunciado;
    }

    public Justificacion getJustificacion() {
        return justificacion;
    }

    public List<OpcionRespuesta> getOpciones() {
        return Collections.unmodifiableList(opciones);
    }

    public EstadoPregunta getEstado() {
        return estado;
    }
}
