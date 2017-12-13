/*
 * Alexander Jordan
 * Quinn Furumo
 * Medication Reminder
 * Fall 2017 - COSC 2100
 */

package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditMedication extends Application{
	GridPane gridPane;
	Medication med;
	int index;
	MyController myController;
	
	//All the fields within the new window
	TextField nameField;
	TextArea descriptionField;
	CheckBox mondayCheck;
	CheckBox tuesdayCheck;
	CheckBox wednesdayCheck;
	CheckBox thursdayCheck;
	CheckBox fridayCheck;
	CheckBox saturdayCheck;
	CheckBox sundayCheck;
	ChoiceBox<Object> hourDropDown;
	ChoiceBox<Object> minuteDropDown;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Set up the new grid pane for the window
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(15);
		
		//Create all objects that are within the window
		Label nameLabel = new Label("Name");
		Label descriptionLabel = new Label("Description");
		nameField = new TextField(med.getMedName());
		descriptionField = new TextArea(med.getMedInfo());
		descriptionField.setWrapText(true);
		mondayCheck = new CheckBox("Mon");
		tuesdayCheck = new CheckBox("Tue");
		wednesdayCheck = new CheckBox("Wed");
		thursdayCheck = new CheckBox("Thu");
		fridayCheck = new CheckBox("Fri");
		saturdayCheck = new CheckBox("Sat");
		sundayCheck = new CheckBox("Sun");
		//Create the save button and add directions for being clicked
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//Make a new medication based off the input from the user
				Medication newMed = saveClicked();
				//Remove the old medication and add the new one
				myController.medList.remove(index);
				myController.medList.add(newMed);
				//Save the new medList and load the new save, updates the GUI label
				save();
				myController.load();
				//Close this window
				primaryStage.close();
			}
		});
		hourDropDown = new ChoiceBox();
		minuteDropDown = new ChoiceBox();
		
		//Make the drop down boxes
		makeDropDowns();
		
		//Set the check boxes according to checked or not accrodingly
		if(med.getMedDateTime().contains("Monday"))
			mondayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Tuesday"))
			tuesdayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Wednesday"))
			wednesdayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Thursday"))
			thursdayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Friday"))
			fridayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Saturday"))
			saturdayCheck.setSelected(true);
		if(med.getMedDateTime().contains("Sunday"))
			sundayCheck.setSelected(true);
		
		//Add all the elements to the window
		gridPane.add(nameLabel, 0, 0, 1, 1);
		gridPane.add(nameField, 1, 0, 2, 1);
		gridPane.add(hourDropDown, 0, 1, 1, 1);
		gridPane.add(minuteDropDown, 2, 1, 1, 1);
		gridPane.add(mondayCheck, 0, 2, 1, 1);
		gridPane.add(tuesdayCheck, 1, 2, 1, 1);
		gridPane.add(wednesdayCheck, 2, 2, 1, 1);
		gridPane.add(thursdayCheck, 0, 3, 1, 1);
		gridPane.add(fridayCheck, 1, 3, 1, 1);
		gridPane.add(saturdayCheck, 2, 3, 1, 1);
		gridPane.add(sundayCheck, 1, 4, 1, 1);
		gridPane.add(descriptionLabel, 0, 5, 2, 1);
		gridPane.add(descriptionField, 0, 6, 3, 5);
		gridPane.add(save, 2, 11, 1, 1);
		
		//Open the window
		Scene scene = new Scene(gridPane, 250, 340);
		primaryStage.setTitle("Edit");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//Makes the window from this class while being passed in the proper parameters
	public void make(Stage stage, Medication myMed, MyController tempController, int myIndex) {
		this.med = myMed;
		this.myController = tempController;
		this.index = myIndex;
		try {
			this.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//When the user clicks on save
	public Medication saveClicked(){
		//Get the days of the week that the user clicked
		String daysOfWeek = "";
		if (sundayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Sunday";
			else daysOfWeek += ", Sunday";
		}
		if (mondayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Monday";
			else daysOfWeek += ", Monday";
		}
		if (tuesdayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Tuesday";
			else daysOfWeek += ", Tuesday";
		}
		if (wednesdayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Wednesday";
			else daysOfWeek += ", Wednesday";
		}
		if (thursdayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Thursday";
			else daysOfWeek += ", Thursday";
		}
		if (fridayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Friday";
			else daysOfWeek += ", Friday";
		}
		if (saturdayCheck.isSelected()) {
			if (daysOfWeek.equals("")) daysOfWeek += "Saturday";
			else daysOfWeek += ", Saturday";
		}
		
		// Make the proper string for the medication and then return a medication object
		String tempFullDay = daysOfWeek + " - " + hourDropDown.getValue() + ":" + minuteDropDown.getValue() + ":00";
		return new Medication(nameField.getText(), tempFullDay, descriptionField.getText());
	}
	
	public void makeDropDowns() {
		// Make the hourDropDown box
		String[] hoursArray = new String[24];
		ObservableList<Object> hours = FXCollections.observableArrayList();
		for (int i = 0; i < hoursArray.length; i++) {
			hoursArray[i] = i + "";
			
			//Formats the hours to always have two digits
			if (i < 10)
				hoursArray[i] = "0" + i;
			
			hours.add(hoursArray[i]);
			
			String hour = med.getMedDateTime();
			hour = hour.substring((hour.indexOf("-") + 2), (hour.indexOf("-") + 4));
			if(hour.equals(hoursArray[i])) {
				hourDropDown.setValue(hour);
			}
		}
		hourDropDown.setItems(hours);

		// Make the minuteDropDown box
		String[] minutesArray = new String[12];
		ObservableList<Object> minutes = FXCollections.observableArrayList();
		for (int i = 0; i < minutesArray.length; i++) {
			minutesArray[i] = i * 5 + "";
			
			//Formats the minutes to always have two digits
			if ((i * 5) < 10)
				minutesArray[i] = "0" + minutesArray[i];
			
			minutes.add(minutesArray[i]);
			
			String minute = med.getMedDateTime();
			minute = minute.substring((minute.indexOf("-") + 5), (minute.indexOf("-") + 7));
			if(minute.equals(minutesArray[i])) {
				minuteDropDown.setValue(minute);
			}
		}
		minuteDropDown.setItems(minutes);
	}
	
	//Saves the medList to a txt file
	public void save() {
		try {
			PrintWriter outputStream = new PrintWriter(new FileOutputStream("medications.txt"));

			for (int i = 0; i < myController.medList.size(); i++) {
				String tempSub = myController.medList.get(i).getMedName();
				outputStream.println(myController.medList.get(i).getMedName());
				outputStream.println(myController.medList.get(i).getMedDateTime());
				outputStream.println(myController.medList.get(i).getMedInfo());
				//Prints 'N/A' if there is no med info
				if(myController.medList.get(i).getMedInfo().isEmpty()) {
					outputStream.print("N/A");
				}
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Missing");
		}
	}
}
