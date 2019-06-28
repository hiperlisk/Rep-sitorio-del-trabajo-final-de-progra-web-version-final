package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.entity.Tinforme;

public interface TinformeRepository extends JpaRepository<Tinforme, Integer> {

	@Query("from Tinforme t where t.nombreTinforme like %:nombreTinforme%")
	List<Tinforme> findbynombreTinforme(String nombreTinforme);

	@Query("select count(t.nombreTinforme) from Tinforme t where t.nombreTinforme =:nombreTinforme")
	public int buscarNombreTinforme(@Param("nombreTinforme") String nombreTinforme);

}
