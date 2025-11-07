package appcontrolescolarfx.controlador;

import appcontrolescolarfx.modelo.pojo.Alumno;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAdminAlumnoController implements Initializable {

    @FXML
    private TextField textBuscar;
    @FXML
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn columnMatricula;
    @FXML
    private TableColumn columnApPaterno;
    @FXML
    private TableColumn columnApMaterno;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnCarrera;
    @FXML
    private TableColumn columnFacultad;
    @FXML
    private TableColumn columnFechaNacimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegistrar(ActionEvent event) {
        
    }

    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void buscarPorNombre(ActionEvent event) {
    }

    @FXML
    private void clicExportar(ActionEvent event) {
        
    }
    
    private void abrirFormulario(){
        
        try{
            FXMLLoader cargador = Utilidades.obtenerVistaMemoria("FXMLFormularioAlumno.fxml");
            Parent parent = cargador.load();
            Scene scene = new Scene(parent);
            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Formulario de Registro Alumno");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    
    
}
