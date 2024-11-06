package org.gwajae.accountbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane calendarView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCalendar();
    }

    private void loadCalendar() {
        String resource = "/org/gwajae/accountbook/";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource + "view/calendar-view.fxml")));
            calendarView.getChildren().setAll(root);
            calendarView.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "styles/calendar.css")).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
