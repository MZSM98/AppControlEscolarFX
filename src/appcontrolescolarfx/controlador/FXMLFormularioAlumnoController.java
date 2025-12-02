package appcontrolescolarfx.controlador;

import appcontrolescolarfx.dominio.AlumnoImpl;
import appcontrolescolarfx.dominio.CatalogoImpl;
import appcontrolescolarfx.interfaces.IObservador;
import appcontrolescolarfx.modelo.pojo.Alumno; 
import appcontrolescolarfx.modelo.pojo.Carrera;
import appcontrolescolarfx.modelo.pojo.Facultad;
import appcontrolescolarfx.modelo.pojo.Respuesta;
import appcontrolescolarfx.utilidades.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage; 
import javax.imageio.ImageIO;

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
    ObservableList<Carrera> carreras; 

    private IObservador observador; 
    private Alumno alumnoEdicion; 
    
    private File fotoSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFacultades();
    }

    @FXML
    private void cargarFoto(ActionEvent event) {
        abrirDialogo();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        if (sonCamposValidos()){
            if(alumnoEdicion == null){
                registrarAlumno();
            }else{
                editarAlumno();
            }
           
        }
    }
    
    
    
    private boolean sonCamposValidos() {
        boolean valido = true;
        String mensajeError = "Se encontraron los siguientes errores: \n";
        
        if(fotoSeleccionada == null && alumnoEdicion == null){
            valido = false;
            mensajeError+= "- Fotografía del alumno requerida\n";
        }
        if (textNombre.getText().isEmpty()) {
            valido = false;
            mensajeError += "- Nombre del alumno requerido.\n";
        }
        if (textApPaterno.getText().isEmpty()) {
            valido = false;
            mensajeError += "- Apellido Paterno obligatorio.\n";
        }
        if (pickerNacimiento.getValue() == null) {
            valido = false;
            mensajeError += "- Fecha de nacimiento requerida.\n";
        }
        
        if (textCorreo.getText().isEmpty()){
            valido = false;
            mensajeError += "- El correo es requerido.\n";
        }
        if (comboCarrera.getSelectionModel().isEmpty()) {
            valido = false;
            mensajeError += "- Carrera requerida (asegúrese de seleccionar una facultad primero).\n";
        }
        
        if (textMatricula.getText().isEmpty()) {
            valido = false;
            mensajeError += "- Matrícula requerida.\n";
        }

        if (!valido) {
            Utilidades.mostrarAlertaSimple("Campos Vacíos o Inválidos", mensajeError, Alert.AlertType.WARNING);
        }
        return valido;
    }
    
    private Alumno obtenerAlumno() {
        Alumno alumno = new Alumno();
        alumno.setMatricula(textMatricula.getText());
        alumno.setNombre(textNombre.getText());
        alumno.setApellidoPaterno(textApPaterno.getText());
        alumno.setApellidoMaterno(textApMaterno.getText());
        alumno.setCorreo(textCorreo.getText());
        alumno.setFechaNacimiento(pickerNacimiento.getValue().toString());
        
        Carrera carreraSeleccionada = comboCarrera.getSelectionModel().getSelectedItem();
        alumno.setIdCarrera(carreraSeleccionada.getIdCarrera());
        
        try{
            if (fotoSeleccionada != null) {
                byte[] fotoBytes = Files.readAllBytes(fotoSeleccionada.toPath());
                alumno.setFoto(fotoBytes);
            } else if (alumnoEdicion != null) {
                alumno.setFoto(alumnoEdicion.getFoto()); 
            }
        }catch(IOException ioe){
            Utilidades.mostrarAlertaSimple("Error con el foto", "no se pudo cargar la foto", Alert.AlertType.ERROR);
        }
            
        return alumno;
    }
    
    private void cerrarVentana() {
        ((Stage) textMatricula.getScene().getWindow()).close();
    }
    

    private int obtenerFacultadSeleccionada(int idFacultad) {
        for (int i = 0; i < facultades.size(); i++) {
            if (facultades.get(i).getIdFacultad() == idFacultad) {
                return i;
            }
        }
        return -1; 
    }
    
    
    private void abrirDialogo(){
        
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Seleccione un archivo");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos JPG(.jpg, .png, .gif)", "*.png", "*.jpg", "*.gif");
        dialogoSeleccion.getExtensionFilters().add(filtroImg);
        fotoSeleccionada = dialogoSeleccion.showOpenDialog(textCorreo.getScene().getWindow());
        
        if (fotoSeleccionada != null){
            mostrarFoto(fotoSeleccionada);
        }
        
    }
    
    private void mostrarFoto(File foto){
        
        try{
            BufferedImage bufferImg = ImageIO.read(foto);
            Image imagen = SwingFXUtils.toFXImage(bufferImg, null);
            imagenPerfil.setImage(imagen);
        }catch(IOException ioe){
            Utilidades.mostrarAlertaSimple("Error en archivo", "Lo sentimos, la imagen seleccionada no puede cargarse, "
                    + "por favor intente con otro archivo", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarFacultades(){
        HashMap<String, Object> respuesta = CatalogoImpl.obtenerFacultades();
        
        if(!(boolean)respuesta.get("error")){
            List<Facultad> facultadesBD = (List<Facultad>)respuesta.get("facultades");
            facultades = FXCollections.observableArrayList();
            facultades.addAll(facultadesBD);
            comboFacultad.setItems(facultades);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar Facultades", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cargarCarreras(ActionEvent event) {
        int idFacultad = comboFacultad.getValue().getIdFacultad();
        HashMap <String, Object> respuesta = CatalogoImpl.obtenerCarreras(idFacultad);
        
        if(!(boolean)respuesta.get("error")){
            List<Carrera> carrerasBD = (List<Carrera>)respuesta.get("carreras");
            carreras = FXCollections.observableArrayList();
            carreras.addAll(carrerasBD);
            comboCarrera.setItems(carreras);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar las Carreras", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void registrarAlumno(){
        
            Alumno alumno = obtenerAlumno();
            if (AlumnoImpl.verificarDuplicado(alumno.getMatricula())){
                Utilidades.mostrarAlertaSimple("Matrícula en uso", "La matrícula (" +
                        alumno.getMatricula() +
                        ") ya se encuentra en uso, intente con una diferente", Alert.AlertType.WARNING);
                return;
            }
            Respuesta respuesta = AlumnoImpl.registrar(alumno);
            if(!respuesta.isError()){
                Utilidades.mostrarAlertaSimple("Registro exitoso", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                observador.notificarOperacionExitosa("registrar", alumno.getNombre());
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
            }
    }
    
    private void editarAlumno(){
        Alumno alumnoEdicion = obtenerAlumno();
        alumnoEdicion.setIdAlumno(this.alumnoEdicion.getIdAlumno());
        HashMap<String, Object> resultado = AlumnoImpl.editarAlumno(alumnoEdicion);
        if(!(boolean)resultado.get(("error"))){
            Utilidades.mostrarAlertaSimple("Alumno editado correctamente", resultado.get("mensaje").toString(), Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("editar", alumnoEdicion.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", resultado.get("mensaje").toString(), Alert.AlertType.ERROR);
        }
    }
    
    public void inicializarDatos(IObservador observador, Alumno alumno) {
        this.observador = observador;
        this.alumnoEdicion = alumno;
        if (alumno != null) {
            textMatricula.setText(alumno.getMatricula());
            textNombre.setText(alumno.getNombre());
            textApPaterno.setText(alumno.getApellidoPaterno());
            textApMaterno.setText(alumno.getApellidoMaterno());
            textCorreo.setText(alumno.getCorreo());
            pickerNacimiento.setValue(LocalDate.parse(alumno.getFechaNacimiento()));

            textMatricula.setEditable(false);
            textMatricula.setDisable(true);

            int posFacultad = obtenerFacultadSeleccionada(alumno.getIdFacultad());
            comboFacultad.getSelectionModel().select(posFacultad);

            if(posFacultad != -1){
                cargarCarreras(null);
                int posCarrera = obtenerCarreraSeleccionada(alumno.getIdCarrera());
                comboCarrera.getSelectionModel().select(posCarrera);
            }

            recuperarFoto(alumno.getIdAlumno());
        }
    }
    
    private void recuperarFoto(int idAlumno){
        HashMap<String, Object> respuesta = AlumnoImpl.obtenerFoto(idAlumno);
        
        try{
            if (!(boolean)respuesta.get("error")) {
                
                byte[] fotoBytes = (byte[]) respuesta.get("foto");
                
                if (fotoBytes != null && fotoBytes.length > 0) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(fotoBytes);
                    Image img = new Image(bis);
                    imagenPerfil.setImage(img);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Error al cargar la foto", respuesta.get("mensaje").toString(), Alert.AlertType.ERROR);
            }
        }catch(NullPointerException npe){
            Utilidades.mostrarAlertaSimple("Error al cargar la foto", npe.getMessage(), Alert.AlertType.ERROR);
        }
    }
        

    private int obtenerCarreraSeleccionada(int idCarrera) {
        if (carreras != null) {
            for (int i = 0; i < carreras.size(); i++) {
                if (carreras.get(i).getIdCarrera() == idCarrera) {
                    return i;
                }
            }
        }
        return -1;
    }
    
}