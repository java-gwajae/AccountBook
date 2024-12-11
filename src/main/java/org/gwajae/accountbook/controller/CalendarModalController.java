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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.io.IOException;
import java.net.URL;
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

    ObservableList<String> outcomelist = FXCollections.observableArrayList("교통", "식비", "기타");
    ObservableList<String> incomelist = FXCollections.observableArrayList("월급", "기타");

    public String type = "";
    private String category = "";

    // Select Radiobutton
    @FXML
    public void selectType(ActionEvent event) {
        if(incomeButton.isSelected()) {
            type = "수입";
            comboBox.setItems(incomelist);
        } else if(outcomeButton.isSelected()) {
            type = "지출";
            comboBox.setItems(outcomelist);
        }

    }

    // Chang ComboBox
    @FXML
    public void comboChange(ActionEvent event) {
        category = comboBox.getValue();
    }

    @FXML
    public void submitButton(ActionEvent event) {
        String calendar_id = "34";
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
        ToggleGroup typegroup = new ToggleGroup();

        incomeButton.setToggleGroup(typegroup);
        outcomeButton.setToggleGroup(typegroup);


        incomeButton.getStyleClass().add("income-button");
        outcomeButton.getStyleClass().add("outcome-button");
    }

}
