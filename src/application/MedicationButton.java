/*
 * Alexander Jordan
 * Quinn Furumo
 * Medication Reminder
 * Fall 2017 - COSC 2100
 */

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
