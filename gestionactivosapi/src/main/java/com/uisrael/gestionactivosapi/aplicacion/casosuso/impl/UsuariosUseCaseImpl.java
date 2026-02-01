package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUsuariosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUsuariosRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IRolesRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IDepartamentosRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.RolesJpa;

public class UsuariosUseCaseImpl implements IUsuariosUseCase {
    
	private final IUsuariosRepositorio repositorio;
	private final IRolesRepositorio rolesRepositorio;
	private final IDepartamentosRepositorio departamentosRepositorio;
	private final PasswordEncoder passwordEncoder;

	public UsuariosUseCaseImpl(IUsuariosRepositorio repositorio, IRolesRepositorio rolesRepositorio,
			IDepartamentosRepositorio departamentosRepositorio, PasswordEncoder passwordEncoder) {
		this.repositorio = repositorio;
		this.rolesRepositorio = rolesRepositorio;
		this.departamentosRepositorio = departamentosRepositorio;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Usuarios crear(Usuarios usuario) {
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			if (rolesRepositorio.buscarPorId(idRol).isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			if (departamentosRepositorio.buscarPorId(idDep).isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
		}
		
		if (repositorio.buscarPorCorreo(usuario.getCorreo()).isPresent()) {
			throw new IllegalArgumentException("Ya existe un usuario con el correo: " + usuario.getCorreo());
		}

		String contrasenaEncriptada = passwordEncoder.encode(usuario.getContrasena());
		
		RolesJpa rol = usuario.getFkRol();
		DepartamentosJpa departamento = usuario.getFkDepartamento();
		Usuarios usuarioConContrasenaEncriptada = new Usuarios(
			usuario.getIdUsuario(),
			usuario.getNombre(),
			usuario.getCorreo(),
			contrasenaEncriptada,
			usuario.isEstado(),
			departamento,
			rol
		);

		return repositorio.guardar(usuarioConContrasenaEncriptada);
	}

	@Override
	public Usuarios obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	@Override
	public List<Usuarios> listar() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int id) {
		repositorio.eliminar(id);
	}

	@Override
	public Usuarios actualizar(Usuarios usuario) {
		Usuarios usuarioExistente = repositorio.buscarPorId(usuario.getIdUsuario())
			.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario.getIdUsuario()));
		
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			if (rolesRepositorio.buscarPorId(idRol).isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			if (departamentosRepositorio.buscarPorId(idDep).isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
		}
		
		repositorio.buscarPorCorreo(usuario.getCorreo()).ifPresent(usuarioConCorreo -> {
			if (usuarioConCorreo.getIdUsuario() != usuario.getIdUsuario()) {
				throw new IllegalArgumentException("Ya existe otro usuario con el correo: " + usuario.getCorreo());
			}
		});

		String contrasenaFinal = usuarioExistente.getContrasena();
		if (!usuario.getContrasena().equals(usuarioExistente.getContrasena())) {
			contrasenaFinal = passwordEncoder.encode(usuario.getContrasena());
		}
		
		RolesJpa rol = usuario.getFkRol();
		DepartamentosJpa departamento = usuario.getFkDepartamento();
		Usuarios usuarioActualizado = new Usuarios(
			usuario.getIdUsuario(),
			usuario.getNombre(),
			usuario.getCorreo(),
			contrasenaFinal,
			usuario.isEstado(),
			departamento,
			rol
		);

		return repositorio.guardar(usuarioActualizado);
	}

}
