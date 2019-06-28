package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Informe;
import pe.edu.upc.service.IEstadoService;
import pe.edu.upc.service.IInformeService;
import pe.edu.upc.service.IProyectoService;
import pe.edu.upc.service.ITinformeService;
import pe.edu.upc.service.IUploadFileService;

@Controller
@RequestMapping("/informes")
public class InformeController {

	@Autowired
	private IInformeService iService;

	@Autowired
	private IProyectoService pService;

	@Autowired
	private IEstadoService eService;

	@Autowired
	private ITinformeService tService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ADMIN" })
	@GetMapping("/nuevo")
	public String nuevoInforme(Model model) {
		model.addAttribute("informe", new Informe());
		model.addAttribute("listaProyectos", pService.listar());
		model.addAttribute("listaEstados", eService.listar());
		model.addAttribute("listaTinformes", tService.listar());
		return "informe/informe";
	}

	@PostMapping("/guardar")
	public String guardarInforme(@Valid Informe informe, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaProyectos", pService.listar());
			model.addAttribute("listaEstados", eService.listar());
			model.addAttribute("listaTinformes", tService.listar());
			return "/informe/informe";
		}
		if (result.hasErrors()) {
			model.addAttribute("listaProyectos", pService.listar());
			model.addAttribute("listaEstados", eService.listar());
			model.addAttribute("listaTinformes", tService.listar());
			model.addAttribute("listaEstados", eService.listar());
			return "/informe/informe";
		}
		if (result.hasErrors()) {
			model.addAttribute("listaTinformes", tService.listar());
			return "/informe/informe";
		} else {
			if (!foto.isEmpty()) {

				if (informe.getIdInforme() > 0 && informe.getFotoInforme() != null
						&& informe.getFotoInforme().length() > 0) {

					uploadFileService.delete(informe.getFotoInforme());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				informe.setFotoInforme(uniqueFilename);
			}
			iService.insertar(informe);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/informes/listar";
		}
	}

	@GetMapping("/listar")
	public String listarInformes(Model model) {
		try {
			model.addAttribute("informe", new Informe());
			model.addAttribute("listaInformes", iService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/informe/listaInforme";
	}

	@Secured({ "ROLE_ENCARGADO", "ROLE_ADMIN" })
	@GetMapping("/detalle/{id}")
	public String detailsInforme(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Informe> informe = iService.listarId(id);

			if (!informe.isPresent()) {
				model.addAttribute("info", "Informe no existe");
				return "redirect:/informes/listar";
			} else {
				model.addAttribute("informe", informe.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/informe/informe";
	}

	@Secured({ "ROLE_ENCARGADO", "ROLE_ADMIN" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				iService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un informe");
		}
		model.put("listaInformes", iService.listar());

		return "redirect:/informes/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Informe informe) throws ParseException {

		List<Informe> listaInformes;
		informe.setDescripcionInforme(informe.getDescripcionInforme());
		listaInformes = iService.buscar(informe.getDescripcionInforme());

		if (listaInformes.isEmpty()) {
			listaInformes = iService.buscarProyecto(informe.getDescripcionInforme());
		}

		if (listaInformes.isEmpty()) {
			listaInformes = iService.buscarEstado(informe.getDescripcionInforme());
		}

		if (listaInformes.isEmpty()) {
			listaInformes = iService.buscarTipo(informe.getDescripcionInforme());
		}

		if (listaInformes.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaInformes", listaInformes);
		return "informe/listaInforme";
	}

	@GetMapping(value = "/verinf/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Informe> informe = iService.listarId(id);
		if (informe == null) {
			flash.addFlashAttribute("error", "El Informe no existe en la base de datos");
			return "redirect:/informes/listar";
		}

		model.put("informe", informe.get());

		return "informe/verinf";
	}

	@Secured({ "ROLE_ENCARGADO", "ROLE_ADMIN" })
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Informe> objInf = iService.listarId(id);

		if (objInf == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/proyectos/listar";
		} else {
			model.addAttribute("listaProyectos", pService.listar());
			model.addAttribute("listaEstados", eService.listar());
			model.addAttribute("listaTinformes", tService.listar());
			model.addAttribute("proyecto", objInf.get());
			return "informe/informe";
		}
	}
}
