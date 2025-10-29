package appcontrolescolarfx.dominio;
import appcontrolescolarfx.modelo.ConexionBD;
import appcontrolescolarfx.modelo.dao.AutenticacionDAO;
import appcontrolescolarfx.modelo.pojo.Profesor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AutenticacionImpl {
    
    public static HashMap<String, Object> verificarCredencialesProfesor(
            String noPersonal, String password){
        
        HashMap<String, Object> respuesta = new HashMap<>();
        
        try{
            ResultSet resultado = AutenticacionDAO.autenticarUsuario(noPersonal,
                    password, ConexionBD.abrirConexion());
            
            if (resultado.next()){
                Profesor profesorSesion = new Profesor();
                profesorSesion.setIdProfesor(resultado.getInt("idProfesor"));
                profesorSesion.setNombre(resultado.getString("nombre"));
                profesorSesion.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                profesorSesion.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                profesorSesion.setNoPersonal(resultado.getString("noPersonal"));
                profesorSesion.setIdRol(resultado.getInt("idRol"));
                profesorSesion.setRol(resultado.getString("rol"));
                respuesta.put("Error", false);
                respuesta.put("mensaje", "Credenciales correctas");
                respuesta.put("Profesor", profesorSesion);
            }else{
                respuesta.put("Error", true);
                respuesta.put("mensaje", "Las credenciales proporcionadas son incorrectas, por favor verifica la información");
            }
            ConexionBD.cerrarConexionBD();
        } catch (SQLException sqle){
            respuesta.put("Error", true);
            respuesta.put("mensaje", sqle.getMessage());
        }
        
        return respuesta;
    }
            
}