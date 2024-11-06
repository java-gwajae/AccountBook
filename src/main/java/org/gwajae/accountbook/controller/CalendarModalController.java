package org.gwajae.accountbook.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CalendarModalController {


    public void showDialog(Stage primaryStage) {
        try {
            String resource = "/org/gwajae/accountbook/";

            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource + "view/calendar-modal.fxml")));
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "styles/calendar-modal.css")).toString());

            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setTitle("Calendar Input");
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
