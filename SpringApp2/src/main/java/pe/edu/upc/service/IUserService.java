package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;


import pe.edu.upc.entity.Users;

public interface IUserService {

	public void insertar(Users user);
	public void modificar (Users user);;
	public void eliminar (long id);
	Optional <Users> listarid (long id);
	List <Users> listar();
	List<Users> buscarNombre(String nombreUsuario);
	List<Users> buscarDni(String dniUsuario);
	Users buscarusername(String username);
}
