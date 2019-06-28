package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Informe;
import pe.edu.upc.repository.InformeRepository;
import pe.edu.upc.service.IInformeService;

@Service
public class InformeServiceImpl implements IInformeService {

	@Autowired
	private InformeRepository iR;

	@Override
	public void insertar(Informe informe) {
		iR.save(informe);
	}

	@Override
	public void modificar(Informe informe) {
		iR.save(informe);
	}

	@Override
	public void eliminar(int idInforme) {
		iR.deleteById(idInforme);
	}

	@Override
	public Optional<Informe> listarId(int idInforme) {
		return iR.findById(idInforme);
	}

	@Override
	public List<Informe> listar() {
		return iR.findAll();
	}

	@Override
	public List<Informe> buscar(String descripcionInforme) {
		return iR.findByDescripcionInforme(descripcionInforme);
	}

	@Override
	public List<Informe> buscarProyecto(String nombreUsuario) {
		return iR.buscarProyecto(nombreUsuario);
	}

	@Override
	public List<Informe> buscarEstado(String descripcionEstado) {
		return iR.buscarEstado(descripcionEstado);
	}

	@Override
	public List<Informe> buscarTipo(String nombreTinforme) {
		return iR.buscarTipo(nombreTinforme);
	}

}
