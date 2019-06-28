package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

	@Query("select p from Proyecto p where p.nombreProyecto like %?1%")
	public List<Proyecto> findByNombreProyecto(String term);

	@Query("select p from Proyecto p where p.usuario.nombreUsuario like %?1%")
	public List<Proyecto> buscarUsuario(String nombreUsuario);

	@Query("select p from Proyecto p where p.area.nombreArea like %?1%")
	public List<Proyecto> buscarArea(String nombreArea);

	@Query("select p from Proyecto p where p.producto.nombreProducto like %?1%")
	public List<Proyecto> buscarProducto(String nombreProducto);

	@Query("select count(p.nombreProyecto) from Proyecto p where p.nombreProyecto =:nombreProyecto")

	public int buscarNombreProyecto(@Param("nombreProyecto") String nombreProyecto);

}
