package appcontrolescolarfx.controlador;

import appcontrolescolarfx.dominio.CatalogoImpl;
import appcontrolescolarfx.modelo.pojo.Carrera;
import appcontrolescolarfx.modelo.pojo.Facultad;
import appcontrolescolarfx.utilidades.Utilidades;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private ComboBox<Facultad> comboFacultad;
    @FXML
    private ComboBox<Carrera> comboCarrera;
    @FXML
    private DatePicker pickerNacimiento;
    
    ObservableList<Facultad> facultades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFacultades();
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
    
    private void cargarFacultades(){
        HashMap<String, Object> respuesta = CatalogoImpl.obtenerFacultades();
        
        if (!(boolean)respuesta.get("error")){
            List<Facultad> facultadesBD = (List<Facultad>)respuesta.get("carreras");
            facultades = FXCollections.observableArrayList();
            facultades.addAll(facultadesBD);
            comboFacultad.setItems(facultades);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar facultades", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
        
    }


    
}
