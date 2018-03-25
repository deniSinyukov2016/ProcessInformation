package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private ObservableList<String> wordsList = FXCollections.observableArrayList();
    private Porter porter;
    private List<String> words;


    @FXML
    private ListView<String> lwlog;

    @FXML
    private ComboBox<String> cbSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search      = new Search();
        boyerMoore  = new BoyerMoore();
        porter      = new Porter();
        searchData.add("Прямой поиск");
        searchData.add("Алгоритм Кнута, Морриса и Пратта");
        searchData.add("Алгоритм Бойера и Мура");
        searchData.add("Алгоритм стемминга");
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
            if(cbSearch.getSelectionModel().getSelectedIndex() != 3) {
                int indSelect = cbSearch.getSelectionModel().getSelectedIndex();
                int point = searchLine(indSelect);
                logData.add(cbSearch.getSelectionModel().getSelectedItem() + " в позиции: " + point);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат поиска");
                alert.setHeaderText("Результат поиска");
                alert.setContentText(point == -1 ? "Поиск не дал результатов." : content + point);
                alert.showAndWait();
            }else if(cbSearch.getSelectionModel().getSelectedIndex() == 3){

                words = porter.stem(ta.getText());
                wordsList.clear();
                for(String word : words){
                    wordsList.add(word);
                }
                porterPanel();
            }

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
//        if (field_search.getText() == null || field_search.getText().length() == 0) {
//            errorMessage += "Вы не ввели строку для поиска\n";
//        }
        if (ta.getText() == null || ta.getText().length() == 0) {
            errorMessage += "Отсутствует тест поиска\n";
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
    private void porterPanel() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("porter.fxml"));
            BorderPane porterLayout =  loader.load();

            //Отображаем главную сцену
            Scene mainScene = new Scene(porterLayout,900,600);
            Stage porterStage = new Stage();
            porterStage.setTitle("Результаты поиска");
            porterStage.setScene(mainScene);

            ControllerPorter controllerPorter = loader.getController();
            controllerPorter.getLaCountWords().setText(String.valueOf(words.size()));
            controllerPorter.getLwWords().setItems(wordsList);
            File file = Util.writeUsingFileWriter(Util.setLine(wordsList));
            logData.add("Запись в файл: "+ file.getAbsolutePath());

            porterStage.showAndWait();


        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
