package edu.ucdenver.clientproject1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Button adminButton;
    public Button publicButton;
    public Button exitButton;
    @FXML
    private Label welcomeText;
    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Controller Constructor
     */
    public Controller() {
    }


    /**
     * adminAccess
     * Opens the admin window
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void adminAccess(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AdminWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 720);
        Stage stage = new Stage();
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * publicAccess
     * Opens the public window
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void publicAccess(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PublicWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        Stage stage = new Stage();
        stage.setTitle("Public");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * exit
     * Exits the window
     *
     * @param actionEvent On button press
     */
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
    }
}