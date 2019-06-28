package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("from Area a where a.nombreArea like %:nombreArea%")
	List<Area> findbynombreArea(String nombreArea);

	@Query("select count(a.nombreArea) from Area a where a.nombreArea =:nombreArea")
	public int buscarNombreArea(@Param("nombreArea") String nombreArea);

}
