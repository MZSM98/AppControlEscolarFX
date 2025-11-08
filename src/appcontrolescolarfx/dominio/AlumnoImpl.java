package appcontrolescolarfx.dominio;

import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.AlumnoDAO;
import appcontrolescolarfx.modelo.pojo.Alumno;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class AlumnoImpl {

    public static HashMap<String, Object> registrarAlumno(Alumno alumno) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {
            int filasAfectadas = AlumnoDAO.registrarAlumno(alumno, ConexionBD.abrirConexion());

            if (filasAfectadas > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "El registro del alumno " + alumno.getNombre() + " fue guardado de manera exitosa");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo guardar la información, inténtelo más tarde");
            }

        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> editarAlumno(Alumno alumno) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {
            int filasAfectadas = AlumnoDAO.actualizarAlumno(alumno, ConexionBD.abrirConexion());

            if (filasAfectadas > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "La información del alumno " + alumno.getNombre() + " fue actualizada de manera exitosa");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo actualizar la información, inténtelo más tarde");
            }

        } catch (SQLException sqle) {
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }

}