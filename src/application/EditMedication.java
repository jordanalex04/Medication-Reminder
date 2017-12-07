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
		gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(15);
		
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
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Medication newMed = saveClicked();
				System.out.println(newMed);
				myController.medList.set(index, newMed);
				save();
				myController.load();
			}
		});
		hourDropDown = new ChoiceBox();
		minuteDropDown = new ChoiceBox();
		
		makeDropDowns();
		
		hourDropDown.setValue(med.getMedDateTime());
		
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
		
		Scene scene = new Scene(gridPane, 200, 340);
		primaryStage.setTitle("Edit");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
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
	
	public Medication saveClicked(){
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
		
		System.out.println(daysOfWeek);
		// Make the proper string for the medication and then add it to the medList
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
	
	public void save() {
		try {
			PrintWriter outputStream = new PrintWriter(new FileOutputStream("medications.txt"));

			for (int i = 0; i < myController.medList.size(); i++) {
				String tempSub = myController.medList.get(i).getMedName();
				outputStream.println(myController.medList.get(i).getMedName());
				outputStream.println(myController.medList.get(i).getMedDateTime());
				outputStream.println(myController.medList.get(i).getMedInfo());
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Missing");
		}
	}
}
