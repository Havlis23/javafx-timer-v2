package com.davidhavel.casovac9000;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
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

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    private Thread t;
    private boolean running = false;
    private int seconds;
    private boolean paused = false;
    private Object monitor = new Object();
    private MediaPlayer mp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        ObservableList<Integer> hoursList = FXCollections.observableArrayList();
        ObservableList<Integer> minutesAndSecondsList = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            if (i < 24) {
                hoursList.add(i);
            }
            minutesAndSecondsList.add(i);
        }
        comboHour.setItems(hoursList);
        comboHour.setValue(0);
        comboMin.setItems(minutesAndSecondsList);
        comboMin.setValue(0);
        comboSec.setItems(minutesAndSecondsList);
        comboSec.setValue(0);
    }

    public void startCounter(ActionEvent event) {
        animateSlide(settingsPage, true);
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
        animateSlide(settingsPage, false);
        running = false;
        synchronized (monitor) {
            monitor.notify();
            paused = false;
        }
    }

    private void animateSlide(AnchorPane node, boolean slideUp) {
        TranslateTransition transition = createTranslateTransition(node, slideUp);
        transition.play();
    }

    private TranslateTransition createTranslateTransition(AnchorPane node, boolean slideUp) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(800), node);
        transition.setToX(0);
        transition.setToY(slideUp ? -380 : 0);
        return transition;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis() + 1000;
        while (running) {
            synchronized (monitor) {
                if (paused) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                }
                if (System.currentTimeMillis() > time) {
                    Platform.runLater(this::updateTime);
                    if (seconds == 0) {
                        playTimesUpSound();
                        running = false;
                    } else {
                        seconds--;
                    }
                    time = System.currentTimeMillis() + 1000;
                }
            }
        }
    }

    private void playTimesUpSound() {
        Media bell = new Media(Objects.requireNonNull(getClass().getResource("/com/davidhavel/casovac9000/sounds/timesUp.mp3")).toString());
        mp = new MediaPlayer(bell);
        mp.play();
    }

    private void updateTime() {
        System.out.println(seconds); // takto to pocitalo kazdou druhou sekundu, vereseno: System.out.println(seconds--);
        short hours = (short) (seconds / 3600);
        hourLabel.setText((hours < 10) ? "0" + hours : "" + hours);
        short minutes = (short) ((seconds % 3600) / 60);
        minutesLabel.setText((minutes < 10) ? "0" + minutes : "" + minutes);
        short sec = (short) (seconds % 60);
        secondsLabel.setText((sec < 10) ? "0" + sec : "" + sec);
    }

    public void pauseCounter(ActionEvent event) {
        if (!paused) {
            paused = true;
        } else {
            synchronized (monitor) {
                monitor.notify();
                paused = false;
            }
        }
    }

    public void resetCounter(ActionEvent event) {
        loadTime();
    }
}
