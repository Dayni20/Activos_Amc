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
		if (!usuario.isEstado()) {
			throw new IllegalArgumentException("Solo se pueden crear usuarios en estado activo");
		}
		
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			var rolOpt = rolesRepositorio.buscarPorId(idRol);
			if (rolOpt.isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
			if (!rolOpt.get().isEstado()) {
				throw new IllegalArgumentException("No se puede asignar un rol inactivo");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			var depOpt = departamentosRepositorio.buscarPorId(idDep);
			if (depOpt.isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
			if (!depOpt.get().isEstado()) {
				throw new IllegalArgumentException("No se puede asignar un departamento inactivo");
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
		Usuarios usuario = repositorio.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
		
		// Validar que el usuario esté activo antes de desactivarlo
		if (!usuario.isEstado()) {
			throw new IllegalArgumentException("Este usuario ya se encuentra inactivo");
		}
		
		// Eliminado lógico: cambiar estado a false
		Usuarios usuarioInactivo = new Usuarios(
			usuario.getIdUsuario(),
			usuario.getNombre(),
			usuario.getCorreo(),
			usuario.getContrasena(),
			false,
			usuario.getFkDepartamento(),
			usuario.getFkRol()
		);
		repositorio.guardar(usuarioInactivo);
	}

	@Override
	public Usuarios actualizar(Usuarios usuario) {
		Usuarios usuarioExistente = repositorio.buscarPorId(usuario.getIdUsuario())
			.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario.getIdUsuario()));
		
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			var rolOpt = rolesRepositorio.buscarPorId(idRol);
			if (rolOpt.isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
			if (!rolOpt.get().isEstado()) {
				throw new IllegalArgumentException("No se puede asignar un rol inactivo");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			var depOpt = departamentosRepositorio.buscarPorId(idDep);
			if (depOpt.isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
			if (!depOpt.get().isEstado()) {
				throw new IllegalArgumentException("No se puede asignar un departamento inactivo");
			}
		}
		
		repositorio.buscarPorCorreo(usuario.getCorreo()).ifPresent(usuarioConCorreo -> {
			if (usuarioConCorreo.getIdUsuario() != usuario.getIdUsuario()) {
				throw new IllegalArgumentException("Ya existe otro usuario con el correo: " + usuario.getCorreo());
			}
		});

		String contrasenaFinal = usuarioExistente.getContrasena();
		// Solo actualizar contraseña si se proporciona una nueva (no vacía)
		if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty() 
			&& !usuario.getContrasena().equals(usuarioExistente.getContrasena())) {
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
