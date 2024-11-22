package org.gwajae.accountbook.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public class CalendarModalController implements Initializable {

    @FXML
    public ComboBox<String> comboBox;

    @FXML
    private ToggleButton incomeButton;

    @FXML
    private ToggleButton outcomeButton;

    @FXML
    private Button submit;

    @FXML
    private Button cancel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField amount;

    @FXML
    private TextArea memoField;

    ObservableList<String> list = FXCollections.observableArrayList("월급", "교통비", "식비", "기타");

    public String type = "";
    private String category = "";

    // Select Radiobutton
    @FXML
    public void selectType(ActionEvent event) {
        if(incomeButton.isSelected()) {
            incomeButton.setTextFill(Color.web("#39a4e0"));
            incomeButton.setBackground(new Background(new BackgroundFill(Color.web("#D7EDF9"), new CornerRadii(100), Insets.EMPTY)));

            type = "수입";
        } else if(outcomeButton.isSelected()) {
            outcomeButton.setTextFill(Color.web("#EC4646"));
            outcomeButton.setBackground(new Background(new BackgroundFill(Color.web("#FACCCC"), new CornerRadii(100), Insets.EMPTY)));

            type = "지출";
        }
    }

    // Chang ComboBox
    @FXML
    public void comboChange(ActionEvent event) {
        category = comboBox.getValue();
    }

    @FXML
    public void submitButton(ActionEvent event) {
        String calendar_id = RandomStringUtils.randomAlphanumeric(8);
        String user_id = "1";
        int amount = Integer.parseInt(this.amount.getText());
        LocalDate localDate = datePicker.getValue();
        Date date = java.sql.Date.valueOf(localDate);
        String memo = this.memoField.getText();

        List<Calendar> calendars = new ArrayList<>();
        CalendarService cs = new CalendarService();
        calendars = cs.read();

        for (Calendar calendar : calendars) {
            if (Objects.equals(calendar.getCalendarId(), calendar_id)) {
                calendar_id = RandomStringUtils.randomAlphanumeric(8);
            }
        }

        cs.create(new Calendar(calendar_id, user_id, type, category, amount, date, memo));

        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    // ComboBox setting
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(list);
    }

    // Show calendar modal
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
