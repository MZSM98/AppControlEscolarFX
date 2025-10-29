package appcontrolescolarfx.controlador;

import appcontrolescolarfx.dominio.ProfesorImpl;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Profesor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAdminProfesorController implements Initializable, IObservador {

    @FXML
    private TextField textBuscar;
    @FXML
    private Button botonRegistrar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonEliminar;
    @FXML
    private TableView<Profesor> tablaProfesores;
    @FXML
    private TableColumn columnPersonal;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnFechaContratacion;
    @FXML
    private TableColumn columnRol;
    @FXML
    private TableColumn columnApellidoPaterno;
    @FXML
    private TableColumn columnApellidoMaterno;
    
    private ObservableList<Profesor> profesores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        llenarTabla();
        
    }    
    
    private void configurarTabla(){
        columnPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columnApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columnFechaContratacion.setCellValueFactory(new PropertyValueFactory("fechaContratacion"));
        columnRol.setCellValueFactory(new PropertyValueFactory("rol"));
        
    }
    
    private void llenarTabla(){
        HashMap<String,Object> respuesta = ProfesorImpl.obtenerProfesores();
        
        boolean error = (boolean)respuesta.get("error");
        if(!error){
            ArrayList<Profesor> profesoresBD = (ArrayList<Profesor>)respuesta.get("profesores");
            profesores = FXCollections.observableArrayList();
            profesores.addAll(profesoresBD);
            tablaProfesores.setItems(profesores);
        }else{
            Utilidades.mostrarAlertaSimple((String)respuesta.get("error"),
                    (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void registrarProfesor(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void modificarProfesor(ActionEvent event) {
        
        
    }

    @FXML
    private void eliminarProfesor(ActionEvent event) {
    }

    @FXML
    private void buscarPorNombre(ActionEvent event) {
    }

    private void irFormulario(Profesor profesor){
        try{
            
            FXMLLoader cargador = 
            Utilidades.obtenerVistaMemoria("vista/FXMLFormularioProfesor.fxml");
            Parent vista = cargador.load();
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setTitle("Formulario profesores");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            FXMLFormularioProfesorController controlador = cargador.getController();
            controlador.inicializarDatos(this, profesor);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        llenarTabla();
    }
    
    
}
