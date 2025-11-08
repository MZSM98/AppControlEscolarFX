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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import appcontrolescolarfx.utilidades.Utilidades;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAdminProfesorController implements Initializable, IObservador {

    @FXML
    private TextField textBuscar;
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
        configurarBusqueda();
        
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
        
        Profesor profesorSeleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado != null){
            irFormulario(profesorSeleccionado);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un profesor", "Para modificar la información de un profesor, primero debes seleccionarlo" , Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarProfesor(ActionEvent event) {
        Profesor profesorSeleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
        
        if (profesorSeleccionado != null){
            boolean confirmacion;
            confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar Eliminacion", "¿está seguro de continuar?", "Está a punto de eliminar el registro del profesor(a) "
                    + profesorSeleccionado.getNombre() + profesorSeleccionado. getApellidoPaterno()+ ", esta acción no se puede revertir");
            
            if(confirmacion)
                eliminarRegistroProfesor(profesorSeleccionado.getIdProfesor());
            
        }else{
            Utilidades.mostrarAlertaSimple("Seleccione un profesor", "Para eliminar a un profesor, primero debes seleccionarlo", Alert.AlertType.WARNING);
        }
    }

    

    private void irFormulario(Profesor profesor){
        try{
            
            FXMLLoader cargador = 
            Utilidades.obtenerVistaMemoria("vista/FXMLFormularioProfesor.fxml");
            Parent vista = cargador.load();
            FXMLFormularioProfesorController controlador = cargador.getController();
            controlador.inicializarDatos(this, profesor);
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setTitle("Formulario profesores");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipoOperacion, String nombre) {
        
        llenarTabla();
        textBuscar.setText("");
        configurarBusqueda();
    }
    
    private void eliminarRegistroProfesor (int idProfesorSeleccionado){
        HashMap<String, Object> respuesta = ProfesorImpl.eliminarProfesor(idProfesorSeleccionado);
        if(!(boolean) respuesta.get("error")){
            textBuscar.setText("");
            Utilidades.mostrarAlertaSimple("Registro eliminado", "El Registro del profesor fue eliminado de manera exitosa" , Alert.AlertType.INFORMATION);
            llenarTabla();
            configurarBusqueda();
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.get("error").toString(), Alert.AlertType.ERROR);
        }
        ProfesorImpl.eliminarProfesor(idProfesorSeleccionado);
        llenarTabla();
    }
    
    private void configurarBusqueda(){
        
        if(profesores!= null && profesores.size()> 0){
            FilteredList<Profesor> filtradoProfesores = 
                    new FilteredList<>(profesores, p ->true);
            textBuscar.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    
                    filtradoProfesores.setPredicate(profesor -> {
                        //CASO DEFAULT VACIO (DEVOLVER TODOS)
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerNewValue = newValue.toLowerCase();
                        if(profesor.getNombre().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        if(profesor.getNoPersonal().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        
                        return false;
                    });
                }
            });
            SortedList<Profesor> sortedProfesor = new SortedList<>(filtradoProfesores);
            sortedProfesor .comparatorProperty().bind(tablaProfesores.comparatorProperty());
            tablaProfesores.setItems(sortedProfesor);
        }
    }
}
