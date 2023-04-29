package com.java.coursework.controllers;

import com.java.coursework.models.Person;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class CreatingTab implements Initializable {
    private final ObservableList<Person> mainList;
    private final ApplicationContext applicationContext;
    @Value("${main-stage.fxml.path}")
    private Resource mainStageResource;
    @Value("${limit-rate}")
    private String limitRate;
    @FXML
    private TextField nameSurnameField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField leavingDayCountField;
    @FXML
    private TextField perDiemDaysField;
    @FXML
    private TextField travelSumValueField;
    @FXML
    private TextField othersSumValueField;
    @FXML
    private TextField costPerDayField;
    private Integer value;
    @FXML
    private Button creationButton;
    @FXML
    private Button returnButton;
    private Person person;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        person = new Person();
        mainList.add(person);
        returnButton.setOnAction(this::onReturnButtonClick);
        creationButton.setOnAction(this::onCreationButtonClick);
    }

    @FXML
    private void onReturnButtonClick(ActionEvent event) {
        comeBackToMainScene(event);
    }

    @FXML
    private void onCreationButtonClick(ActionEvent actionEvent) {
        Integer leavingDayCountParsed = Integer.parseInt(leavingDayCountField.getText()),
                perDiemDaysParsed = Integer.parseInt(perDiemDaysField.getText());
        Double travelSumValueParsed = Double.parseDouble(travelSumValueField.getText()),
                othersSumValueParsed = Double.parseDouble(othersSumValueField.getText()),
                costPerDayParsed = Double.parseDouble(costPerDayField.getText()),
                limitRateParsed = Double.parseDouble(limitRate),
                value = travelSumValueParsed +
                        othersSumValueParsed +
                        costPerDayParsed * leavingDayCountParsed +
                        limitRateParsed * perDiemDaysParsed;
        person.setNameSurname(nameSurnameField.getText());
        person.setDate(dateField.getValue());
        person.setLeavingDayCount(leavingDayCountParsed);
        person.setPerDiemDays(perDiemDaysParsed);
        person.setTravelSumValue(travelSumValueParsed);
        person.setOthersSumValue(othersSumValueParsed);
        person.setCostPerDayField(costPerDayParsed);
        person.setValue(value);
        comeBackToMainScene(actionEvent);
    }

    @SneakyThrows
    private void comeBackToMainScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(mainStageResource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(scene);
        stage.show();
    }
}