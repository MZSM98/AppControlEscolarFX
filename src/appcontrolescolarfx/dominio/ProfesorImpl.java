
package appcontrolescolarfx.dominio;

import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.ProfesorDAO;
import appcontrolescolarfx.modelo.pojo.Profesor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class ProfesorImpl {
    
    private ProfesorImpl(){
        
    }
    
    public static HashMap<String, Object> obtenerProfesores(){
        
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try{
            
            ResultSet resultado = ProfesorDAO.obtenerProfesores(ConexionBD.abrirConexion());
            ArrayList<Profesor> profesores = new ArrayList<>();
            
            while (resultado.next()){
                Profesor profesor = new Profesor ();
                profesor.setIdProfesor(resultado.getInt("idProfesor"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                profesor.setNoPersonal(resultado.getString("noPersonal"));
                profesor.setFechaNacimiento(resultado.getString("fechaNacimiento"));
                profesor.setFechaContratacion(resultado.getString("fechaContratacion"));
                profesor.setIdRol(resultado.getInt("idRol"));
                profesor.setRol(resultado.getString("Rol"));
                profesores.add(profesor);
                
            }
            respuesta.put("error", false);
            respuesta.put("profesores", profesores);
            ConexionBD.cerrarConexionBD();
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
    
    public static HashMap<String, Object> registrarProfesor(Profesor profesor){
        
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            int filasAfectadas = ProfesorDAO.registrarProfesor(profesor, ConexionBD.abrirConexion());
            
            if (filasAfectadas > 0){
                respuesta.put("error", false);
                respuesta.put("mensaje", "El registro del profesor" + profesor.getNombre() +" fue guardado de manera exitosa");
                
            }else{
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo guardar la información, inténtelo más tarde");
            }
            
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
}
