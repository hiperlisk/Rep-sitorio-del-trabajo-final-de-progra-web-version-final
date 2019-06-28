package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	@Query("from Producto p where p.nombreProducto like %:nombreProducto%")
	List<Producto> findBynombreProducto(String nombreProducto);

	@Query("from Producto p where p.marcaProducto like %:marcaProducto%")
	List<Producto> findBymarcaProducto(String marcaProducto);

	@Query("from Producto p where p.tproducto.nombreTproducto like %:nombreTproducto%")
	List<Producto> findBytipoProducto(String nombreTproducto);

	@Query("select count(p.nombreProducto) from Producto p where p.nombreProducto =:nombreProducto")
	public int buscarNombreProducto(@Param("nombreProducto") String nombreProducto);

}