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
    
    public static HashMap<String, Object> obtenerAlumnos(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            ResultSet resultado = AlumnoDAO.obtenerAlumnos(ConexionBD.abrirConexion());
            ArrayList<Alumno> alumnos = new ArrayList<>();
            while(resultado.next()){
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultado.getInt("idAlumno"));
                alumno.setNombre(resultado.getString("nombre"));
                alumno.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                alumno.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                alumno.setCorreo(resultado.getString("correo"));
                alumno.setMatricula(resultado.getString("matricula"));
                alumno.setIdCarrera(resultado.getInt("idCarrera"));
                alumno.setFechaNacimiento(resultado.getString("fechaNacimiento"));
                alumnos.add(alumno);
            }
            respuesta.put("error", false);
            respuesta.put("alumnos", alumnos);
            ConexionBD.cerrarConexion();
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> verificarDuplicado(String matricula){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            boolean esDuplicado = AlumnoDAO.verificarDuplicado(ConexionBD.abrirConexion(), matricula);
            if(esDuplicado){
                respuesta.put("duplicado", true);
            }else{
                respuesta.put("duplicado", false);
                respuesta.put("error", true);
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
}