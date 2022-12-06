package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Control.SysData;
import Exceptions.JsonException;
import Model.Game;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionEditor implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private ComboBox<Utils.Difficulty> difficultyComboBox;

	@FXML
	private TableView<Question> questionsTable;

	/*
	 * Column of the Table of Questions
	 */
	@FXML
	public TableColumn<Question, String> numb; // id of the question

	@FXML
	public TableColumn<Question, String> diff; // difficulty of the question
	@FXML
	public TableColumn<Question, String> cont;// text of the question
	@FXML
	public TableColumn<Question, String> correct; // correct answer

	private SysData sd = SysData.getInstance();
	private ObservableList<Question> questions;

	// ObservableList<Question> questions = FXCollections.observableArrayList();

	List<Question> q;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// fill difficulty ComboBox
		if (difficultyComboBox != null)
			difficultyComboBox.getItems().addAll(Difficulty.values());
		
		// fill Question table
		try {
			if (!sd.load_questions()) // import not successful
				throw new JsonException();
			questions = FXCollections.observableArrayList(FXCollections.observableArrayList(sd.get_questions()));
			numb.setCellValueFactory(new PropertyValueFactory<>("questionId"));
			diff.setCellValueFactory(new PropertyValueFactory<>("questionDifficulty"));
			cont.setCellValueFactory(new PropertyValueFactory<>("questionContent"));
			correct.setCellValueFactory(new PropertyValueFactory<>("correctAnswerId"));
			questionsTable.setItems(questions);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JsonException e) {
			SysData.alert(e.getMessage(), e.getMessage(), AlertType.ERROR);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
