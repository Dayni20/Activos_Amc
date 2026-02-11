package com.uisrael.consumogestionactivosapi.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	private final SesionUsuario sesionUsuario;

	public AuthInterceptor(SesionUsuario sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!sesionUsuario.isAutenticado()) {
			response.sendRedirect("/login");
			return false;
		}

		return true;
	}
}
