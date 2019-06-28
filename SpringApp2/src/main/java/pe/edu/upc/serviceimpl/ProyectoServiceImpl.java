package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Proyecto;
import pe.edu.upc.repository.ProyectoRepository;
import pe.edu.upc.service.IProyectoService;

@Service
public class ProyectoServiceImpl implements IProyectoService {

	@Autowired
	private ProyectoRepository prR;

	@Override
	@Transactional
	public Integer insertar(Proyecto proyecto) {
	
		int rpta=prR.buscarNombreProyecto(proyecto.getNombreProyecto());
		if (rpta== 0) {		
			prR.save(proyecto);

		}
		return rpta;
	}


	@Override
	@Transactional
	public void modificar(Proyecto proyecto) {
		prR.save(proyecto);
	}

	@Override
	@Transactional
	public void eliminar(int idProyecto) {
		prR.deleteById(idProyecto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Proyecto> listarId(int idProyecto) {
		return prR.findById(idProyecto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> listar() {
		return prR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> buscar(String nombreProyecto) {
		return prR.findByNombreProyecto(nombreProyecto);
	}

	@Override
	public List<Proyecto> buscarUsuario(String nombreUsuario) {
		return prR.buscarUsuario(nombreUsuario);
	}

	@Override
	@Transactional
	public List<Proyecto> buscarArea(String nombreArea) {
		return prR.buscarArea(nombreArea);
	}

	@Override
	@Transactional
	public List<Proyecto> buscarProducto(String nombreProducto) {
		return prR.buscarProducto(nombreProducto);
	}

}
