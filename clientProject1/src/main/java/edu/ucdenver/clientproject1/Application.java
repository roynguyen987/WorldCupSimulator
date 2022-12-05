package edu.ucdenver.clientproject1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {

    /**
     * Starts the user access window
     *
     * @param stage stage parameter
     * @throws IOException Throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene mainScene = new Scene(root, 720, 480);
            stage.setTitle("User Access");
            stage.setScene(mainScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * closeStage
     *
     * @param stage takes in stage
     */
    public void closeStage(Stage stage) {
        stage.close();
    }

    /**
     * main
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }

}