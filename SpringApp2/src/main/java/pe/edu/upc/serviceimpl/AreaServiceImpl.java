package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Area;
import pe.edu.upc.repository.AreaRepository;
import pe.edu.upc.service.IAreaService;

@Service
public class AreaServiceImpl implements IAreaService {

	@Autowired
	private AreaRepository aR;

	@Override
	@Transactional
	public Integer insertar(Area area) {

		int rpta = aR.buscarNombreArea(area.getNombreArea());
		if (rpta == 0) {
			aR.save(area);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Area area) {
		aR.save(area);
	}

	@Override
	@Transactional
	public void eliminar(int idArea) {
		aR.deleteById(idArea);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Area> listarId(int idArea) {
		return aR.findById(idArea);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> listar() {
		return aR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> buscarNombre(String nombreArea) {
		return aR.findbynombreArea(nombreArea);
	}

}
