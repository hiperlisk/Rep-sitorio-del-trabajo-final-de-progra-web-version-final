package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Tproducto;
import pe.edu.upc.repository.TproductoRepository;
import pe.edu.upc.service.ITproductoService;

@Service
public class TProductoServiceImpl implements ITproductoService {

	@Autowired
	private TproductoRepository tR;

	@Override
	public Integer insertar(Tproducto tproducto) {
		int rpta = tR.buscarNombreTproducto(tproducto.getNombreTproducto());
		if (rpta == 0) {
			tR.save(tproducto);
		}
		return rpta;
	}

	@Override
	public void modificar(Tproducto tproducto) {
		tR.save(tproducto);
	}

	@Override
	public void eliminar(int idTproducto) {
		tR.deleteById(idTproducto);

	}

	@Override
	public Optional<Tproducto> listarId(int idTproducto) {
		// TODO Auto-generated method stub
		return tR.findById(idTproducto);
	}

	@Override
	public List<Tproducto> listar() {
		// TODO Auto-generated method stub
		return tR.findAll(Sort.by(Sort.Direction.ASC, "nombreTproducto"));
	}

	@Override
	public List<Tproducto> buscarNombre(String nombreTproducto) {
		// TODO Auto-generated method stub
		return tR.findbynombreTproducto(nombreTproducto);
	}

}
