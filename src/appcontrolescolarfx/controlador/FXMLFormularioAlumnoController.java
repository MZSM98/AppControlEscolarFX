package appcontrolescolarfx.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLFormularioAlumnoController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
    @FXML
    private TextField textMatricula;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApPaterno;
    @FXML
    private TextField textApMaterno;
    @FXML
    private TextField textCorreo;
    @FXML
    private ComboBox<?> comboFacultad;
    @FXML
    private ComboBox<?> comboCarrera;
    @FXML
    private DatePicker pickerNacimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cargarFoto(ActionEvent event) {
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
    }



    
}
