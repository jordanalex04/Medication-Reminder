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

	/*
	 * @FXML GridPane gridPane;
	 * 
	 * public void initialize(URL location, ResourceBundle resources) {
	 * gridPane.setPadding(new Insets(20)); gridPane.setHgap(10);
	 * gridPane.setVgap(15); }
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		gridPane = new GridPane();

		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(15);

		for (int i = 0; i < medList.size(); i++) {
			Label temp = new Label(medList.get(i).getMedName() + '\n'
					+ medList.get(i).getMedDateTime().substring((medList.get(i).getMedDateTime().indexOf('-')) + 2));
			gridPane.add(temp, 0, (2 * i), 1, 2);
			MedicationButton remove = new MedicationButton("Remove", medList.get(i));
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					MedicationButton temp = (MedicationButton) e.getSource();

					removeClicked(temp.getMedication());
				}
			});
			gridPane.add(remove, 2, ((2 * i) + 1), 1, 1);
			MedicationButton edit = new MedicationButton("Edit", medList.get(i));
			edit.onActionProperty();
			edit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					MedicationButton temp = (MedicationButton) e.getSource();
					
					for (int i = 0; i < myController.medList.size(); i++) {
						if (temp.getMedication().toString().equals(myController.medList.get(i).toString())) {
							System.out.println("Edited");
							Stage secondaryStage = new Stage();
							EditMedication scene = new EditMedication();
							scene.make(secondaryStage, medList.get(i), myController, i);
							medList.set(i, myController.medList.get(i));
							
						}
					}
				}
			});
			gridPane.add(edit, 2, (2 * i), 1, 1);

		}

		Scene scene = new Scene(gridPane, 170, 300);
		primaryStage.setTitle("Medications");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

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

	public void removeClicked(Medication med) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove");
		alert.setHeaderText("Are you sure you want to remove this medication?");
		alert.setContentText(med.toString());

		Optional<ButtonType> option = alert.showAndWait();

		if (option.get() == ButtonType.OK) {
			System.out.println("remove");
			for (int i = 0; i < myController.medList.size(); i++) {
				if (med.toString().equals(myController.medList.get(i).toString())) {
					System.out.println("removed");
					myController.medList.remove(i);
					save();
					myController.load();
				}
			}
		} else if (option.get() == ButtonType.CANCEL) {

		}
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