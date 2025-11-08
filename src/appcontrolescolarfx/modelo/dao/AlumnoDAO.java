package appcontrolescolarfx.modelo.dao;

import appcontrolescolarfx.modelo.pojo.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlumnoDAO {
    
    private AlumnoDAO(){
        
    }
    
    public static int registrarAlumno(Alumno alumno, Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String insercion = "INSERT INTO alumno (nombre, apellidoPaterno, apellidoMaterno, "
                    + "matricula, correo, idCarrera, foto, fechaNacimiento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(insercion);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2, alumno.getApellidoPaterno());
            sentencia.setString(3, alumno.getApellidoMaterno());
            sentencia.setString(4, alumno.getMatricula());
            sentencia.setString(5, alumno.getCorreo());
            sentencia.setInt(6, alumno.getIdCarrera());
            sentencia.setBytes(7, alumno.getFoto()); // El campo 'foto' es longblob
            sentencia.setString(8, alumno.getFechaNacimiento());
            return sentencia.executeUpdate();
        }
        throw new SQLException("Error de conexión con la base de datos");
    }
    
    public static int actualizarAlumno(Alumno alumno, Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String actualizacion = "UPDATE alumno SET nombre = ?, apellidoPaterno = ?, "
                    + "apellidoMaterno = ?, matricula = ?, correo = ?, idCarrera = ?, "
                    + "foto = ?, fechaNacimiento = ? WHERE idAlumno = ?;";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(actualizacion);
            sentencia.setString(1, alumno.getNombre());
            sentencia.setString(2, alumno.getApellidoPaterno());
            sentencia.setString(3, alumno.getApellidoMaterno());
            sentencia.setString(4, alumno.getMatricula());
            sentencia.setString(5, alumno.getCorreo());
            sentencia.setInt(6, alumno.getIdCarrera());
            sentencia.setBytes(7, alumno.getFoto());
            sentencia.setString(8, alumno.getFechaNacimiento());
            sentencia.setInt(9, alumno.getIdAlumno()); // idAlumno para el WHERE
            return sentencia.executeUpdate();
        }
        throw new SQLException("Error de conexión con la base de datos");
    }
    
}