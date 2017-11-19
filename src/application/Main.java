package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Scene.fxml"));
			
			int[] hours = new int[12];
			for(int i = 0; i < hours.length; i++) {
				hours[i] = i+1;
			}
			
			ObservableList<int[]> languages //
            = FXCollections.observableArrayList(hours);
			ChoiceBox<int[]> choiceBox = new ChoiceBox<int[]>(languages);
			
			//Read file fxml and draw interface
			primaryStage.setTitle("Test");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
