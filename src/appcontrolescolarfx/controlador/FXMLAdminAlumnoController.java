package appcontrolescolarfx.controlador;

import appcontrolescolarfx.dominio.AlumnoImpl;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Alumno;
import appcontrolescolarfx.modelo.pojo.Profesor;
import appcontrolescolarfx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAdminAlumnoController implements Initializable, IObservador {

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
    
    private ObservableList alumnos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        llenarTabla();
        configurarBusqueda();
    }    

    @FXML
    private void clicRegistrar(ActionEvent event) {
        abrirFormulario(null);
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        Alumno alumnoSeleccionado = tablaAlumnos.getSelectionModel().getSelectedItem();
        
        if(alumnoSeleccionado != null){
            abrirFormulario(alumnoSeleccionado);
        }else{
            Utilidades.mostrarAlertaSimple("ADVERTENCIA", "Para editar a un alumno primero debe seleccionarlo", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }


    @FXML
    private void clicExportar(ActionEvent event) {
        
    }
    
    private void abrirFormulario(Alumno alumno){
        
        try{
            FXMLLoader cargador = Utilidades.obtenerVistaMemoria("vista/FXMLFormularioAlumno.fxml");
            Parent parent = cargador.load();
            
            FXMLFormularioAlumnoController controlador = cargador.getController();
            controlador.inicializarDatos(this, alumno);
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
    
    private void configurarTabla(){
        
        columnMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columnApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columnFacultad.setCellValueFactory(new PropertyValueFactory("facultad"));
        columnFechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        columnCarrera.setCellValueFactory(new PropertyValueFactory("carrera"));
        
    }
    
    private void llenarTabla(){
        HashMap<String, Object> respuesta = AlumnoImpl.obtenerAlumnos();
        
        if (!(boolean)respuesta.get("error")){
            ArrayList<Alumno> alumnosBD = (ArrayList<Alumno>)respuesta.get("alumnos");
            alumnos = FXCollections.observableArrayList();
            alumnos.addAll(alumnosBD);
            tablaAlumnos.setItems(alumnos);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar Alumnos", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarBusqueda(){
        
        if(alumnos!= null && alumnos.size()> 0){
            FilteredList<Alumno> filtradoAlumnos = 
                    new FilteredList<>(alumnos, p ->true);
            textBuscar.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    
                    filtradoAlumnos.setPredicate(alumno -> {
                        //CASO DEFAULT VACIO (DEVOLVER TODOS)
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerNewValue = newValue.toLowerCase();
                        if(alumno.getNombre().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        if(alumno.getApellidoPaterno().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        if(alumno.getApellidoMaterno().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        
                        return false;
                    });
                }
            });
            SortedList<Alumno> sortedAlumno = new SortedList<>(filtradoAlumnos);
            sortedAlumno.comparatorProperty().bind(tablaAlumnos.comparatorProperty());
            tablaAlumnos.setItems(sortedAlumno);
        }
    }
    
    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        
        llenarTabla();
        textBuscar.setText("");
        configurarBusqueda();
    }
    
}
