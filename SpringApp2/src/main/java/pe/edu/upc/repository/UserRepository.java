package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	public Users findByUsername(String username);
	
	@Query("from Users u where u.nombreUsuario like %:nombreUsuario%")
	List<Users> findByNombreUser(String nombreUsuario);

	@Query("from Users u where u.dniUsuario like %:dniUsuario%")
	List<Users> findByDniUser(String dniUsuario);

}