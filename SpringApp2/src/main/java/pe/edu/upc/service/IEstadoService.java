package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Estado;

public interface IEstadoService {

	public void insertar(Estado estado);

	public void modificar(Estado estado);

	public void eliminar(int idEstado);

	Optional<Estado> listarId(int idEstado);

	List<Estado> listar();

	List<Estado> buscarDescripcionEstado(String descripcionEstado);

}