package com.magdezis.repository;

import com.magdezis.config.DatabaseConfig;
import com.magdezis.model.TipoRol;
import com.magdezis.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    private static final String SQL_BUSCAR_POR_CORREO =
            "select u.id, u.nombre, u.apellido, u.correo, u.password_hash, r.nombre as rol "
          + "from usuarios u "
          + "inner join roles r on r.id = u.rol_id "
          + "where u.correo = ?";

    private static final String SQL_EXISTE_CORREO =
            "select 1 from usuarios where correo = ?";

    private static final String SQL_INSERTAR =
            "insert into usuarios (nombre, apellido, correo, password_hash, rol_id) "
          + "values (?, ?, ?, ?, (select id from roles where nombre = ?))";

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_POR_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar el usuario", e);
        }
    }

    @Override
    public boolean existeCorreo(String correo) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_EXISTE_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar el correo", e);
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getPasswordHash());
            ps.setString(5, usuario.getRol().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar el usuario", e);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setRol(TipoRol.valueOf(rs.getString("rol")));
        return usuario;
    }
}
