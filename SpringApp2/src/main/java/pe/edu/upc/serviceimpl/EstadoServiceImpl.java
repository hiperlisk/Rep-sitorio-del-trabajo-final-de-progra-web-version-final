package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Estado;
import pe.edu.upc.repository.EstadoRepository;
import pe.edu.upc.service.IEstadoService;

@Service
public class EstadoServiceImpl implements IEstadoService {

	@Autowired
	private EstadoRepository eR;

	@Override
	@Transactional
	public void insertar(Estado estado) {
		eR.save(estado);

	}

	@Override
	@Transactional
	public void modificar(Estado estado) {
		eR.save(estado);

	}

	@Override
	@Transactional
	public void eliminar(int idEstado) {
		eR.deleteById(idEstado);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Estado> listarId(int idEstado) {
		return eR.findById(idEstado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Estado> listar() {
		return eR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Estado> buscarDescripcionEstado(String descripcionEstado) {
		return eR.findBydescripcionEstado(descripcionEstado);
	}

}