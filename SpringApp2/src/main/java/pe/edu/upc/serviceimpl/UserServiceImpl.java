package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Users;
import pe.edu.upc.repository.UserRepository;
import pe.edu.upc.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	
	
	@Autowired
	private UserRepository uR;
	
	@Override
	@Transactional
	public void insertar(Users user) {
		
		uR.save(user);
		
	}

	@Override
	@Transactional
	public void modificar(Users user) {
		uR.save(user);
		
	}

	@Override
	@Transactional
	public void eliminar(long id) {
		uR.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Users> listarid(long id) {
		// TODO Auto-generated method stub
		return uR.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Users> listar() {
		// TODO Auto-generated method stub
		return uR.findAll();
	}

	public List<Users> buscarNombre(String nombreusuario) {
		
		return uR.findByNombreUser(nombreusuario);
	}

	@Override
	public List<Users> buscarDni(String dniUsuario) {
		// TODO Auto-generated method stub
		return uR.findByDniUser(dniUsuario);
	}

	@Override
	public Users buscarusername(String username) {
		// TODO Auto-generated method stub
		return uR.findByUsername(username);
	}

}
