package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Tinforme;

public interface ITinformeService {
	public Integer insertar(Tinforme tinforme);

	public void modificar(Tinforme tinforme);

	public void eliminar(int idTinforme);

	Optional<Tinforme> listarId(int idTinforme);

	List<Tinforme> listar();

	List<Tinforme> buscarNombre(String nombreTinforme);

}
