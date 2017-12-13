package application;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

import javax.swing.Timer;

public class MyController implements Initializable {

	@FXML private CheckBox mondayCheck;
	@FXML private CheckBox tuesdayCheck;
	@FXML private CheckBox wednesdayCheck;
	@FXML private CheckBox thursdayCheck;
	@FXML private CheckBox fridayCheck;
	@FXML private CheckBox saturdayCheck;
	@FXML private CheckBox sundayCheck;
	@FXML private ChoiceBox<Object> hourDropDown;
	@FXML private ChoiceBox<Object> minuteDropDown;
	@FXML private Label timeLabel;
	@FXML private Label upcomingMedsLabel;
	@FXML private TextArea descriptionField;
	@FXML private TextField nameField;
	@FXML private MenuBar menuBar;


	protected ArrayList<Medication> medList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		medList = new ArrayList<Medication>(1);		//Creates the medList array list
		makeDropDowns();							// Creates all the drop down boxes
		setTime();									// Sets the time, also runs continuously
		load();										// Loads into the program all the medications found in medication.txt
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
		}
		minuteDropDown.setItems(minutes);
	}
	
	public void setTime() {
		//Set the format
		DateFormat timeFormat = new SimpleDateFormat("EEEE HH:mm:ss");
		
		//Use timeline to continously loop, once a second, and update the label and check against all meds
		final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			//Get the date and set the label accordingly
			Date dt = new Date();
			String time = timeFormat.format(dt);
			timeLabel.setText(time);
			
			if(time.substring(time.indexOf(" ") + 1).equals("00:00:01")) {
				sort();
			}
			
			// Check the time on all medications
			String dayOfWeek = time.substring(0, time.indexOf(" "));
			for (int i = 0; i < medList.size(); i++) {
				Medication temp = medList.get(i);
				if(temp.getMedDateTime().contains(dayOfWeek)) {
					if(temp.getMedDateTime().contains(time.substring(time.indexOf(" "), time.length()).trim())) {
						//When the time hits the correct day and time
						Reminder remind = new Reminder();
						remind.appear(temp.getMedName(), temp.getMedInfo());
					}
				}
			}
			//System.out.println(medList.get(1));
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	// When the user clicks on addButton this method will be called
	public void addButton(ActionEvent event) {
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
		
		// Make the proper string for the medication and then add it to the medList
		String tempFullDay = daysOfWeek + " - " + hourDropDown.getValue() + ":" + minuteDropDown.getValue() + ":00";
		medList.add(new Medication(nameField.getText(), tempFullDay, descriptionField.getText()));
		//upcomingMedsLabel.setText(upcomingMedsLabel.getText() + "\n\n" + medList.get(medList.size() - 1).toString());
		//calls the save method to save the new medication to the txt file
		save(tempFullDay);
		load();
	}
	
	//Sorts medlist and updates the label as to what is coming up that day
	public void sort() {
		ArrayList<Medication> dayList = new ArrayList<Medication>();
		upcomingMedsLabel.setText("");
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("EEEE");
		String currentDay = df.format(dt);
		
		//Runs through all elements in the array
		for(int i = 0; i < medList.size(); i++) {
			//Checks to see if the medication needs to be taken today
			if(medList.get(i).getMedDateTime().contains(currentDay)) {
				boolean added = false;
				//If no other element has been added, added the element
				if(dayList.size() == 0) {
					dayList.add(medList.get(i));
				} else {
					//Checks all other medications in the dayList to order them chronologicaly
					for(int j = 0; j < dayList.size(); j++) {
						//Get the hour of element at j and compare it again element at i of medList
						int hour = Integer.parseInt(dayList.get(j).getHour());
						//If < add at j, if equal check minutes else add later
						if(Integer.parseInt(medList.get(i).getHour()) < hour) {
							dayList.add(j, medList.get(i));
							added = true;
							break;
						} else if (Integer.parseInt(medList.get(i).getHour()) == hour) {
							added = true;
							//Checks against minutes
							int minute = Integer.parseInt(dayList.get(j).getMinute());
							if(Integer.parseInt(medList.get(i).getMinute()) < minute) {
								dayList.add(j, medList.get(i));
								break;
							} else {
								if(dayList.size() > (j + 1)) {
									dayList.add(j+1, medList.get(i));
									break;
								} else {
									dayList.add(medList.get(i));
									break;
								}
							}
						}
					}
					if(added == false) {
						dayList.add(medList.get(i));
					}
				}
			}
		}
		
		//Update the upcomingMedsLabel
		for(int i = 0; i < dayList.size(); i++) {
			upcomingMedsLabel.setText(upcomingMedsLabel.getText() + "\n\n" + dayList.get(i));
		}
	}
	
	public void save(String tempFullDay) {
		try {
			//Counts how many lines there are in the file
			int lines = 0;
			Scanner inputStream = new Scanner(new FileInputStream("medications.txt"));
			while (inputStream.hasNextLine()) {
				lines++;
				inputStream.nextLine();
			}
			inputStream.close();
			
			//Delcare an array of size lines and then fill each spot with a line from the txt file
			String contents[] = new String[lines];
			inputStream = new Scanner(new FileInputStream("medications.txt"));
			for(int i = 0; i < lines; i++) {
				contents[i] = inputStream.nextLine();
			}
			inputStream.close();
			
			//Write the array back to the file
			PrintWriter outputStream = new PrintWriter(new FileOutputStream("medications.txt"));
			for (int i = 0; i < contents.length; i++) {
				outputStream.println(contents[i]);
			}
			
			//If the info is empty print N/A
			String description = descriptionField.getText();
			if(descriptionField.getText().isEmpty()) {
				description = "N/A";
				System.out.println("grr");
			}
			//Finally add the new medication to the file
			outputStream.print(nameField.getText() + "\n" + tempFullDay + "\n" + description + "\n");
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Missing");
		}
	}
	
	public void load() {
		medList = new ArrayList<Medication>();
		try {
			upcomingMedsLabel.setText("");
			//Counts how many lines there are in the file
			int lines = 0;
			Scanner inputStream = new Scanner(new FileInputStream("medications.txt"));
			while (inputStream.hasNextLine()) {
				lines++;
				inputStream.nextLine();
			}
			inputStream.close();
			
			//Delcare an array of size lines and then fill each spot with a line from the txt file
			String contents[] = new String[lines];
			inputStream = new Scanner(new FileInputStream("medications.txt"));
			for(int i = 0; i < lines; i++) {
				contents[i] = inputStream.nextLine();
				if((i+1)%3 == 0) {
					medList.add(new Medication(contents[i-2], contents[i-1], contents[i]));
					//upcomingMedsLabel.setText(upcomingMedsLabel.getText() + "\n\n" + medList.get(medList.size() - 1).toString());
				}
			}
			inputStream.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File Missing");
		}
		
		sort();
	}

	// When the user clicks on clearButton this method will be called
	public void clearButton(ActionEvent event) {
		//Sets the fields to blank
		nameField.setText("");
		descriptionField.setText("");
		
		//Clears the selected values on the drop downs
		makeDropDowns();

		//Removes all checks in the check boxes
		mondayCheck.setSelected(false);
		tuesdayCheck.setSelected(false);
		wednesdayCheck.setSelected(false);
		thursdayCheck.setSelected(false);
		fridayCheck.setSelected(false);
		saturdayCheck.setSelected(false);
		sundayCheck.setSelected(false);
	}
	
	public void viewMeds(ActionEvent event) {
		Stage secondaryStage = new Stage();
		
		ViewMedications scene = new ViewMedications();
		scene.make(medList, secondaryStage, this);
		/*
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ViewMedicationsScene.fxml"));
			
			//Read file fxml and draw interface
			secondaryStage.setTitle("Medications");
			secondaryStage.setScene(new Scene(root));
			secondaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		} */
		
	}

}