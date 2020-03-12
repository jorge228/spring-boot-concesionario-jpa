package com.lozano.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lozano.springboot.app.models.entity.Coche;
import com.lozano.springboot.app.models.entity.Marca;
import com.lozano.springboot.app.models.service.ICocheService;
import com.lozano.springboot.app.models.service.IMarcaService;
import com.lozano.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes({"marca", "coche"})
public class PrincipalController {

	@Autowired
	private IMarcaService marcaService;
	
	@Autowired
	private ICocheService cocheService;
	
	//------------------- INICIO HOME para marcas y COCHES para coches-------------------------------------
	@RequestMapping(value="/inicio", method=RequestMethod.GET)
	public String home(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 3);
		//aquí obtengo la lista paginada
		Page<Marca> marcas = marcaService.findAll(pageRequest);
		PageRender<Marca> pageRender = new PageRender<>("/inicio", marcas);
		model.addAttribute("titulo", "Listado de Marcas");
		model.addAttribute("marcas",marcas);
		model.addAttribute("page",pageRender);
		return "inicio";
	}
	@RequestMapping(value="/coches", method=RequestMethod.GET)
	public String coches(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Coche> coches = cocheService.findAll(pageRequest);
		PageRender<Coche> pageRender = new PageRender<>("/coches", coches);
		model.addAttribute("titulo", "Listado de Marcas");
		model.addAttribute("coches",coches);
		model.addAttribute("page",pageRender);
		return "coches";
	}
	
	//------------------- INICIO MARCAS-------------------------------------
	@GetMapping(value="/vermarca/{id}")
	public String ver(@PathVariable(value="id") int id, Map<String, Object> model, RedirectAttributes flash) {
		Marca marca = marcaService.findOne(id);
		if(marca==null) {
			flash.addFlashAttribute("error", "La marca no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("marca", marca);
		model.put("titulo", "Información sobre " + marca.getNombre());
		return "vermarca";
	}
	
	
	@RequestMapping(value="/listarmarcas", method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		//aquí obtengo la lista paginada
		Page<Marca> marcas = marcaService.findAll(pageRequest);
		PageRender<Marca> pageRender = new PageRender<>("/listarmarcas", marcas);
		model.addAttribute("titulo", "Listado de marcas");
		model.addAttribute("marcas",marcas);
		model.addAttribute("page",pageRender);
		return "listarmarcas";
	}
	
	@RequestMapping(value="/formmarca/{id}")
	public String editarMarca(@PathVariable(value="id") int id, Map<String, Object> model, RedirectAttributes flash) {
		Marca marca =null;
		if(id>0) {
			marca = marcaService.findOne(id);
			if(marca == null) {
				flash.addFlashAttribute("error", "El ID de la marca no existe en la base de datos");
			}
		}else {
			flash.addFlashAttribute("error", "El ID de la marca no puede ser 0");
			return "redirect:/listarmarcas";
		}
		model.put("titulo", "Editar marca");
		model.put("marca", marca);
		return "formmarca";
	}
	
	@RequestMapping(value="/formmarca")
	public String crearMarca(Map<String, Object> model) {
		Marca marca = new Marca();
		model.put("titulo", "Formulario de Marcas");
		model.put("marca", marca);
		return "formmarca";
	}

	@RequestMapping(value="/formmarca", method = RequestMethod.POST)
	public String guardarM(@Valid Marca marca, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Marcas");
			return "formmarca";
		}
		
		if(!foto.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");
				marca.setLogo(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		if(!foto.isEmpty()) {
			Path rootPath = Paths.get("uploads").resolve(foto.getOriginalFilename());
			Path rootAbsolutPaht = rootPath.toAbsolutePath();
			try {
				Files.copy(foto.getInputStream(), rootAbsolutPaht);
				flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");
				marca.setLogo(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		//hasta aquí
		String mensajeFlash = (marca.getId() >0)? "Marca editada con exíto" : "Marca creada con éxito";
		
		marcaService.save(marca);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarmarcas";
	}

	@RequestMapping(value="/eliminarmarca/{id}")
	public String eliminarMarca(@PathVariable(value="id") int id, RedirectAttributes flash) {
		if(id > 0) {
			//cocheService.deleteCocheIdMarca(id);
			marcaService.delete(id);
			flash.addFlashAttribute("success", "Marca eliminada con éxito");
		}
		return "redirect:/listarmarcas";
	}
	
	//-------------------FIN MARCAS-------------------------------------
	
	//-------------------INICIO COCHES-------------------------------------
	@GetMapping(value="/vercoche/{id}")
	public String verCoche(@PathVariable(value="id") int id, Map<String, Object> model, RedirectAttributes flash) {
		Coche coche = cocheService.findOne(id);
		if(coche==null) {
			flash.addFlashAttribute("error", "El coche no existe en la base de datos");
			return "redirect:/inicio";
		}
		
		model.put("coche", coche);
		model.put("titulo", "Información sobre " + coche.getModelo());
		return "vercoche";
	}
	
	@RequestMapping(value="/listarcoches", method=RequestMethod.GET)
	public String listarC(@RequestParam(name="page", defaultValue="0") int page,Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Coche> coches =cocheService.findAll(pageRequest);
		PageRender<Coche> pageRender = new PageRender<>("/listarcoches", coches);
		model.addAttribute("titulo", "Listado de coches");
		model.addAttribute("coches", coches);
		model.addAttribute("page",pageRender);
		return "listarcoches";
	}
	
	@RequestMapping(value="/formcoche/{id}")
	public String editarCoche(@PathVariable(value="id") int id, Map<String, Object> model, RedirectAttributes flash) {
		Coche coche =null;
		if(id>0) {
			coche = cocheService.findOne(id);
			if(coche == null) {
				flash.addFlashAttribute("error", "El ID del coche no existe en la base de datos");
			}
		}else {
			flash.addFlashAttribute("error", "El ID del coche no puede ser 0");
			return "redirect:/listarcoches";
		}
		model.put("marcas", marcaService.findAll());
		model.put("titulo", "Editar coche");
		model.put("coche", coche);
		return "formcoche";
	}
	
	@RequestMapping(value="/formcoche")
	public String creaCoche(Map <String, Object> model) {
		Coche coche = new Coche();
		model.put("titulo", "Formulario de Coches");
		model.put("coche", coche);
		model.put("marcas", marcaService.findAll());
		return "formcoche";
	}
	
	@RequestMapping(value="/formcoche", method = RequestMethod.POST)
	public String guardarC(@Valid Coche coche, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, HttpServletRequest request, SessionStatus status, RedirectAttributes flash) {
		/*if(result.hasErrors()) {
			int numero = Integer.parseInt(request.getParameter("idMarca"));
			model.addAttribute("titulo", "Formulario de Coches");
			model.addAttribute("marcas", marcaService.findOne(numero));
			return "formcoche";
		}*/
		if(!foto.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");
				coche.setImagen(foto.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String mensajeFlash = (coche.getId() >0)? "Coche editado con exíto" : "Coche creado con éxito";

		Marca marca = new Marca();
		if(request.getParameter("idMarca") == null) {
			marca = marcaService.findOne(coche.getMarca());
		}else {
			marca = marcaService.findOne(Integer.parseInt(request.getParameter("idMarca")));
		}
		coche.setMarca(marca);
		cocheService.save(coche);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarcoches";
	}
	
	@RequestMapping(value="/eliminarcoche/{id}")
	public String eliminarCoche(@PathVariable(value="id") int id, RedirectAttributes flash) {
		if(id > 0) {
			cocheService.delete(id);
			//List<Coche> c = cocheService.findByIdMarca(1);
			//System.out.println("ENTRA" + c.size());
			flash.addFlashAttribute("success", "Coche eliminado con éxito");
		}
		return "redirect:/listarcoches";
	}
	//-------------------FIN COCHES-------------------------------------
	
	//-------------------COCHES POR MARCA-------------------------------------
	
	@RequestMapping(value="/marca/{id}", method=RequestMethod.GET)
	public String prueba(@PathVariable(value="id") int id, Model model) {
		List<Coche> coches = cocheService.findAll();
		Iterator<Coche> it = coches.iterator();
		while(it.hasNext()) {
			if(it.next().getMarca()!=id) {
				it.remove();
			}
		}
		String nombre = marcaService.findOne(id).getNombre();
		model.addAttribute("titulo", "Listado de Coches de " + nombre);
		model.addAttribute("coches",coches);
		return "marca";
	}

}
