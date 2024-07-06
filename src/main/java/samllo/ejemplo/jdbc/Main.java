package samllo.ejemplo.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static Properties dataBaseProperties() {
        Properties p = new Properties();
        p.setProperty("user", "sa");
        p.setProperty("password", "123");
        return p;
    }

    private static Connection myConnection(Properties props) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/TestJDBC", props);
    }

    private static void queryPrep(Connection conn, int yearMeno) {
        String querySql = "SELECT * FROM alumno WHERE DATE_PART('year',fecha)<?";
        try (PreparedStatement ps = conn.prepareStatement(querySql)) {
            ps.setInt(1, yearMeno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    Date fechaN = rs.getDate("fecha");
                    System.out.println(nombre + " " + apellido + ", Fecha de nacimiento: " + fechaN);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertTo(Connection conn, String nombre, String apellido, Date fechaN) {
        String sql = "INSERT INTO alumno(nombre, apellido, fecha) values(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setDate(3, fechaN);
            int count = ps.executeUpdate();
            System.out.println("Número de filas actualizada/s: " + count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
/*      MOSTRAR LOS DRIVERS INSTALADOS
        for (Driver d : DriverManager.drivers().toList()) {
            System.out.println(d.toString());
        }
*/
        System.out.println("Nacidos antes que el año: ");
        Scanner input = new Scanner(System.in);
        try (Connection conn = myConnection(dataBaseProperties())) {
            insertTo(conn, "Karen", "del Carmen", Date.valueOf("1990-04-11"));
            queryPrep(conn, input.nextInt());
        }
    }
}