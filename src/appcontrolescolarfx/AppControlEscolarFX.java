package appcontrolescolarfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppControlEscolarFX extends Application {
    
    
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
