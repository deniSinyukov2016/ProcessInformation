package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPorter implements Initializable{


    @FXML
    private ListView<String> lwWords;

    @FXML
    private Label laCountWords;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setLaCountWords(Label laCountWords) {
        this.laCountWords = laCountWords;
    }
    public Label getLaCountWords() {
        return laCountWords;
    }

    public ListView<String> getLwWords() {
        return lwWords;
    }

    public void setLwWords(ListView<String> lwWords) {
        this.lwWords = lwWords;
    }
}
