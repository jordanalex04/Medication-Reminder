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
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewMedications extends Application {

	GridPane gridPane;
	ArrayList<Medication> medList;
	MyController myController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Initialize the grid pane and set the gaps accordingly
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(15);

		//Run through all medications in the medList and add them and their buttons to the GUI
		for (int i = 0; i < medList.size(); i++) {
			//Sets the label that tells you what medication you are going to work with and adds it to the gridpane
			Label temp = new Label(medList.get(i).getMedName() + '\n'
					+ medList.get(i).getMedDateTime().substring((medList.get(i).getMedDateTime().indexOf('-')) + 2));
			gridPane.add(temp, 0, (2 * i), 1, 3);
			
			//Make the remove button and add it
			MedicationButton remove = new MedicationButton("Remove", medList.get(i));
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					//Get the medication that is associated with that button
					MedicationButton temp = (MedicationButton) e.getSource();
					removeClicked(temp.getMedication());
					//Close this window and reopen it, easy way to update the GUI
					primaryStage.close();
					ViewMedications tempVM = new ViewMedications();
					tempVM.make(medList, primaryStage, myController);
				}
			});
			gridPane.add(remove, 3, ((2 * i) + 1), 1, 1);
			
			//Make the edit button and add it
			MedicationButton edit = new MedicationButton("Edit", medList.get(i));
			edit.onActionProperty();
			edit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					//Get the medication that is associated with that button
					MedicationButton temp = (MedicationButton) e.getSource();
					//Run through all medications to get the medication we need to order
					for (int i = 0; i < myController.medList.size(); i++) {
						if (temp.getMedication().toString().equals(myController.medList.get(i).toString())) {
							//Make a new window
							Stage secondaryStage = new Stage();
							EditMedication scene = new EditMedication();
							scene.make(secondaryStage, medList.get(i), myController, i);
							medList.set(i, myController.medList.get(i));
							
						}
					}
				}
			});
			gridPane.add(edit, 3, (2 * i), 1, 1);

		}

		//Show the current window
		Scene scene = new Scene(gridPane, 200, 300);
		primaryStage.setTitle("Medications");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//Makes the window from this class while being passed in the proper parameters
	public void make(ArrayList<Medication> medListTemp, Stage stage, MyController tempController) {
		medList = medListTemp;
		myController = tempController;
		try {
			this.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//User clicked the remove button
	public void removeClicked(Medication med) {
		//Alert to ask if the user wants to remove a medication
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove");
		alert.setHeaderText("Are you sure you want to remove this medication?");
		alert.setContentText(med.toString());

		Optional<ButtonType> option = alert.showAndWait();

		//If the user clicks ok then the medication gets removed and the new list is saved while the main GUI gets udpdated
		if (option.get() == ButtonType.OK) {
			for (int i = 0; i < myController.medList.size(); i++) {
				if (med.toString().equals(myController.medList.get(i).toString())) {
					myController.medList.remove(i);
					save();
					myController.load();
				}
			}
		} else if (option.get() == ButtonType.CANCEL) {

		}
	}

	//Saves the medList to a txt file.
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