package org.gwajae.accountbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.gwajae.accountbook.model.ReloadEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane calendarView;

    @FXML
    private AnchorPane sideView;

    private CalendarController calendarController;
    private SideController sideController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCalendar();
        loadSide();
    }

    private void loadCalendar() {
        String resource = "/org/gwajae/accountbook/";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource(resource + "view/calendar-view.fxml").openStream());
            calendarView.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "styles/calendar.css")).toString());

            CalendarController ccontroller = fxmlLoader.getController();
            calendarController = ccontroller;

            p.addEventFilter(ReloadEvent.OPTIONS_ALL, this::handleReloadEvent2);
            calendarView.getChildren().add(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSide() {
        String resource = "/org/gwajae/accountbook/";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource(resource + "view/side-view.fxml").openStream());
            sideView.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "styles/side.css")).toString());

            SideController scontroller = fxmlLoader.getController();
            sideController = scontroller;

            sideView.getChildren().add(p);
            p.addEventFilter(ReloadEvent.OPTIONS_ALL, this::handleReloadEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleReloadEvent(ReloadEvent event) {
        calendarController.updateCalendar();
        sideController.reloadMenu(calendarController.getCurrentMonth(), calendarController.getCurrentYear());
    }

    public void handleReloadEvent2(ReloadEvent event) {
        calendarController.updateCalendar();
        sideController.reloadMenu(calendarController.getCurrentMonth(), calendarController.getCurrentYear());
    }
}
