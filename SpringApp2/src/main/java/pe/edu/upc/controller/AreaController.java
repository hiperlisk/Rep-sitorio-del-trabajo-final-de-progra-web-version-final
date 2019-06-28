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

import pe.edu.upc.entity.Area;
import pe.edu.upc.service.IAreaService;

@Controller
@RequestMapping("/areas")
public class AreaController {

	@Autowired
	private IAreaService aService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevaArea(Model model) {
		model.addAttribute("area", new Area());
		return "area/area";
	}

	@PostMapping("/guardar")
	public String guardarArea(@Valid Area area, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "area/area";
		} else {
			int rpta = aService.insertar(area);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/area/area";
			} else {
				model.addAttribute("mensaje", "Muy bien ingresados los datos. Felicitaciones");
				status.setComplete();
			}
		}
		model.addAttribute("listaAreas", aService.listar());

		return "/area/listaArea";
	}

	@GetMapping("/listar")
	public String listarAreas(Model model) {
		try {
			model.addAttribute("area", new Area());
			model.addAttribute("listaAreas", aService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/area/listaArea";
	}

	@GetMapping("/detalle/{id}")
	public String detailsArea(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Area> area = aService.listarId(id);
			if (!area.isPresent()) {
				model.addAttribute("info", "Area no existe");
				return "redirect:/areas/listar";
			} else {
				model.addAttribute("area", area.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/area/area";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				aService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un area");
		}
		model.put("listaAreas", aService.listar());

		return "redirect:/areas/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Area area) throws ParseException {

		List<Area> listaAreas;

		area.setNombreArea(area.getNombreArea());
		listaAreas = aService.buscarNombre(area.getNombreArea());

		if (listaAreas.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaAreas", listaAreas);
		return "area/listaArea";
	}
}
