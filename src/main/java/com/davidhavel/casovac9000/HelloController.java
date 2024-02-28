package com.davidhavel.casovac9000;

import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ComboBox<Integer> comboHour;

    @FXML
    private ComboBox<Integer> comboMin;

    @FXML
    private ComboBox<Integer> comboSec;

    @FXML
    private AnchorPane calculatePage;

    @FXML
    private AnchorPane settingsPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> hodinyList = FXCollections.observableArrayList();
        ObservableList<Integer> minutyASekundyList = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            if (i >= 0 && i < 24) hodinyList.add(new Integer(i));
            minutyASekundyList.add(new Integer(i));
        }
        comboHour.setItems(hodinyList);
        comboHour.setValue(0);
        comboMin.setItems(minutyASekundyList);
        comboMin.setValue(0);
        comboSec.setItems(minutyASekundyList);
        comboSec.setValue(0);
    }

    public void startCounter(ActionEvent event) {
        slideUp();
    }

    public void slideUp() {
        TranslateTransition transition1 = new TranslateTransition();
        transition1.setDuration(Duration.millis(800));
        transition1.setToX(0);
        transition1.setToY(-380);
        transition1.setNode(settingsPage);
        transition1.play();
    }

    public void slideDown() {
TranslateTransition transition1 = new TranslateTransition();
        transition1.setDuration(Duration.millis(800));
        transition1.setToX(0);
        transition1.setToY(380);
        transition1.setNode(settingsPage);
        transition1.play();
    }
}
