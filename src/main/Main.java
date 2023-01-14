package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = Main.loadFxml("tetrishomescene.fxml");
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }

    public static Parent loadFxml(String filename) {
        try {
            return FXMLLoader.load(Paths.get("src",filename).toUri().toURL());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
