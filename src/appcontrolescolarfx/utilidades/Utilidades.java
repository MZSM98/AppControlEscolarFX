
package appcontrolescolarfx.utilidades;

import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;


public class Utilidades {

    private Utilidades(){
        
    }
    
    public static void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo){
        
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String encabezado, String contenido){
        boolean confirmacion = false;
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType ("No");
        alerta.getButtonTypes().setAll(botonSi, botonNo);
        Optional<ButtonType> opcion = alerta.showAndWait();
        
        return (opcion.isPresent() && opcion.get() == botonSi);
    }
    
    public static FXMLLoader obtenerVistaMemoria(String url){
        
        return new FXMLLoader(appcontrolescolarfx.AppControlEscolarFX.class.getResource(url));
    }
}
