package com.magdezis.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConfig {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = DatabaseConfig.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException("No se encontró db.properties");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer db.properties", e);
        }
    }

    private DatabaseConfig() { }

    public static Connection getConnection() throws SQLException {
        cargarDriver();
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password"));
    }

    private static void cargarDriver() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL (" + DRIVER + ") en el classpath de ejecución", e);
        }
    }
}