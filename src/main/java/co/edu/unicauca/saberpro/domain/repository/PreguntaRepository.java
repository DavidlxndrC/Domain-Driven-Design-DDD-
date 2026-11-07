package co.edu.unicauca.saberpro.domain.repository;

import co.edu.unicauca.saberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.saberpro.domain.model.Pregunta;

import java.util.List;
import java.util.Optional;

// Interfaz de repositorio PURA (puerto). La implementacion (JPA, memoria, etc.)
// vive fuera del dominio, en la capa de infraestructura.
public interface PreguntaRepository {

    Pregunta guardar(Pregunta pregunta);

    Optional<Pregunta> buscarPorId(Long id);

    List<Pregunta> buscarPorEstado(EstadoPregunta estado);

    void eliminar(Long id);
}
