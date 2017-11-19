package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MyController implements Initializable {

	@FXML private Button myButton;
	@FXML private CheckBox mondayCheck;
	@FXML private CheckBox tuesdayCheck;
	@FXML private CheckBox wednesdayCheck;
	@FXML private CheckBox thursdayCheck;
	@FXML private CheckBox fridayCheck;
	@FXML private CheckBox saturdayCheck;
	@FXML private CheckBox sundayCheck;
	@FXML private ChoiceBox hourDropDown;
	@FXML private ChoiceBox minuteDropDown;
	@FXML private ChoiceBox morningAfternoonDropDown;
	@FXML private TextField textFieldTest;
	@FXML private TextField nameField;
	@FXML private TextField descriptionField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Make the hourDropDown box
		int[] hoursArray = new int[12];
		ObservableList<Integer> hours = FXCollections.observableArrayList();
		for (int i = 0; i < hoursArray.length; i++) {
			hoursArray[i] = i + 1;
			hours.add(hoursArray[i]);
		}
		hourDropDown.setItems(hours);

		// Make the minuteDropDown box
		int[] minutesArray = new int[12];
		ObservableList<Integer> minutes = FXCollections.observableArrayList();
		for (int i = 0; i < minutesArray.length; i++) {
			minutesArray[i] = i * 5;
			minutes.add(minutesArray[i]);
		}
		minuteDropDown.setItems(minutes);
		
		// Make the morningAfternoonDropDown box
		ObservableList<String> amOrPm = FXCollections.observableArrayList("AM", "PM");
		morningAfternoonDropDown.setItems(amOrPm);

	}

	// When user click on myButton
	// this method will be called.
	public void buttonTest(ActionEvent event) {
		System.out.println("Button Clicked!");

		Date now = new Date();

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

		// Model Data
		String dateTimeString = df.format(now);

		// Show in VIEW
		textFieldTest.setText(dateTimeString);

	}

	// When the user clicks on addButton this method will be called
	public void addButton(ActionEvent event) {
		System.out.println("ADD");
		
		//how to get if a check box is checked
		System.out.println(fridayCheck.isSelected());
	}

	// When the user clicks on clearButton this method will be called
	public void clearButton(ActionEvent event) {
		System.out.println("Clear");
	}

}