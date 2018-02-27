package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class AddCommandController {
    @FXML private TextField inputPhrase;
    @FXML private TextField typeCommand;
    @FXML private TextField command;
    @FXML private Button okButton;

    Stage stageAddCommand;

    public AddCommandController () {
    }

    public AddCommandController (Stage stage) {
        stageAddCommand = stage;
    }
    public void addCommandButton (ActionEvent event) {
        addCommandToTextFile(inputPhrase.getText(),typeCommand.getText(),command.getText());
        Stage stage = (Stage) okButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        //stageAddCommand.close();
    }

    public void addCommandToTextFile (String input,String type,String command) {
        BufferedWriter textWriter = null;
        try {
            String logFilePath = "/Users/Alex/Desktop/Eclipse Projects/jeffry_1.3/src/TextFiles/commands.txt";
            textWriter = new BufferedWriter(new FileWriter(logFilePath,true));
            textWriter.write( input + "---" + type +"---" + command+"\n");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Incorrect Log File path!!!");
        }finally {
            try {
                textWriter.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
