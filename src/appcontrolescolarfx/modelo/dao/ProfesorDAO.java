
package appcontrolescolarfx.modelo.dao;

import appcontrolescolarfx.modelo.pojo.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfesorDAO {
    
    private ProfesorDAO(){
        
    }
    
    public static int registrarProfesor(Profesor profesor, Connection conexionBD) throws SQLException{
        
        if(conexionBD != null){
            String insert = "INSERT INTO profesor"
                    + " (nombre, apellidoPaterno, apellidoMaterno, noPersonal, password,"
                    + " fechaNacimiento, fechaContratacion, idRol) VALUES"
                    + " (?, ?, ?, ?, ?, ?, ?, ?);";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(insert);
            sentencia.setString(1, profesor.getNombre());
            sentencia.setString(2, profesor.getApellidoParterno());
            sentencia.setString(3, profesor.getApellidoMaterno());
            sentencia.setString(4, profesor.getNoPersonal());
            sentencia.setString(5, profesor.getPassword());
            sentencia.setString(6, profesor.getFechaNacimiento());
            sentencia.setString(7, profesor.getFechaContratacion());
            sentencia.setInt(8, profesor.getIdRol());
            return sentencia.executeUpdate();
        }
        throw new SQLException("Error de conexion con la base de datos");
    }
    
    public static ResultSet obtenerProfesores(Connection conexionBD)
            throws SQLException{
        
        if (conexionBD != null){
           
                String consulta = "SELECT idProfesor, nombre, apellidoPaterno, apellidoMaterno, noPersonal, fechaNacimiento, " +
                    "fechaContratacion, profesor.idRol, Rol " +
                    "FROM " +
                    "profesor " +
                    "INNER JOIN " +
                    "rol on rol.idRol = profesor.idRol;";
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                return sentencia.executeQuery();
            
        }
        throw new SQLException("No hay conexion a la base de datos.");
        
    }
}
