package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//GITHUB TEST PLEASE WORK

public class Medication {
       private String medName; //name of medication
       private String medDateTime; // day of the week and at times of which
       private String medInfo; //amount to take, take with food or not, etc
       
       public Medication(String myMedName, String myMedDateTime, String myMedInfo) {
              this.medName = myMedName;
              this.medDateTime = myMedDateTime;
              this.medInfo = myMedInfo;
       }

       public String getMedName() {
              return this.medName;
       }

       public String getMedDateTime() {
              return this.medDateTime;
       }
       
       public String getMedInfo() {
              return this.medInfo;
       }

       public void setMedName(String myMedName) {
              this.medName = myMedName;
       }
      
       public void setMedDateTime(String myMedDateTime) {
              this.medDateTime = myMedDateTime;
       }
      
       public void setMedInfo(String myMedInfo) {
              this.medInfo = myMedInfo;
       }
             
       public String toString() {
              return "Medication Name: " + "." + medName + " Medication Time and Date: " + "." + medDateTime + " Medication Information: " + medInfo + ".";
       }

}
