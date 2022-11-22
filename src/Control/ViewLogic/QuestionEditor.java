package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Control.SysData;
import Model.Question;
import Utils.Difficulty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionEditor implements Initializable{
	
	 @FXML
	 private AnchorPane mainPane;
	 
	 @FXML
	 private ComboBox<Utils.Difficulty> difficultyComboBox;
	
	@FXML
	private TableView<Question> questionsTable;
		
	 //ObservableList<Question> questions = FXCollections.observableArrayList();
	
	 List<Question> q;
	 
	 @Override
	//fill difficulty ComboBox 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (difficultyComboBox != null)
			difficultyComboBox.getItems().addAll(Difficulty.values());

			//SysData.getInstance().load_questions();
			/*q.addAll(SysData.getInstance().get_questions());
			questionsTable.getItems().addAll(q);
			System.out.println(q);*/

	}
	  
	@FXML
    void returnToHomePage(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show(); 
 	}

}
