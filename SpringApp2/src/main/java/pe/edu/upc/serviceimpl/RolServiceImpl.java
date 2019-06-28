package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Rol;
import pe.edu.upc.repository.RolRepository;
import pe.edu.upc.service.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	private RolRepository rR;

	@Override
	@Transactional
	public Integer insertar(Rol rol) {

		int rpta = rR.buscarNombreRol(rol.getNombreRol());
		if (rpta == 0) {
			rR.save(rol);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Rol rol) {
		rR.save(rol);
	}

	@Override
	@Transactional
	public void eliminar(int idRol) {
		rR.deleteById(idRol);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Rol> listarId(int idRol) {
		return rR.findById(idRol);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Rol> listar() {
		return rR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Rol> buscarnombreRol(String nombreRol) {
		return rR.findBynombreRol(nombreRol);
	}

}
