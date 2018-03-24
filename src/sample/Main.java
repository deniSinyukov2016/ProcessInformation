package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Главная");
        initRoot();
    }

    private void initRoot() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout =  loader.load();

            //Отображаем главную сцену
            Scene mainScene = new Scene(rootLayout,900,600);
            primaryStage.setScene(mainScene);

            Controller rootController = loader.getController();
            rootController.setMainApp(this);

            primaryStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
