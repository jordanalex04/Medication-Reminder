package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//Test to make sure all works

public class Reminder{
	void appear(String medName, String medInfo) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");

		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("ALERT!\nIt's time to take your medication " + medName
				+ '\n' + "Your Description for this medication is: \n" + medInfo);

		alert.show();
	}

}