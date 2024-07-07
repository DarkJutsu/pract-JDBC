package samllo.ejemplo.jdbc;

import utils.Filas;

import java.sql.*;
import java.util.Properties;

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

    private static void queryPrep(Connection conn) {
        String querySql = "SELECT * FROM alumno WHERE DATE_PART('year',fecha) < ?";
        try (PreparedStatement ps = conn.prepareStatement(querySql)) {
            ps.setInt(1, 2000);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Filas.alumnos(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void queryPrepScroll(Connection conn) {
        String querySql = "SELECT * FROM alumno";
        try (PreparedStatement ps = conn.prepareStatement(querySql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet rs = ps.executeQuery()) {
                rs.afterLast();
                while (rs.previous()) {
                    Filas.alumnos(rs);
                }
                while (rs.next()) {
                    Filas.alumnos(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertTo(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        Alumno[] alumnos = new Alumno[]{
                new Alumno("Angel", "Lopez", Date.valueOf("1991-02-12")),
                new Alumno("Kevin", "Gomez", Date.valueOf("1991-02-12")),
                new Alumno("Pedro", "Martines", Date.valueOf("1991-02-12")),
                new Alumno("Jose", null, Date.valueOf("1991-02-12")),
        };
        String sql = "INSERT INTO alumno(nombre, apellido, fecha) values(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int resCount = 0;
            for (Alumno alum : alumnos) {
                ps.setString(1, alum.nombre);
                ps.setString(2, alum.apellido);
                ps.setDate(3, alum.fechaN);
                resCount = resCount + ps.executeUpdate();
            }
            conn.commit();
            System.out.println("Número de filas actualizada/s: " + resCount);
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }

    private record Alumno(String nombre, String apellido, Date fechaN) {
    }

    public static void main(String[] args) throws SQLException {
/*      MOSTRAR LOS DRIVERS INSTALADOS
        for (Driver d : DriverManager.drivers().toList()) {
            System.out.println(d.toString());
        }
*/
        try (Connection conn = myConnection(dataBaseProperties())) {
//            insertTo(conn);
//            queryPrep(conn);
            queryPrepScroll(conn);
        }
    }
}