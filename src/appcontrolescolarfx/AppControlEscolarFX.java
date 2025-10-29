package appcontrolescolarfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class AppControlEscolarFX extends Application {
    
    
    //Agregar en el DAO de profesor, una consulta que verifique si el numero de personal ya existe
    //Si existe, deberia notificar al controlador que no puede continuar
    //Entrega Jueves
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("vista/FXMLInicioSesion.fxml"));
            Scene scene = new Scene (parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ioe){
            System.out.println("Ocurrió una excepción");
            return;
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
