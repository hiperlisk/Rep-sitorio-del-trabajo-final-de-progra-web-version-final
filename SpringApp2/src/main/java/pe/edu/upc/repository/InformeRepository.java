package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Informe;

@Repository
public interface InformeRepository extends JpaRepository<Informe, Integer>{

	@Query("select i from Informe i where i.descripcionInforme like %?1%")
	public List<Informe> findByDescripcionInforme(String term);
	
	@Query("select i from Informe i where i.proyecto.nombreProyecto like %?1%")
	public List<Informe> buscarProyecto(String nombreProyecto);
	
	@Query("select i from Informe i where i.estado.descripcionEstado like %?1%")
	public List<Informe> buscarEstado(String descripcionEstado);
	
	@Query("select i from Informe i where i.tinforme.nombreTinforme like %?1%")
	public List<Informe> buscarTipo(String nombreTinforme);
}
