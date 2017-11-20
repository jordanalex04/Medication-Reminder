package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

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
	@FXML private Label upcomingMedsLabel;
	@FXML private TextArea descriptionField;
	@FXML private TextField textFieldTest;
	@FXML private TextField nameField;
	
	
	protected static ArrayList<Medication> medList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Make the hourDropDown box
		int[] hoursArray = new int[24];
		ObservableList<Object> hours = FXCollections.observableArrayList();
		hours.add("");
		for (int i = 0; i < hoursArray.length; i++) {
			hoursArray[i] = i;
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

		medList = new ArrayList<Medication>(100);
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
           String daysOfWeek = "";
           if(sundayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Sunday,";
                  } else {
                        daysOfWeek += "Sunday";
                  }
           }
           if(mondayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Monday,";
                  } else {
                        daysOfWeek += "Monday";
                  }
           }
           if(tuesdayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Tuesday,";
                  } else {
                        daysOfWeek += "Tuesday";
                  }
           }
           if(wednesdayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Wednesday,";
                  } else {
                        daysOfWeek += "Wednesday";
                  }
           }
           if(thursdayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Thursday,";
                  } else {
                        daysOfWeek += "Thursday";
                  }
           }
           if(fridayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Friday,";
                  } else {
                        daysOfWeek += "Friday";
                  }
           }
           if(saturdayCheck.isSelected()) {
                  if(daysOfWeek.equals("")) {
                        daysOfWeek += "Saturday,";
                  } else {
                        daysOfWeek += "Saturday";
                  }
           }
           String tempFullDay = daysOfWeek + "-" + hourDropDown.getValue() + ":" + minuteDropDown.getValue();
           medList.add(new Medication(nameField.getText(), tempFullDay, descriptionField.getText()));
           
           upcomingMedsLabel.setText(upcomingMedsLabel.getText() + "\n" + medList.get(medList.size()-1).toString());
           
           //how to get if a check box is checked
           System.out.println(fridayCheck.isSelected());
    }

	// When the user clicks on clearButton this method will be called
	public void clearButton(ActionEvent event) {
		String emptyString = " ";
		nameField.setText("");
		descriptionField.setText("");
		
		mondayCheck.setSelected(false);
		tuesdayCheck.setSelected(false);
		wednesdayCheck.setSelected(false);
		thursdayCheck.setSelected(false);
		fridayCheck.setSelected(false);
		saturdayCheck.setSelected(false);
		sundayCheck.setSelected(false);
	}

}