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

import pe.edu.upc.entity.Producto;
import pe.edu.upc.service.IProductoService;
import pe.edu.upc.service.ITproductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	private IProductoService pService;

	@Autowired
	private ITproductoService tService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevoCliente(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaTproductos", tService.listar());
		return "producto/producto";
	}

	@PostMapping("/guardar")
	public String guardarCategoria(@Valid Producto producto, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaTproductos", tService.listar());
			return "producto/producto";
		} else {
			int rpta = pService.insertar(producto);
			if (rpta > 0) {
				model.addAttribute("listaTproductos", tService.listar());
				model.addAttribute("mensaje", "Ya existe");
				return "/producto/producto";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}

		}
		model.addAttribute("listaProductos", pService.listar());

		if (result.hasErrors()) {
			model.addAttribute("listaTproductos", tService.listar());
			return "/producto/producto";
		} else {
			pService.insertar(producto);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/productos/listar";
		}
	}

	@GetMapping("/listar")
	public String listarProductos(Model model) {
		try {
			model.addAttribute("producto", new Producto());
			model.addAttribute("listaProductos", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/producto/listaProducto";
	}

	@GetMapping("/detalle/{id}")
	public String detailsProducto(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Producto> producto = pService.listarId(id);
			if (!producto.isPresent()) {
				model.addAttribute("info", "Producto no existe");
				return "redirect:/productos/listar";
			} else {
				model.addAttribute("producto", producto.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/producto/producto";
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				pService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un producto");
		}
		model.put("listaProductos", pService.listar());

		return "redirect:/productos/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Producto producto) throws ParseException {

		List<Producto> listaProductos;

		producto.setNombreProducto(producto.getNombreProducto());
		listaProductos = pService.buscarnombreProducto(producto.getNombreProducto());

		if (listaProductos.isEmpty()) {
			listaProductos = pService.buscarmarcaProducto(producto.getNombreProducto());
		}

		if (listaProductos.isEmpty()) {
			listaProductos = pService.buscartipoProducto(producto.getNombreProducto());
		}

		if (listaProductos.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaProductos", listaProductos);
		return "producto/listaProducto";

	}
}