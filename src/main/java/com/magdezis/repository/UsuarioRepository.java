package com.magdezis.repository;

import com.magdezis.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> buscarPorCorreo(String correo);
    boolean existeCorreo(String correo);
    void guardar(Usuario usuario);
}
