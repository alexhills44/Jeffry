package main;

import Controllers.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/StartScene.fxml"));
        primaryStage.setTitle("JEFFRY 1.3");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    //GUI METHOD
    public static void setStage (Stage s) {
        stage = s;
    }
    //GUI METHOD
    public static Stage getStage() {
        return Main.stage;
    }
}
