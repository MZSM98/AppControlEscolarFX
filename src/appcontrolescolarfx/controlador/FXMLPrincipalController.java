package appcontrolescolarfx.controlador;

import appcontrolescolarfx.AppControlEscolarFX;
import appcontrolescolarfx.modelo.pojo.Profesor;
import appcontrolescolarfx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private Label labelRol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void obtenerSesion(Profesor profesorSesion){
        
        labelNombre.setText(profesorSesion.getNombre() + " "+ profesorSesion.getApellidoPaterno()
                + " " +profesorSesion.getApellidoMaterno());
        labelNumTrabajador.setText(profesorSesion.getNoPersonal());
        labelRol.setText(profesorSesion.getRol());
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
        
        try {
            FXMLLoader cargador = Utilidades.obtenerVistaMemoria("vista/FXMLAdminAlumno.fxml");
            Parent parent = cargador.load();
            Scene escena = new Scene(parent);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Administrador de alumnos");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        
    }

    @FXML
    private void clicAdminCarreras(ActionEvent event) {
    }

    @FXML
    private void cerrarVentana(MouseEvent event) {
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

}
