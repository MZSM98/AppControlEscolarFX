package appcontrolescolarfx.dominio;

import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.AlumnoDAO;
import appcontrolescolarfx.modelo.dao.ProfesorDAO;
import appcontrolescolarfx.modelo.pojo.Alumno;
import appcontrolescolarfx.modelo.pojo.Respuesta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
                alumno.setCarrera(resultado.getString("carrera"));
                alumno.setIdFacultad(resultado.getInt("idFacultad"));
                alumno.setFacultad(resultado.getString("facultad"));
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
    
    public static HashMap<String, Object> obtenerFoto (int idAlumno){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
                
        try{
            ResultSet resultado = AlumnoDAO.obtenerFoto(ConexionBD.abrirConexion(), idAlumno);
            if(resultado.next()){
                byte[] foto;
                foto = resultado.getBytes("foto");
                respuesta.put("error", false);
                respuesta.put("foto", foto);
            }
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
    public static boolean verificarDuplicado(String matricula){
        try {
            return AlumnoDAO.verificarDuplicado(ConexionBD.abrirConexion(), matricula);
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrarConexion();
        }
    }
    
    public static Respuesta registrar(Alumno alumno){
        
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        
        try{
            int filasAfectadas = AlumnoDAO.registrarAlumno(alumno, ConexionBD.abrirConexion());
            if(filasAfectadas >0){
                respuesta.setError(false);
                respuesta.setMensaje("Información del alumno guardada de manera exitosa");
            }else{
                respuesta.setMensaje("Lo sentimos :(, no pudimos guardar la información");
            }
        }catch(SQLException sqle){
            respuesta.setMensaje(sqle.getMessage());
        }
        return respuesta;
    }
    
}