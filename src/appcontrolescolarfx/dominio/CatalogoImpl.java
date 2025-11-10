
package appcontrolescolarfx.dominio;

import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.CatalogoDAO;
import appcontrolescolarfx.modelo.pojo.Carrera;
import appcontrolescolarfx.modelo.pojo.Facultad;
import appcontrolescolarfx.modelo.pojo.Rol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class CatalogoImpl {
    
    public static HashMap<String,Object> obtenerRolesProfesor(){
        
        HashMap<String,Object> respuesta = new LinkedHashMap<>();
        
        try{
            ResultSet resultado = CatalogoDAO.obtenerRoles(ConexionBD.abrirConexion());
            List<Rol> roles = new ArrayList<>();
            
            while(resultado.next()){
                Rol rol = new Rol();
                rol.setIdRol(resultado.getInt("idRol"));
                rol.setRol(resultado.getString("rol"));
                roles.add(rol);
            }
            ConexionBD.cerrarConexion();
            respuesta.put("error", false);
            respuesta.put("roles", roles);
        }catch(SQLException sqle){
            respuesta.put("error",true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerFacultades(){
        
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        
        try {
            ResultSet resultado = CatalogoDAO.obtenerFacultades(ConexionBD.abrirConexion());
            List<Facultad> facultades = new ArrayList<>();
            
            while (resultado.next()){
                Facultad facultad = new Facultad();
                facultad.setIdFacultad(resultado.getInt("idFacultad"));
                facultad.setFacultad(resultado.getString("facultad"));
                facultades.add(facultad);
            }
            ConexionBD.cerrarConexion();
            respuesta.put("error", false);
            respuesta.put("facultades", facultades);
            
        }catch(SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerCarreras(int idFacultad){
        
        HashMap <String, Object> respuesta = new LinkedHashMap<>();
        
        try{
            ResultSet resultado = CatalogoDAO.obtenerCarreras(ConexionBD.abrirConexion(), idFacultad);
            List<Carrera> carreras = new ArrayList<>();
            while (resultado.next()){
                Carrera carrera = new Carrera();
                carrera.setIdCarrera(resultado.getInt("idCarrera"));
                carrera.setCarrera(resultado.getString("carrera"));
                carreras.add(carrera);
            }
            ConexionBD.cerrarConexion();
            respuesta.put("error", false);
            respuesta.put("carreras", carreras);
        }catch (SQLException sqle){
            respuesta.put("error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        return respuesta;
    }
    
}
