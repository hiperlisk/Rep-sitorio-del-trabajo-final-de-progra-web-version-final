package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Area;

public interface IAreaService {

	public Integer insertar(Area area);

	public void modificar(Area area);

	public void eliminar(int idArea);

	Optional<Area> listarId(int idArea);

	List<Area> listar();

	List<Area> buscarNombre(String nombreArea);

}
