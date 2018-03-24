package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private  Main main;
    final FileChooser fileChooser = new FileChooser();
    private String pattern;
    private String text;
    private Search search;
    private BoyerMoore boyerMoore;
    private ObservableList<String> searchData = FXCollections.observableArrayList();
    private ObservableList<String> logData = FXCollections.observableArrayList();

    @FXML
    private ListView<String> lwlog;

    @FXML
    private ComboBox<String> cbSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search      = new Search();
        boyerMoore  = new BoyerMoore();
        searchData.add("Прямой поиск");
        searchData.add("Алгоритм Кнута, Морриса и Пратта");
        searchData.add("Алгоритм Бойера и Мура");
        lwlog.setItems(logData);
        lwlog.setEditable(false);
        cbSearch.setItems(searchData);
        cbSearch.getSelectionModel().selectFirst();
    }

    @FXML
    private TextField field_search;

    @FXML
    private TextArea ta;

    @FXML
    void toSearch(ActionEvent event) {
        if (correctValid()) {
            String content = "Подстрока найдена в позиции : ";
            int indSelect = cbSearch.getSelectionModel().getSelectedIndex();
            int point = searchLine(indSelect);
            logData.add(cbSearch.getSelectionModel().getSelectedItem() + " в позиции: " + point);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Результат поиска");
            alert.setContentText(point == -1 ? "Поиск не дал результатов." : content + point);
            alert.showAndWait();
        }

    }

    private int searchLine(int indSelect) {
        int point = 0;

        switch (indSelect){
            case 0: {
                point = search.straightSearch(ta.getText(), field_search.getText());
            }break;
            case 1: {
                point = search.kmpMatcher(ta.getText(), field_search.getText());
            }break;
            case 2: {
                point = boyerMoore.findPattern(ta.getText(), field_search.getText());
            }break;
            default: {
                point = search.straightSearch(ta.getText(), field_search.getText());
            }break;

        }
        return point;
    }

    @FXML
    void ToloadFile(ActionEvent event) throws IOException {
        ta.clear();
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());
        String text = Util.readUsingBufferedReader(file.getAbsolutePath());
        ta.setText(text);
    }

    @FXML
    void exit(ActionEvent event) {
        System.out.println("exit");
        this.main.getPrimaryStage().close();
    }

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
    }

    protected boolean correctValid() {

        String errorMessage = "";
        if (field_search.getText() == null || field_search.getText().length() == 0) {
            errorMessage += "Вы не ввели строку для поиска\n";
        }
        if (ta.getText() == null || ta.getText().length() == 0) {
            errorMessage += "Отсутствует строка для поиска\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.initOwner();
            alert.setTitle("Ошибка данных");
            alert.setHeaderText("Пожалуйста корректно введите данные");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }



}
