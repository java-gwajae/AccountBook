package org.gwajae.accountbook.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SideController implements Initializable {
    @FXML
    public RadioButton datesort;

    @FXML
    public RadioButton amountsort;

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

    @FXML
    private Button order;

    private int currentMonth;
    private int currentYear;
    private boolean ascending = false;

    private Stage primaryStage;

    private MonthtabController monthtabController;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource("/org/gwajae/accountbook/view/monthtab-view.fxml").openStream());
            monthview.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/gwajae/accountbook/styles/side.css")).toString());

            MonthtabController mcontroller = fxmlLoader.getController();
            monthtabController = mcontroller;

            monthview.getChildren().setAll(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image img = new Image(getClass().getResourceAsStream("/org/gwajae/accountbook/images/order.png"));
        ImageView imgView = new ImageView(img);
        order.setGraphic(imgView);

        ToggleGroup typegroup = new ToggleGroup();

        allselect.getStyleClass().remove("radio-button");
        allselect.getStyleClass().add("toggle-button");

        incomeselect.getStyleClass().remove("radio-button");
        incomeselect.getStyleClass().add("toggle-button");

        outcomeselect.getStyleClass().remove("radio-button");
        outcomeselect.getStyleClass().add("toggle-button");

        allselect.setToggleGroup(typegroup);
        incomeselect.setToggleGroup(typegroup);
        outcomeselect.setToggleGroup(typegroup);

        ToggleGroup sortgroup = new ToggleGroup();

        amountsort.getStyleClass().remove("radio-button");
        amountsort.getStyleClass().add("sort");

        datesort.getStyleClass().remove("radio-button");
        datesort.getStyleClass().add("sort");

        amountsort.setToggleGroup(sortgroup);
        datesort.setToggleGroup(sortgroup);


        amountsort.setSelected(true);
        allselect.setSelected(true);

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

        order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.getChildren().clear();
                if(ascending) {
                    ascending = false;
                } else {
                    ascending = true;
                }
                sortedMenu();
            }
        });

        loadMenu();
    }

    public void loadMenu() {
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
                if (calendar.getYearMonth().equals(currentYear + "-" + currentMonth)) {
                    if(!selector.equals("전체")) {
                        if(!calendar.getType().equals(selector)) continue;
                    }
                    FXMLLoader loader = new FXMLLoader();
                    Pane p = loader.load(getClass().getResource("/org/gwajae/accountbook/view/entry-view.fxml").openStream());
                    EntryController entryController = loader.getController();
                    entryController.setPrimaryStage(primaryStage);
                    entryController.updateEntry(calendar);
                    list.getChildren().add(p);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortedMenu() {
        String selector = "";
        String sorttype = "";

        if (allselect.isSelected()) {
            selector = "전체";
        } else if (incomeselect.isSelected()) {
            selector = "수입";
        } else if (outcomeselect.isSelected()) {
            selector = "지출";
        }


        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();



        if (amountsort.isSelected()) {
            sorttype = "amount";
        } else if (datesort.isSelected()) {
            sorttype = "date";
        }

        if(ascending) {
            calendarList = calendarService.sortread(false, sorttype);
        } else {
            calendarList = calendarService.sortread(true, sorttype);
        }

        try {
            for (Calendar calendar : calendarList) {
                if (calendar.getYearMonth().equals(currentYear + "-" + currentMonth)) {
                    if(!selector.equals("전체")) {
                        if(!calendar.getType().equals(selector)) continue;
                    }
                    FXMLLoader loader = new FXMLLoader();
                    Pane p = loader.load(getClass().getResource("/org/gwajae/accountbook/view/entry-view.fxml").openStream());
                    EntryController entryController = loader.getController();
                    entryController.setPrimaryStage(primaryStage);
                    entryController.updateEntry(calendar);
                    list.getChildren().add(p);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadMenu(int newMonth, int newYear) {
        currentMonth = newMonth;
        currentYear = newYear;
        list.getChildren().clear();
        loadMenu();

        monthtabController.updateMenu(newYear, newMonth);
    }
}
