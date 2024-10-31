module org.gwajae.accountbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.gwajae.accountbook to javafx.fxml;
    exports org.gwajae.accountbook;
}