package pe.edu.upc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pe.edu.upc.entity.Proyecto;
import pe.edu.upc.service.IAreaService;
import pe.edu.upc.service.IProductoService;
import pe.edu.upc.service.IProyectoService;
import pe.edu.upc.service.IUserService;


@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

	@Autowired
	private IProyectoService pService;

	@Autowired
	private IUserService uService;

	@Autowired
	private IAreaService arService;

	@Autowired
	private IProductoService prService;
	
	@Autowired
	private ServletContext context;

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ADMIN" })
	@GetMapping("/nuevo")
	public String nuevoProyecto(Model model) {
		model.addAttribute("proyecto", new Proyecto());
		model.addAttribute("listaUsers", uService.listar());
		model.addAttribute("listaAreas", arService.listar());
		model.addAttribute("listaProductos", prService.listar());
		return "proyecto/proyecto";
	}

	@PostMapping("/guardar")
	public String guardarProyecto(@Valid Proyecto proyecto, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaUsuarios", uService.listar());
			model.addAttribute("listaAreas", arService.listar());
			model.addAttribute("listaProductos", prService.listar());
			return "proyecto/proyecto";
		} else {
			int rpta = pService.insertar(proyecto);
			if (rpta > 0) {
				model.addAttribute("listaUsuarios", uService.listar());
				model.addAttribute("listaAreas", arService.listar());
				model.addAttribute("listaProductos", prService.listar());
				model.addAttribute("mensaje", "Ya existe");
				return "/proyecto/proyecto";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}

		}
		model.addAttribute("listaProyectos", pService.listar());

		if (result.hasErrors()) {
			model.addAttribute("listaUsuarios", uService.listar());
			return "/proyecto/proyecto";
		}

		if (result.hasErrors()) {
			model.addAttribute("listaAreas", arService.listar());
			return "/proyecto/proyecto";
		}
		if (result.hasErrors()) {
			model.addAttribute("listaProductos", prService.listar());
			return "/proyecto/proyecto";
		} else {
			pService.insertar(proyecto);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/proyectos/listar";
		}
	}

	@GetMapping("/listar")
	public String listarProyectos(Model model) {
		try {
			model.addAttribute("proyecto", new Proyecto());
			model.addAttribute("listaProyectos", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/proyecto/listaProyecto";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ADMIN" })
	@GetMapping("/detalle/{id}")
	public String detailsProyecto(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Proyecto> proyecto = pService.listarId(id);

			if (!proyecto.isPresent()) {
				model.addAttribute("info", "Proyecto no existe");
				return "redirect:/proyectos/listar";
			} else {
				model.addAttribute("proyecto", proyecto.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/proyecto/proyecto";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ADMIN" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				pService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un proyecto");
		}
		model.put("listaProyectos", pService.listar());

		return "redirect:/proyectos/listar";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ENCARGADO" , "ROLE_ADMIN" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Proyecto proyecto) throws ParseException {

		List<Proyecto> listaProyectos;
		proyecto.setNombreProyecto(proyecto.getNombreProyecto());
		listaProyectos = pService.buscar(proyecto.getNombreProyecto());

		if (listaProyectos.isEmpty()) {
			listaProyectos = pService.buscarUsuario(proyecto.getNombreProyecto());
		}

		if (listaProyectos.isEmpty()) {
			listaProyectos = pService.buscarArea(proyecto.getNombreProyecto());
		}

		if (listaProyectos.isEmpty()) {
			listaProyectos = pService.buscarProducto(proyecto.getNombreProyecto());
		}

		if (listaProyectos.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaProyectos", listaProyectos);
		return "proyecto/listaProyecto";
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Proyecto> proyecto = pService.listarId(id);
		if (proyecto == null) {
			flash.addFlashAttribute("error", "El Proyecto no existe en la base de datos");
			return "redirect:/proyectos/listar";
		}

		model.put("proyecto", proyecto.get());

		return "proyecto/ver";
	}

	@Secured({ "ROLE_JEFE", "ROLE_ADMIN" })
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Proyecto> objPro = pService.listarId(id);

		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/proyectos/listar";
		} else {
			model.addAttribute("listaUsuarios", uService.listar());
			model.addAttribute("listaAreas", arService.listar());
			model.addAttribute("listaProductos", prService.listar());
			model.addAttribute("proyecto", objPro.get());
			return "proyecto/proyecto";
		}
	}
	
	@RequestMapping(value = "/downloadPDF/{id}", method = RequestMethod.GET)
	public void downloadPDF(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		Proyecto proyecto = pService.listarId(id).get();
		if (proyecto == null) {
			return;
		}

		Document document = new Document(PageSize.A4, 10, 10, 30, 30);
		String fileName = "informeProyecto.pdf";
		String filePath = context.getRealPath("/Download");

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + fileName));
			document.open();

			Font titleFont = FontFactory.getFont("Calibri", 18, Font.BOLD, BaseColor.BLACK);
			Paragraph paragraph = new Paragraph("Informe de proyecto", titleFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingAfter(10);

			document.add(paragraph);

			PdfPTable table = new PdfPTable(2);
			table.setSpacingBefore(10);

			Font headerFont = FontFactory.getFont("Calibri", 12, Font.BOLD, BaseColor.BLACK);
			Font bodyFont = FontFactory.getFont("Calibri", 10, BaseColor.BLACK);

			PdfPCell codeCell = new PdfPCell(new Paragraph("Código", headerFont));
			codeCell.setBorderColor(BaseColor.BLACK);
			codeCell.setBorderWidth(2);
			codeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			codeCell.setVerticalAlignment(Element.ALIGN_CENTER);
			codeCell.setExtraParagraphSpace(5f);

			PdfPCell nameCell = new PdfPCell(new Paragraph("Proyecto", headerFont));
			nameCell.setBorderColor(BaseColor.BLACK);
			nameCell.setBorderWidth(2);
			nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
			nameCell.setExtraParagraphSpace(5f);

			PdfPCell descriptionCell = new PdfPCell(new Paragraph("Descripción de proyecto", headerFont));
			descriptionCell.setBorderColor(BaseColor.BLACK);
			descriptionCell.setBorderWidth(2);
			descriptionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			descriptionCell.setVerticalAlignment(Element.ALIGN_CENTER);
			descriptionCell.setExtraParagraphSpace(5f);

			PdfPCell responsibleCell = new PdfPCell(new Paragraph("Responable", headerFont));
			responsibleCell.setBorderColor(BaseColor.BLACK);
			responsibleCell.setBorderWidth(2);
			responsibleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			responsibleCell.setVerticalAlignment(Element.ALIGN_CENTER);
			responsibleCell.setExtraParagraphSpace(5f);

			PdfPCell areaCell = new PdfPCell(new Paragraph("Área", headerFont));
			areaCell.setBorderColor(BaseColor.BLACK);
			areaCell.setBorderWidth(2);
			areaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			areaCell.setVerticalAlignment(Element.ALIGN_CENTER);
			areaCell.setExtraParagraphSpace(5f);

			PdfPCell productCell = new PdfPCell(new Paragraph("Producto", headerFont));
			productCell.setBorderColor(BaseColor.BLACK);
			productCell.setBorderWidth(2);
			productCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productCell.setVerticalAlignment(Element.ALIGN_CENTER);
			productCell.setExtraParagraphSpace(5f);

			PdfPCell productQuantityCell = new PdfPCell(new Paragraph("Cantidad de Productos", headerFont));
			productQuantityCell.setBorderColor(BaseColor.BLACK);
			productQuantityCell.setBorderWidth(2);
			productQuantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productQuantityCell.setVerticalAlignment(Element.ALIGN_CENTER);
			productQuantityCell.setExtraParagraphSpace(5f);

			PdfPCell startDateCell = new PdfPCell(new Paragraph("Fecha inicio", headerFont));
			startDateCell.setBorderColor(BaseColor.BLACK);
			startDateCell.setBorderWidth(2);
			startDateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			startDateCell.setVerticalAlignment(Element.ALIGN_CENTER);
			startDateCell.setExtraParagraphSpace(5f);

			PdfPCell endDateCell = new PdfPCell(new Paragraph("Fecha Fín", headerFont));
			endDateCell.setBorderColor(BaseColor.BLACK);
			endDateCell.setBorderWidth(2);
			endDateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			endDateCell.setVerticalAlignment(Element.ALIGN_CENTER);
			endDateCell.setExtraParagraphSpace(5f);

			PdfPCell codeValueCell = new PdfPCell(new Paragraph(Integer.toString(proyecto.getIdProyecto()), bodyFont));
			codeValueCell.setBorderColor(BaseColor.BLACK);
			codeValueCell.setBorderWidth(2);
			codeValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			codeValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			codeValueCell.setExtraParagraphSpace(5f);

			PdfPCell nameValueCell = new PdfPCell(new Paragraph(proyecto.getNombreProyecto(), bodyFont));
			nameValueCell.setBorderColor(BaseColor.BLACK);
			nameValueCell.setBorderWidth(2);
			nameValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			nameValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			nameValueCell.setExtraParagraphSpace(5f);

			PdfPCell descriptionValueCell = new PdfPCell(new Paragraph(proyecto.getDescripcionProyecto(), bodyFont));
			descriptionValueCell.setBorderColor(BaseColor.BLACK);
			descriptionValueCell.setBorderWidth(2);
			descriptionValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			descriptionValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			descriptionValueCell.setExtraParagraphSpace(5f);

			PdfPCell responsibleValueCell = new PdfPCell(
					new Paragraph(proyecto.getUsuario().getNombreUsuario(), bodyFont));
			responsibleValueCell.setBorderColor(BaseColor.BLACK);
			responsibleValueCell.setBorderWidth(2);
			responsibleValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			responsibleValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			responsibleValueCell.setExtraParagraphSpace(5f);

			PdfPCell areaValueCell = new PdfPCell(new Paragraph(proyecto.getArea().getNombreArea(), bodyFont));
			areaValueCell.setBorderColor(BaseColor.BLACK);
			areaValueCell.setBorderWidth(2);
			areaValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			areaValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			areaValueCell.setExtraParagraphSpace(5f);

			PdfPCell productValueCell = new PdfPCell(
					new Paragraph(proyecto.getProducto().getNombreProducto(), bodyFont));
			productValueCell.setBorderColor(BaseColor.BLACK);
			productValueCell.setBorderWidth(2);
			productValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			productValueCell.setExtraParagraphSpace(5f);

			PdfPCell productQuantityValueCell = new PdfPCell(
					new Paragraph(Integer.toString(proyecto.getCantidadProducto()), bodyFont));
			productQuantityValueCell.setBorderColor(BaseColor.BLACK);
			productQuantityValueCell.setBorderWidth(2);
			productQuantityValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productQuantityValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			productQuantityValueCell.setExtraParagraphSpace(5f);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			PdfPCell startDateValueCell = new PdfPCell(
					new Paragraph(dateFormat.format(proyecto.getFechaInicio()), bodyFont));
			startDateValueCell.setBorderColor(BaseColor.BLACK);
			startDateValueCell.setBorderWidth(2);
			startDateValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			startDateValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			startDateValueCell.setExtraParagraphSpace(5f);

			PdfPCell endDateValueCell = new PdfPCell(
					new Paragraph(dateFormat.format(proyecto.getFechaFin()), bodyFont));
			endDateValueCell.setBorderColor(BaseColor.BLACK);
			endDateValueCell.setBorderWidth(2);
			endDateValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			endDateValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
			endDateValueCell.setExtraParagraphSpace(5f);

			table.addCell(codeCell);
			table.addCell(codeValueCell);
			table.addCell(nameCell);
			table.addCell(nameValueCell);
			table.addCell(descriptionCell);
			table.addCell(descriptionValueCell);
			table.addCell(responsibleCell);
			table.addCell(responsibleValueCell);
			table.addCell(areaCell);
			table.addCell(areaValueCell);
			table.addCell(productCell);
			table.addCell(productValueCell);
			table.addCell(productQuantityCell);
			table.addCell(productQuantityValueCell);
			table.addCell(startDateCell);
			table.addCell(startDateValueCell);
			table.addCell(endDateCell);
			table.addCell(endDateValueCell);

			document.add(table);
			document.close();
			writer.close();

			final int BUFFER_SIZE = 4096;
			FileInputStream inputStream = new FileInputStream(file + "/" + fileName);
			String mimeType = context.getMimeType(filePath);
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachement; filename=" + fileName);
			OutputStream outputStream = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outputStream.close();
			file.delete();
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
