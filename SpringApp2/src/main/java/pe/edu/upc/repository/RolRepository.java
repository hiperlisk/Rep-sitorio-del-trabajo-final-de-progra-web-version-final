package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

	@Query("from Rol r where r.nombreRol like %:nombreRol%")
	List<Rol> findBynombreRol(String nombreRol);

	@Query("select count(r.nombreRol) from Rol r where r.nombreRol =:nombreRol")
	public int buscarNombreRol(@Param("nombreRol") String nombreRol);

}
