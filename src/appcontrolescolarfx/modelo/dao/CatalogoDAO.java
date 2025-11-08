
package appcontrolescolarfx.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CatalogoDAO {
    
    private CatalogoDAO(){
        
    }
    
    public static ResultSet obtenerRoles(Connection ConexionBD) throws SQLException{
        
        if (ConexionBD != null){
            String consulta = "SELECT * from rol";
            PreparedStatement sentencia = ConexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }else {
            throw new SQLException("Lo sentimos, no hay conexion a la base de datos");
        }
    }
    
    public static ResultSet obtenerFacultades (Connection ConexionBD) throws SQLException{
        
        if(ConexionBD != null){
            String consulta = "SELECT * from facultad";
            PreparedStatement sentencia = ConexionBD.prepareStatement(consulta);
            return sentencia.executeQuery();
        }else{
            throw new SQLException ("Lo sentimos, no hay conexion a la base de datos");
        }
    }
    
    public static ResultSet obtenerCarreras(Connection ConexionBD, int idFacultad) throws SQLException{
        
        if(ConexionBD != null){
            String consulta = "SELECT * from carrera where idFacultad = ?;";
            PreparedStatement sentencia = ConexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idFacultad);
            return sentencia.executeQuery();
        }else{
            throw new SQLException();
        }
    }
}
