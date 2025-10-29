
package utilidad;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;


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
    
    public static FXMLLoader obtenerVistaMemoria(String url){
        
        return new FXMLLoader(appcontrolescolarfx.AppControlEscolarFX.class.getResource(url));
    }
}
