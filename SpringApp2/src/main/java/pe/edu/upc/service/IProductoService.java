package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Producto;

public interface IProductoService {
	
	public Integer insertar(Producto producto);

	public void modificar(Producto producto);

	public void eliminar(int idProducto);

	Optional<Producto> listarId(int idProducto);

	List<Producto> listar();

	List<Producto> buscarnombreProducto(String nombreProducto);

	List<Producto> buscarmarcaProducto(String marcaProducto);
	
	List<Producto> buscartipoProducto(String nombreTproducto);
	
}