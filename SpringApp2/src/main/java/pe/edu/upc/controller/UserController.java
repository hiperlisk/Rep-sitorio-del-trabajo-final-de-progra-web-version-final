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

import pe.edu.upc.entity.Users;
import pe.edu.upc.service.IUploadFileService;
import pe.edu.upc.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService uService;

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

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({"ROLE_ADMIN", "ROLE_JEFE"})
	@GetMapping("/nuevo")
	public String nuevoUser(Model model) {
		model.addAttribute("users", new Users());
		return "users/users";
	}

	@PostMapping("/guardar")
	public String guardarUser(@Valid Users user, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaUsers", uService.listar());
			return "users/users";
		} else {

			if (!foto.isEmpty()) {

				if (user.getId() > 0 && user.getFotoUsuario() != null && user.getFotoUsuario().length() > 0) {

					uploadFileService.delete(user.getFotoUsuario());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				user.setFotoUsuario(uniqueFilename);
			}

			uService.insertar(user);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
		}

		model.addAttribute("listaUsers", uService.listar());
		return "/users/listaUsers";
	}

	@GetMapping("/listar")
	public String listarUsuarios(Model model) {
		try {
			model.addAttribute("users", new Users());
			model.addAttribute("listaUsers", uService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/users/listaUsers";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Users user) throws ParseException {

		List<Users> listaUsers;

		user.setNombreUsuario(user.getNombreUsuario());
		listaUsers = uService.buscarNombre(user.getNombreUsuario());

		if (listaUsers.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaUsers", listaUsers);
		return "users/listaUsers";

	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				uService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un usuario");
		}
		model.put("listaUsers", uService.listar());

		return "redirect:/users/listar";
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Users> objUsu = uService.listarid(id);

		if (objUsu == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/users/listar";
		} else {
			model.addAttribute("users", objUsu.get());
			return "users/users";
		}
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Users> Users = uService.listarid(id);
		if (Users == null) {
			flash.addFlashAttribute("error", "El user no existe en la base de datos");
			return "redirect:/users/listar";
		}

		model.put("users", Users.get());

		return "users/ver";
	}
}
