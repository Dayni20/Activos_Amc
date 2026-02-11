package com.uisrael.consumogestionactivosapi.controlador;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.consumogestionactivosapi.modelo.dto.request.ProveedoresRequestDTO;
import com.uisrael.consumogestionactivosapi.modelo.dto.response.ProveedoresResponseDTO;
import com.uisrael.consumogestionactivosapi.security.SesionUsuario;
import com.uisrael.consumogestionactivosapi.service.IProveedoresServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/proveedores") // url
public class ProveedoresControlador {

	private final IProveedoresServicio servicioProveedores;
	private final SesionUsuario sesionUsuario;

	@ModelAttribute("sesionUsuario")
	public SesionUsuario obtenerSesionUsuario() {
		return sesionUsuario;
	}

	@GetMapping
	public String listarProveedores(Model model) {
		List<ProveedoresResponseDTO> contenidoBD=	servicioProveedores.listarProveedores();
		model.addAttribute("listarproveedores", contenidoBD);
		return "proveedores/listarProveedores"; // ubicacion fisica page
	}

	 //GET: muestra el formulario
    @GetMapping("/nuevoProveedores")
    public String nuevoProveedores(Model model) {
        model.addAttribute("nuevoproveedor", new ProveedoresRequestDTO());
        return "proveedores/nuevoProveedores";  // ubicacion fisica page
    }


    //POST: guarda en BD
    @PostMapping
    public String guardarProveedores(@ModelAttribute("nuevoproveedor") ProveedoresRequestDTO nuevoproveedor,
                                     Model model) {
        try {
            servicioProveedores.nuevoProveedores(nuevoproveedor);
            return "redirect:/proveedores";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "proveedores/nuevoProveedores";
        }
    }

	//GET: EDITAR
		@GetMapping("/editar/{id}")
		public String editarProveedores(@PathVariable Integer id, Model model) {

		    ProveedoresResponseDTO proveedores = servicioProveedores.obtenerProveedor(id);

		    model.addAttribute("nuevoproveedor", proveedores);

		    return "proveedores/editarProveedores";
		}
		//POST ACTUALIZAR
		@PostMapping("/actualizar/{id}")
		public String actualizarProveedor(@PathVariable Integer id,
		                                  @ModelAttribute("nuevoproveedor") ProveedoresRequestDTO dto,
		                                  Model model) {
		    try {
		        servicioProveedores.actualizarProveedor(id, dto);
		        return "redirect:/proveedores";
		    } catch (IllegalStateException e) {
		        model.addAttribute("error", e.getMessage());
		        model.addAttribute("nuevoproveedor", dto);
		        return "proveedores/editarProveedores";
		    }
		}

		// POST:ELIMINAR
		@PostMapping("/eliminar/{id}")
		public String eliminarProveedor(@PathVariable Integer id, Model model) {
			try {
				servicioProveedores.eliminarProveedor(id);
				return "redirect:/proveedores";
			} catch (RuntimeException e) {
				List<ProveedoresResponseDTO> contenidoBD = servicioProveedores.listarProveedores();
				model.addAttribute("listarproveedores", contenidoBD);
				model.addAttribute("errorEliminar", e.getMessage());
				return "proveedores/listarProveedores";
			}
}
}