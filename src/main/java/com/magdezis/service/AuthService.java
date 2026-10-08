package com.magdezis.service;

import com.magdezis.dto.LoginRequest;
import com.magdezis.dto.RegistroRequest;
import com.magdezis.model.TipoRol;
import com.magdezis.model.Usuario;
import com.magdezis.repository.UsuarioRepository;
import java.util.Locale;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private static final int LONGITUD_MINIMA_PASSWORD = 8;
    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void registrar(RegistroRequest req) {
        validarRegistro(req);
        String correo = normalizarCorreo(req.getCorreo());

        if (usuarioRepository.existeCorreo(correo)) {
            throw new AuthException("El correo ya está registrado.");
        }

        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        Usuario usuario = new Usuario(
                req.getNombre().trim(),
                req.getApellido().trim(),
                correo,
                hash,
                TipoRol.USER);
        usuarioRepository.guardar(usuario);
    }

    public Usuario login(LoginRequest req) {
        if (esVacio(req.getCorreo()) || esVacio(req.getPassword())) {
            throw new AuthException("Ingrese correo y contraseña.");
        }

        return usuarioRepository.buscarPorCorreo(normalizarCorreo(req.getCorreo()))
                .filter(u -> BCrypt.checkpw(req.getPassword(), u.getPasswordHash()))
                .orElseThrow(() -> new AuthException("Correo o contraseña incorrectos."));
    }

    private void validarRegistro(RegistroRequest req) {
        if (esVacio(req.getNombre()) || esVacio(req.getApellido())
                || esVacio(req.getCorreo()) || esVacio(req.getPassword())) {
            throw new AuthException("Todos los campos son obligatorios.");
        }
        if (!PATRON_CORREO.matcher(req.getCorreo().trim()).matches()) {
            throw new AuthException("El correo no tiene un formato válido.");
        }
        if (req.getPassword().length() < LONGITUD_MINIMA_PASSWORD) {
            throw new AuthException("La contraseña debe tener al menos "
                    + LONGITUD_MINIMA_PASSWORD + " caracteres.");
        }
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
