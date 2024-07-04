package samllo.ejemplo.jdbc;

import java.sql.*;
import java.util.Properties;

public class Main {
    private static Properties dataBaseProperties() {
        Properties p = new Properties();
        p.setProperty("user", "sa");
        p.setProperty("password", "123");
        return p;
    }

    public static void main(String[] args) throws SQLException {
/*      MOSTRAR LOS DRIVERS INSTALADOS
        for (Driver d : DriverManager.drivers().toList()) {
            System.out.println(d.toString());
        }
*/
        Properties props = dataBaseProperties();
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TestJDBC", props);
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