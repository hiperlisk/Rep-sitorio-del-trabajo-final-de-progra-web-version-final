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

import pe.edu.upc.entity.Rol;
import pe.edu.upc.service.IRolService;

@Controller
@RequestMapping("/roles")
public class RolController {

	@Autowired
	private IRolService rService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevoRol(Model model) {
		model.addAttribute("rol", new Rol());
		return "rol/rol";
	}

	@PostMapping("/guardar")
	public String guardarRol(@Valid Rol rol, BindingResult result, Model model, SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			return "/rol/rol";
		} else {
			int rpta = rService.insertar(rol);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/rol/rol";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaRoles", rService.listar());

		return "/rol/listaRol";
	}

	@GetMapping("/listar")
	public String listarRoles(Model model) {
		try {
			model.addAttribute("rol", new Rol());
			model.addAttribute("listaRoles", rService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/rol/listaRol";
	}

	@GetMapping("/detalle/{id}")
	public String detailsRol(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Rol> rol = rService.listarId(id);
			if (!rol.isPresent()) {
				model.addAttribute("info", "Rol no existe");
				return "redirect:/roles/listar";
			} else {
				model.addAttribute("rol", rol.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/rol/rol";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				rService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un estado");
		}
		model.put("listaRoles", rService.listar());

		return "redirect:/roles/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Rol rol) throws ParseException {

		List<Rol> listaRoles;

		rol.setNombreRol(rol.getNombreRol());
		listaRoles = rService.buscarnombreRol(rol.getNombreRol());

		if (listaRoles.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaRoles", listaRoles);
		return "rol/listaRol";

	}
}