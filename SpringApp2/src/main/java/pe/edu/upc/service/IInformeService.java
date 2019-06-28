package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Informe;

public interface IInformeService {

	public void insertar(Informe informe);

	public void modificar(Informe informe);

	public void eliminar(int idInforme);

	Optional<Informe> listarId(int idInforme);

	List<Informe> listar();

	List<Informe> buscar(String descripcionInforme);

	List<Informe> buscarProyecto(String nombreUsuario);

	List<Informe> buscarEstado(String descripcionEstado);

	List<Informe> buscarTipo(String nombreTinforme);

}
