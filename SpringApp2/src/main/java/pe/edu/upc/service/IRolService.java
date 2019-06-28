package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Rol;

public interface IRolService {

	public Integer insertar(Rol rol);

	public void modificar(Rol rol);

	public void eliminar(int idRol);

	Optional<Rol> listarId(int idRol);

	List<Rol> listar();

	List<Rol> buscarnombreRol(String nombreRol);

}
