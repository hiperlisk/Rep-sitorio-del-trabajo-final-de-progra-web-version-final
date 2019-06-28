package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.edu.upc.entity.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Query("from Estado e where e.descripcionEstado like %:descripcionEstado%")
	List<Estado> findBydescripcionEstado(String descripcionEstado);

}