package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.entity.Tproducto;

public interface TproductoRepository extends JpaRepository<Tproducto, Integer> {

	@Query("from Tproducto t where t.nombreTproducto like %:nombreTproducto%")
	List<Tproducto> findbynombreTproducto(String nombreTproducto);

	@Query("select count(t.nombreTproducto) from Tproducto t where t.nombreTproducto =:nombreTproducto")
	public int buscarNombreTproducto(@Param("nombreTproducto") String nombreTproducto);

}
