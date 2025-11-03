
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
            sentencia.setString(2, profesor.getApellidoPaterno());
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
           
                String consulta = "SELECT idProfesor, nombre, apellidoPaterno, apellidoMaterno, noPersonal, fechaNacimiento, password, " +
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
    
    public static boolean verificarNumeroPersonal(Connection conexionBD, String noPersonal) throws SQLException{
        
        if (conexionBD != null){
            
            String consulta = "SELECT * FROM profesor WHERE noPersonal = ?;";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, noPersonal);
            return sentencia.executeQuery().next();
        }
        throw new SQLException("No hay conexion a la base de datos");
    }
    
    public static int editarProfesor(Profesor profesor, Connection conexionBD) throws SQLException{
        
        if (conexionBD != null ){
            String actualizar = "UPDATE profesor SET nombre = ?,"
                    + " apellidoPaterno = ?,"
                    + " apellidoMaterno = ?, fechaNacimiento = ?,"
                    + " fechaContratacion = ?, idRol = ?, password = ? where noPersonal = ?;";
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizar);
            sentencia.setString(1, profesor.getNombre());
            sentencia.setString(2, profesor.getApellidoPaterno());
            sentencia.setString(3, profesor.getApellidoMaterno());
            sentencia.setString(4, profesor.getFechaNacimiento());
            sentencia.setString(5, profesor.getFechaContratacion());
            sentencia.setInt(6, profesor.getIdRol());
            sentencia.setString(7, profesor.getPassword());
            sentencia.setString(8, profesor.getNoPersonal());
            return sentencia.executeUpdate();
        }
        throw new SQLException("No hay conexion a la base de datos");
    }
    
    public static int eliminarProfesor(int idProfesor, Connection conexionBD) throws SQLException{
        
        if(conexionBD != null){
            String eliminar = "DELETE from profesor where idProfesor = ?;";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(eliminar);
            sentencia.setInt(1, idProfesor);
            return sentencia.executeUpdate();
        }
        
        throw new SQLException("No hay conexion a la base de datos");
    }
}
