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

import pe.edu.upc.entity.Tproducto;
import pe.edu.upc.service.ITproductoService;

@Controller
@RequestMapping("/tproductos")
public class TproductoController {

	@Autowired
	private ITproductoService tService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevaTproducto(Model model) {
		model.addAttribute("tproducto", new Tproducto());
		return "tproducto/tproducto";
	}

	@PostMapping("/guardar")
	public String guardarTproducto(@Valid Tproducto tproducto, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "tproducto/tproducto";
		} else {
			int rpta = tService.insertar(tproducto);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/tproducto/tproducto";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaTproductos", tService.listar());

		return "/tproducto/listaTproducto";
	}

	@GetMapping("/listar")
	public String listarTproducto(Model model) {
		try {
			model.addAttribute("tproducto", new Tproducto());
			model.addAttribute("listaTproductos", tService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tproducto/listaTproducto";
	}

	@GetMapping("/detalle/{id}")
	public String detailsTproducto(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Tproducto> tproducto = tService.listarId(id);
			if (!tproducto.isPresent()) {
				model.addAttribute("info", "Tproducto no existe");
				return "redirect:/tproductos/listar";
			} else {
				model.addAttribute("tproducto", tproducto.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tproducto/tproducto";
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
			model.put("mensaje", "No se puede eliminar una Tproducto");
		}
		model.put("listaTproductos", tService.listar());

		return "redirect:/tproductos/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Tproducto tproducto) throws ParseException {

		List<Tproducto> listaTproductos;

		tproducto.setNombreTproducto(tproducto.getNombreTproducto());
		listaTproductos = tService.buscarNombre(tproducto.getNombreTproducto());

		if (listaTproductos.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaTproductos", listaTproductos);
		return "tproducto/listaTproducto";

	}
}
