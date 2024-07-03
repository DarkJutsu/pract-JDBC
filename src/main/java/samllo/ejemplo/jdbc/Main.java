package samllo.ejemplo.jdbc;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
/*      MOSTRAR LOS DRIVERS INSTALADOS
        for (Driver d : DriverManager.drivers().toList()) {
            System.out.println(d.toString());
        }
*/

        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TestJDBC", "sa", "123");
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("select * from alumno")) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String fechaN = rs.getString("fecha");
                System.out.println("Hola " + nombre + " " + apellido + " tu naciste " + fechaN);
            }
        }


    }
}