package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Producto;
import pe.edu.upc.repository.ProductoRepository;
import pe.edu.upc.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoRepository pR;

	@Override
	@Transactional
	public Integer insertar(Producto producto) {

		int rpta = pR.buscarNombreProducto(producto.getNombreProducto());
		if (rpta == 0) {
			pR.save(producto);

		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Producto producto) {
		pR.save(producto);

	}

	@Override
	@Transactional
	public void eliminar(int idProducto) {
		pR.deleteById(idProducto);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> listarId(int idProducto) {
		return pR.findById(idProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> listar() {
		return pR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> buscarnombreProducto(String nombreProducto) {
		return pR.findBynombreProducto(nombreProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> buscarmarcaProducto(String marcaProducto) {
		return pR.findBymarcaProducto(marcaProducto);
	}

	@Override
	public List<Producto> buscartipoProducto(String nombreTproducto) {
		return pR.findBytipoProducto(nombreTproducto);
	}	

}