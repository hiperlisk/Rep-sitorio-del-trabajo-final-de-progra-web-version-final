package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Tinforme;
import pe.edu.upc.repository.TinformeRepository;
import pe.edu.upc.service.ITinformeService;

@Service
public class TInformeServiceImpl implements ITinformeService {

	@Autowired
	private TinformeRepository tR;

	@Override
	public Integer insertar(Tinforme tinforme) {
		int rpta = tR.buscarNombreTinforme(tinforme.getNombreTinforme());
		if (rpta == 0) {
			tR.save(tinforme);
		}
		return rpta;
	}

	@Override
	public void modificar(Tinforme tinforme) {
		tR.save(tinforme);
	}

	@Override
	public void eliminar(int idTinforme) {
		tR.deleteById(idTinforme);

	}

	@Override
	public Optional<Tinforme> listarId(int idTinforme) {
		// TODO Auto-generated method stub
		return tR.findById(idTinforme);
	}

	@Override
	public List<Tinforme> listar() {
		// TODO Auto-generated method stub
		return tR.findAll(Sort.by(Sort.Direction.ASC, "nombreTinforme"));
	}

	@Override
	public List<Tinforme> buscarNombre(String nombreTinforme) {
		// TODO Auto-generated method stub
		return tR.findbynombreTinforme(nombreTinforme);
	}

}
