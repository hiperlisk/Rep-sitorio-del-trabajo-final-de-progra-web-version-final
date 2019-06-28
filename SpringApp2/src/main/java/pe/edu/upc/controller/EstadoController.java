package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Estado;
import pe.edu.upc.service.IEstadoService;

@Controller
@RequestMapping("/estados")
public class EstadoController {
	@Autowired
	private IEstadoService eService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevoEstado(Model model) {
		model.addAttribute("estado", new Estado());
		return "estado/estado";
	}

	@PostMapping("/guardar")
	public String guardarEstado(@Valid Estado estado, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/estado/estado";
		} else {
			eService.insertar(estado);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/estados/listar";
		}
	}

	@GetMapping("/listar")
	public String listarEstados(Model model) {
		try {
			model.addAttribute("estado", new Estado());
			model.addAttribute("listaEstados", eService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/estado/listaEstado";
	}

	@GetMapping("/detalle/{id}")
	public String detailsEstado(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Estado> estado = eService.listarId(id);
			if (!estado.isPresent()) {
				model.addAttribute("info", "Estado no existe");
				return "redirect:/estados/listar";
			} else {
				model.addAttribute("estado", estado.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/estado/estado";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				eService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un estado");
		}
		model.put("listaEstados", eService.listar());

		return "redirect:/estados/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Estado estado) throws ParseException {

		List<Estado> listaEstados;

		estado.setDescripcionEstado(estado.getDescripcionEstado());
		listaEstados = eService.buscarDescripcionEstado(estado.getDescripcionEstado());

		if (listaEstados.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaEstados", listaEstados);
		return "estado/listaEstado";

	}
}