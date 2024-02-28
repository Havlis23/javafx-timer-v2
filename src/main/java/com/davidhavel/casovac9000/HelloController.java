package com.davidhavel.casovac9000;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable, Runnable {

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

    @FXML
    private Label hourLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label secondsLabel;

    private Thread t;

    private boolean running = false;
    private int seconds;

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
        t = new Thread(this);
        t.setDaemon(true);
        running = true;

        loadTime();
        t.start();
    }

    private void loadTime() {
     seconds = comboHour.getValue() * 3600 + comboMin.getValue() * 60 + comboSec.getValue();
    }
    public void stopCounter(ActionEvent event) {
        slideDown();
        running = false;
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
        transition1.setToY(0);
        transition1.setNode(settingsPage);
        transition1.play();
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis() + 1000;
        while (running) {
            if (System.currentTimeMillis() > time) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateTime();
                    }
                });
                if (seconds == 0) running = false;
                else seconds--;
                time = System.currentTimeMillis() + 1000;

            }
        }
    }

    private void updateTime() {
        System.out.println(seconds--);
        short hours = (short) (seconds / 3600);
        hourLabel.setText((hours < 10) ? "0" + hours: "" + hours);
        short minutes = (short) ((seconds % 3600) / 60);
        minutesLabel.setText((minutes < 10) ? "0" + minutes: "" + minutes);
        short sec = (short) (seconds % 60);
        secondsLabel.setText((sec < 10) ? "0" + sec: "" + sec);
    }

    public void pauseCounter(ActionEvent event) {

    }

    @FXML
    public void resetCounter(ActionEvent event) {
    loadTime();
    }
}
