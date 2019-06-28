package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Tproducto;

public interface ITproductoService {
	public Integer insertar(Tproducto tproducto);

	public void modificar(Tproducto tproducto);

	public void eliminar(int idTproducto);

	Optional<Tproducto> listarId(int idTproducto);

	List<Tproducto> listar();

	List<Tproducto> buscarNombre(String nombreTproducto);

}
