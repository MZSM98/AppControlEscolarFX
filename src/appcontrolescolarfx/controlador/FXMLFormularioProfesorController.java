package appcontrolescolarfx.controlador;


import appcontrolescolarfx.dominio.CatalogoImpl;
import appcontrolescolarfx.dominio.ProfesorImpl;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Profesor;
import appcontrolescolarfx.modelo.pojo.Rol;
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
import javafx.stage.Stage;
import utilidad.Utilidades;

public class FXMLFormularioProfesorController implements Initializable {

    @FXML
    private TextField textNumPersonal;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    @FXML
    private DatePicker pickerNacimiento;
    @FXML
    private DatePicker pickerContratacion;
    @FXML
    private ComboBox<Rol> comboRol;
    @FXML
    private TextField textContrasena;
    
    private IObservador observador;
    private Profesor profesorEdicion;
    
    ObservableList<Rol> roles;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRolesProfesor();
    }    

    @FXML
    private void cerrarRegistro(ActionEvent event) {
            cerrarVentana();
    }

    @FXML
    private void guardarRegistro(ActionEvent event) {
        
        if (sonCamposValidos()){
           registrarProfesor();
        }
    }
    
    private void cargarRolesProfesor(){
        HashMap <String,Object> respuesta = CatalogoImpl.obtenerRolesProfesor();
        
        if (!(boolean)respuesta.get("error")){
            List<Rol> rolesBd = (List<Rol>)respuesta.get("roles");
            roles = FXCollections.observableArrayList();
            roles.addAll(rolesBd);
            comboRol.setItems(roles);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar roles del sistema", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
        
    }
    
    private boolean sonCamposValidos(){
        boolean valido = true;
        String mensajeError = "Se encontraron los siguientes errores: \n";
        
        if(textNombre.getText().isEmpty()){
            valido = false;
            mensajeError += "-Nombre del profesor requerido.\n";
        }
        if(textApellidoPaterno.getText().isEmpty()){
            valido = false;
            mensajeError += "-Apellido Paterno obligatorio.\n";
            
        }
        if(textApellidoMaterno.getText().isEmpty()){
            valido = false;
            mensajeError += "-Apellido Materno requerido.\n";
            
        }
        
        if(textNumPersonal.getText().isEmpty()){
            valido = false;
            mensajeError += "-Numero de personal requerido.\n";
            
        }
        
        if (textContrasena.getText().isEmpty()){
            valido = false;
            mensajeError += "-Contraseña requerida.\n";
        }
        
        if (pickerContratacion.getValue() == null){
            valido = false;
            mensajeError += "-Fecha de contratacion requerida.\n";
        }
        if(pickerNacimiento.getValue() == null){
            valido = false;
            mensajeError += "-Fecha de nacimiento requerida.\n";
        }
        
        if (comboRol.getSelectionModel().isEmpty()){
            valido = false;
            mensajeError += "-Rol requerido.\n";
            
        }
        if(!valido){
            Utilidades.mostrarAlertaSimple("Campos Vacios", mensajeError, Alert.AlertType.WARNING);
        }
        return valido;
    }
    
    private void registrarProfesor(){
        
        Profesor profesorNuevo = obtenerProfesor ();
        HashMap<String, Object> resultado = ProfesorImpl.registrarProfesor(profesorNuevo);
        if((boolean)resultado.get(("error"))){
            Utilidades.mostrarAlertaSimple("Profesor regitrado correctamente", resultado.get("mensaje").toString(), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("registrar", profesorNuevo.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", resultado.get("error").toString(), Alert.AlertType.ERROR);
        }
        
    }
    
    
    private Profesor obtenerProfesor(){
        Profesor profesor = new Profesor();
        profesor.setNoPersonal(textNumPersonal.getText());
        profesor.setNombre(textNombre.getText());
        profesor.setApellidoPaterno(textApellidoPaterno.getText());
        profesor.setApellidoMaterno(textApellidoMaterno.getText());
        profesor.setFechaNacimiento(pickerNacimiento.getValue().toString());
        profesor.setFechaContratacion(pickerContratacion.getValue().toString());
        profesor.setPassword(textContrasena.getText());
        Rol rolSeleccionado = comboRol.getSelectionModel().getSelectedItem();
        profesor.setIdRol(rolSeleccionado.getIdRol());
        return profesor;
    }
    
    public void inicializarDatos(IObservador observador, Profesor profesor){
        this.observador = observador;
        profesorEdicion = profesor;
        //Cargar datos a la pantalla de edicion
    }
            
    private void cerrarVentana(){
        //El orden de casteo se ejecuta desde el intento de casteo (primero la instruccion, luego el casteo)
        ((Stage) textNombre.getScene().getWindow()).close();
    }
}
