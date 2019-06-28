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

import pe.edu.upc.entity.Tinforme;
import pe.edu.upc.service.ITinformeService;

@Controller
@RequestMapping("/tinformes")
public class TinformeController {

	@Autowired
	private ITinformeService tService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevaTinforme(Model model) {
		model.addAttribute("tinforme", new Tinforme());
		return "tinforme/tinforme";
	}

	@PostMapping("/guardar")
	public String guardarTinforme(@Valid Tinforme tinforme, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "tinforme/tinforme";
		} else {
			int rpta = tService.insertar(tinforme);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/tinforme/tinforme";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaTinformes", tService.listar());

		return "/tinforme/listaTinforme";
	}

	@GetMapping("/listar")
	public String listarTinforme(Model model) {
		try {
			model.addAttribute("tinforme", new Tinforme());
			model.addAttribute("listaTinformes", tService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tinforme/listaTinforme";
	}

	@GetMapping("/detalle/{id}")
	public String detailsTinforme(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Tinforme> tinforme = tService.listarId(id);
			if (!tinforme.isPresent()) {
				model.addAttribute("info", "Tinforme no existe");
				return "redirect:/tinformes/listar";
			} else {
				model.addAttribute("tinforme", tinforme.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tinforme/tinforme";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				tService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar una Tinforme");
		}
		model.put("listaTinformes", tService.listar());

		return "redirect:/tinformes/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Tinforme tinforme) throws ParseException {

		List<Tinforme> listaTinformes;

		tinforme.setNombreTinforme(tinforme.getNombreTinforme());
		listaTinformes = tService.buscarNombre(tinforme.getNombreTinforme());

		if (listaTinformes.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaTinformes", listaTinformes);
		return "tinforme/listaTinforme";

	}
}
