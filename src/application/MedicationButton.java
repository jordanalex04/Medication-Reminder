package application;

import javafx.scene.control.Button;

public class MedicationButton extends Button{
	Medication medication;
	
	public MedicationButton(String title, Medication myMedication) {
		super(title);
		this.medication = myMedication;
	}
	
	public Medication getMedication() {
		return this.medication;
	}
}
