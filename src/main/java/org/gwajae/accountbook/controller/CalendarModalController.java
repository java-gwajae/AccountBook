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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarModalController implements Initializable {

    @FXML
    public ComboBox<String> comboBox;

    @FXML
    private ToggleButton incomeButton;

    @FXML
    private ToggleButton outcomeButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField amount;

    @FXML
    private TextArea memoField;

    ObservableList<String> list = FXCollections.observableArrayList("월급", "교통비", "식비", "기타");

    public String type = "";
    private String category = "";

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

    @FXML
    public void comboChange(ActionEvent event) {
        category = comboBox.getValue();
    }

    @FXML
    public void submitButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(list);
    }

    public void showDialog(Stage primaryStage) {
        try {
            String resource : "/org/gwajae/accountbook/"
            
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource + "/view/calendar-modal.fxml")));
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "/styles/calendar-modal.css")).toString());

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
