module com.example.clientproject1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ucdenver.clientproject1 to javafx.fxml;
    exports edu.ucdenver.clientproject1;
}