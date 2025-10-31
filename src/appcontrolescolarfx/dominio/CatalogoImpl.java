
package appcontrolescolarfx.dominio;

import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.CatalogoDAO;
import appcontrolescolarfx.modelo.dao.ProfesorDAO;
import appcontrolescolarfx.modelo.pojo.Profesor;
import appcontrolescolarfx.modelo.pojo.Rol;
import java.sql.PreparedStatement;
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
            ConexionBD.cerrarConexionBD();
            respuesta.put("error", false);
            respuesta.put("roles", roles);
        }catch(SQLException sqle){
            respuesta.put("error",true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        
        return respuesta;
    }
    
}
