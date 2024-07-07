package utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Filas {
    public static void alumnos(ResultSet rs) throws SQLException {
        int id = rs.getRow();
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        Date fechaN = rs.getDate("fecha");
        System.out.printf("%d__ %2s %s %s\n", id, nombre, apellido, fechaN);
    }
}
