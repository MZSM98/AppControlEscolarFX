package appcontrolescolarfx.controlador;


import appcontrolescolarfx.dominio.CatalogoImpl;
import appcontrolescolarfx.dominio.ProfesorImpl;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Profesor;
import appcontrolescolarfx.modelo.pojo.Rol;
import java.net.URL;
import java.time.LocalDate;
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
import appcontrolescolarfx.utilidades.Utilidades;

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
            if(profesorEdicion == null){
                registrarProfesor();
            }else{
                editarProfesor();
            }
           
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
        
                
        if(textNumPersonal.getText().isEmpty()){
            valido = false;
            mensajeError += "-Numero de personal requerido.\n";
            
        }
        
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
        
        if (ProfesorImpl.verificarDuplicado(profesorNuevo.getNoPersonal())){
            Utilidades.mostrarAlertaSimple("Numero de Personal en uso", "el numero de personal (" +
                    profesorNuevo.getNoPersonal() +
                    ") ya se encuentra en uso, intente con uno diferente", Alert.AlertType.WARNING);
            return;
        }
        
        HashMap<String, Object> resultado = ProfesorImpl.registrarProfesor(profesorNuevo);
        if(!(boolean)resultado.get(("error") )){
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
    
    
            
    private void cerrarVentana(){
        ((Stage) textNumPersonal.getScene().getWindow()).close();
    }
    
    public void inicializarDatos(IObservador observador, Profesor profesor){
        this.observador = observador;
        profesorEdicion = profesor;
        
        if (profesor != null){
            textNombre.setText(profesor.getNombre());
            textApellidoPaterno.setText(profesor.getApellidoPaterno());
            textApellidoMaterno.setText(profesor.getApellidoMaterno());
            textContrasena.setText(profesor.getPassword());
            textNumPersonal.setText(profesor.getNoPersonal());
            textNumPersonal.setEditable(false);
            textNumPersonal.setDisable(true);
            pickerContratacion.setValue(LocalDate.parse(profesor.getFechaContratacion()));
            pickerNacimiento.setValue(LocalDate.parse(profesor.getFechaNacimiento()));
            
            int posicion = obtenerRolSeleccionado(profesor.getIdRol());
            comboRol.getSelectionModel().select(posicion);
        }
    }
    
    private int obtenerRolSeleccionado(int idRol){
        for (int i = 0; i < roles.size(); i++){
            if (roles.get(i).getIdRol() == idRol){
                return i;
            }
        }
        return -1;
    }
    
    private void editarProfesor(){
        Profesor profesorEdicion = obtenerProfesor();
        profesorEdicion.setIdProfesor(this.profesorEdicion.getIdProfesor());
        HashMap<String, Object> resultado = ProfesorImpl.editarProfesor(profesorEdicion);
        if(!(boolean)resultado.get(("error"))){
            Utilidades.mostrarAlertaSimple("Profesor regitrado correctamente", resultado.get("mensaje").toString(), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("editar", profesorEdicion.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", resultado.get("error").toString(), Alert.AlertType.ERROR);
        }
    }
    
}
