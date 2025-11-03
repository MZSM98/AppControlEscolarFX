package appcontrolescolarfx.controlador;
import appcontrolescolarfx.AppControlEscolarFX;
import appcontrolescolarfx.dominio.AutenticacionImpl;
import appcontrolescolarfx.modelo.pojo.Profesor;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilidad.Utilidades;

public class FXMLInicioSesionController implements Initializable {
    @FXML
    private TextField tfNumPersonal;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private PasswordField pfContrasena;
    @FXML
    private Button botonIngresar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void iniciarSesion(ActionEvent event) {
        
        String noPersonal = tfNumPersonal.getText();
        String contrasena = pfContrasena.getText();
        
        if (sonDatosValidos(noPersonal, contrasena)){
            validarSesion(noPersonal, contrasena);
        }
    }
    
    private boolean sonDatosValidos(String noPersonal, String contrasena){
        
        boolean correctos = true;
        lbErrorPassword.setText("");
        lbErrorUsuario.setText("");
        
        if (noPersonal == null || noPersonal.isEmpty()){
            correctos = false;
            lbErrorUsuario.setText("Número de personal obligatorio");
        }
        
        if (contrasena == null || contrasena.isEmpty()){
            correctos = false;
            lbErrorPassword.setText("Contraseña obligatoria");
        }
        
        return correctos;
    }
    
    private void validarSesion(String noPersonal, String contrasena){
        HashMap<String, Object> respuesta = AutenticacionImpl.verificarCredencialesProfesor(noPersonal, contrasena);
        boolean error = (boolean) respuesta.get("Error");
        
        if (!error){
            Profesor profesorSesion = (Profesor)respuesta.get("Profesor");
            Utilidades.mostrarAlertaSimple("Credenciales Correctas", "Bienvenido(a) profesor(a) " +
                    profesorSesion.getNombre() + ", al Sistema de Admnistracion Escolar" ,Alert.AlertType.INFORMATION);
            irPantallaPrincipal(profesorSesion);
            
        } else {
            Utilidades.mostrarAlertaSimple("Credenciales Incorrectas", "No. de Personal y/o contraseña"
                    + " incorrectos, por favor verifique la información"
                    ,Alert.AlertType.INFORMATION);
        }
        
    }
    
    private void irPantallaPrincipal(Profesor profesorSesion){
        
        try{
            
            FXMLLoader cargador = new FXMLLoader(AppControlEscolarFX.class.getResource
        ("vista/FXMLPrincipal.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalController controlador = cargador.getController();
            controlador.obtenerSesion(profesorSesion);
            Scene escena = new Scene(vista);      
            Stage escenario = (Stage) tfNumPersonal.getScene().getWindow();
            escenario.setScene(escena);
            escenario.setTitle("Inicio");
            escenario.show();
            
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}