module com.davidhavel.casovac9000 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.davidhavel.casovac9000 to javafx.fxml;
    exports com.davidhavel.casovac9000;
}