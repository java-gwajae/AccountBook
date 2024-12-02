package org.gwajae.accountbook.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SideController implements Initializable {
    @FXML
    private AnchorPane monthview;

    @FXML
    private VBox list;

    @FXML
    private RadioButton allselect;

    @FXML
    private RadioButton incomeselect;

    @FXML
    private RadioButton outcomeselect;

    private int currentMonth;
    private int currentYear;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/gwajae/accountbook/view/monthtab-view.fxml")));
            root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/gwajae/accountbook/styles/side.css")).toString());
            monthview.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MonthtabController month = new MonthtabController();
        month.setPrimaryStage(primaryStage);

        ToggleGroup group = new ToggleGroup();

        allselect.getStyleClass().remove("radio-button");
        allselect.getStyleClass().add("toggle-button");

        incomeselect.getStyleClass().remove("radio-button");
        incomeselect.getStyleClass().add("toggle-button");

        outcomeselect.getStyleClass().remove("radio-button");
        outcomeselect.getStyleClass().add("toggle-button");

        allselect.setToggleGroup(group);
        incomeselect.setToggleGroup(group);
        outcomeselect.setToggleGroup(group);

        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();

        allselect.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                list.getChildren().clear();
                loadMenu();
            }
        });

        incomeselect.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                list.getChildren().clear();
                loadMenu();
            }
        });

        outcomeselect.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                list.getChildren().clear();
                loadMenu();
            }
        });

        loadMenu();
    }

    private void loadMenu() {
        String selector = "";

        if (allselect.isSelected()) {
            selector = "전체";
        } else if (incomeselect.isSelected()) {
            selector = "수입";
        } else if (outcomeselect.isSelected()) {
            selector = "지출";
        }



        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();

        calendarList = calendarService.read();

        try {
            for (Calendar calendar : calendarList) {
                if (calendar.getMonth().equals(currentYear + "-" + currentMonth)) {
                    if(!selector.equals("전체")) {
                        if(!calendar.getType().equals(selector)) continue;
                    }
                    FXMLLoader loader = new FXMLLoader();
                    Pane p = loader.load(getClass().getResource("/org/gwajae/accountbook/view/entry-view.fxml").openStream());

                    EntryController entryController = loader.getController();
                    entryController.updateEntry(calendar);
                    entryController.setPrimaryStage(primaryStage);
                    list.getChildren().add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
