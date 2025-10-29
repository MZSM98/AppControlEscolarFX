package appcontrolescolarfx.controlador;

import appcontrolescolarfx.AppControlEscolarFX;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Profesor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLPrincipalController implements Initializable {

    

    @FXML
    private Label labelNombre;
    @FXML
    private Label labelNumTrabajador;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonAdminProfesores;
    @FXML
    private Button botonAdminAlumnos;
    @FXML
    private Button botonAdminCarreras;
    @FXML
    private Label labelRol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void obtenerSesion(Profesor profesorSesion){
        
        labelNombre.setText(profesorSesion.getNombre() + " "+ profesorSesion.getApellidoParterno()
                + " " +profesorSesion.getApellidoMaterno());
        labelNumTrabajador.setText(profesorSesion.getNoPersonal());
        labelRol.setText(profesorSesion.getRol());
        
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        
        
        try{
            Parent vista = FXMLLoader.load(AppControlEscolarFX.class.getResource("vista/FXMLInicioSesion.fxml"));
            Scene escena = new Scene(vista);
            Stage stActual = (Stage)labelNombre.getScene().getWindow();
            stActual.setTitle("Iniciar Sesion");
            stActual.setScene(escena);
            stActual.show();
        
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    private void clicAdminProfesores(ActionEvent event) {
        
        try{
            
            Parent vista = FXMLLoader.load(AppControlEscolarFX.class.getResource("vista/FXMLAdminProfesor.fxml"));
            Scene escena = new Scene(vista);
            Stage stAdmin = new Stage();
            stAdmin.setScene(escena);
            stAdmin.setTitle("Adminsitracion Profesores");
            stAdmin.initModality(Modality.APPLICATION_MODAL);
            stAdmin.showAndWait();
        
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    private void clicAdminAlumnos(ActionEvent event) {
    }

    @FXML
    private void clicAdminCarreras(ActionEvent event) {
    }
    
}
