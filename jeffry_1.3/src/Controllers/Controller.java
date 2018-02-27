package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.ServerMain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML public TextField enterIpAddress;
    @FXML public TextField enterPortNumber;

    @FXML
    public void startButton(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/SecondScene.fxml"));

        startJeffry();
        new ServerMain().start();

        Stage stage = Main.getStage();
        stage.setScene(new Scene(root,700,700));
        stage.setTitle("JEFFRY");
        stage.show();
    }

    private void startJeffry () {
        if (enterIpAddress.getText()!= null) {
            ServerMain.setIpAddress(enterIpAddress.getText());
        }
        if (enterPortNumber.getText()!= null) {
            ServerMain.setIpAddress(enterPortNumber.getText());
        }
    }


}
