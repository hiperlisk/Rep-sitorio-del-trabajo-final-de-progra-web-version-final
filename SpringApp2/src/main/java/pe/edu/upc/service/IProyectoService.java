package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Proyecto;

public interface IProyectoService {

	public Integer insertar(Proyecto proyecto);

	public void modificar(Proyecto proyecto);

	public void eliminar(int idProyecto);

	Optional<Proyecto> listarId(int idProyecto);

	List<Proyecto> listar();

	List<Proyecto> buscar(String nombreProyecto);

	List<Proyecto> buscarUsuario(String nombreUsuario);

	List<Proyecto> buscarArea(String nombreArea);

	List<Proyecto> buscarProducto(String nombreProducto);


}
