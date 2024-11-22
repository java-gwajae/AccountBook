module org.gwajae.accountbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires commons.lang3;


    opens org.gwajae.accountbook to javafx.fxml;
    exports org.gwajae.accountbook;
}