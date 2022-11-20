module com.group10 {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.group10 to javafx.fxml;
  exports com.group10;
}