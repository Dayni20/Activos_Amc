package com.uisrael.consumogestionactivosapi.controlador;

import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.consumogestionactivosapi.security.SesionUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthControlador {

	private final SesionUsuario sesionUsuario;

	public AuthControlador(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	@GetMapping("/")
	public String redirigirInicio() {
		if (sesionUsuario.isAutenticado()) {
			return "redirect:/inicio";
		}
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String mostrarLogin(Model model, @RequestParam(required = false) String error) {
		if (sesionUsuario.isAutenticado()) {
			return "redirect:/inicio";
		}
		if (error != null) {
			model.addAttribute("error", "Credenciales incorrectas. Por favor, intente nuevamente.");
		}

		return "auth/login";
	}

	@PostMapping("/login")
	public String procesarLogin(
			@RequestParam String correo,
			@RequestParam String contrasena,
			Model model,
			HttpSession session) {

		try {
			String credenciales = correo + ":" + contrasena;
			String credencialesBase64 = Base64.getEncoder().encodeToString(credenciales.getBytes());

			WebClient clienteTemp = WebClient.builder()
					.baseUrl("http://localhost:8080/api")
					.defaultHeader("Authorization", "Basic " + credencialesBase64)
					.build();

			try {
				Map<String, Object> respuestaUsuario = clienteTemp.get()
						.uri("/auth/yo")
						.retrieve()
						.bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>(){})
						.block();

				if (respuestaUsuario != null) {
					String nombreUsuario = (String) respuestaUsuario.getOrDefault("nombreUsuario", correo.split("@")[0]);
					String rol = (String) respuestaUsuario.getOrDefault("rol", "AUDITOR");
					sesionUsuario.iniciarSesion(correo, contrasena, nombreUsuario, rol);
					return "redirect:/inicio";
				} else {
					String nombreUsuario = correo.split("@")[0];
					sesionUsuario.iniciarSesion(correo, contrasena, nombreUsuario, "AUDITOR");
					return "redirect:/inicio";
				}

			} catch (WebClientResponseException e) {
				return "redirect:/login?error";
			}

		} catch (Exception e) {
			model.addAttribute("error", "Error al procesar el login. Verifique que la API esté funcionando.");
			return "auth/login";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesion(HttpSession session) {
		sesionUsuario.cerrarSesion();
		session.invalidate();
		return "redirect:/login";
	}
}

