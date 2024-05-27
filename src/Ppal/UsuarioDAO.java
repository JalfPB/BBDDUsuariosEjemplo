/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ppal;

/**
 *
 * @author Alfon
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String URL = "jdbc:sqlite:src/Ppal/res/BDTest";

    private Connection connect() {
        // Establecer la conexión con la base de datos
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public Usuario verificarCredenciales(String usuario, String contrasena) {
    String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
    try (Connection conn = this.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, usuario);
        pstmt.setString(2, contrasena);

        // Depuración: imprimir consulta
        System.out.println("Consulta: " + pstmt.toString());

        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("dni"),
                rs.getString("usuario"),
                rs.getString("contraseña"),
                rs.getBoolean("admin")
            );
        } else {
            // Depuración: No se encontró el usuario
            System.out.println("No se encontró el usuario.");
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
    }


    public void anadirUsuario(String nombre, String apellido1, String apellido2, String dni, String usuario, String contrasena, boolean admin) {
        String sql = "INSERT INTO usuarios(nombre, apellido1, apellido2, dni, usuario, contraseña, admin) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido1);
            pstmt.setString(3, apellido2);
            pstmt.setString(4, dni);
            pstmt.setString(5, usuario);
            pstmt.setString(6, contrasena);
            pstmt.setBoolean(7, admin);
            pstmt.executeUpdate();
            System.out.println("Usuario añadido exitosamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido1"),
                    rs.getString("apellido2"),
                    rs.getString("dni"),
                    rs.getString("usuario"),
                    rs.getString("contraseña"),
                    rs.getBoolean("admin")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return usuarios;
    }
}

