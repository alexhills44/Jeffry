package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.InputsStringsHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondController implements Initializable{


    @FXML
    private TextField enterTextField;
    @FXML
    private ListView<String> listView;

    static ObservableList<String> messages = FXCollections.observableArrayList();


    public SecondController () {

    }
    public SecondController (String userName,String message) {
        displayText(userName,message);
    }

    @FXML
    public void sendTextToServer(ActionEvent event) {
        String[] a = new String[6];
        for (int i=0;i<6;i++) {
            a[i]= enterTextField.getText();
        }
        System.out.println(a[0]);
        InputsStringsHandler inputsStringsHandler = new InputsStringsHandler (a ,"Server");
        inputsStringsHandler.phraseArrayAnalyzer();
        
        displayText("Server",a[0]);
        enterTextField.setText("");
    }

    @FXML
    public void addCommandButton (ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/AddCommandStage.fxml"));

        Stage stage = new Stage();
        new AddCommandController(stage);
        stage.setTitle("ADD COMMAND");
        stage.setScene(new Scene(root,500,500));
        stage.show();
    }

    public static void displayText(String userName,String message) {
        messages.add(userName+" : "+message);
    }

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        listView.setItems(messages);
    }
}
