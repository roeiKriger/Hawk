package Control.ViewLogic;

import java.io.IOException;
import java.text.ParseException;
import Control.SysData;
import Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameHistory {

	@FXML
	private AnchorPane mainPane;
	private SysData sd = SysData.getInstance();

	@FXML
	private TableView<Game> tbData;
	@FXML
	public TableColumn<Game, String> nickName;

	@FXML
	public TableColumn<Game, String> time;

	@FXML
	public TableColumn<Game, String> score;

	@FXML
	public TableColumn<Game, String> level;
	
	private ObservableList<Game> gamesHistory;


	@FXML
	public void initialize() {
		try {
			boolean flag = sd.import_scores();
			System.out.println(flag);
			gamesHistory = FXCollections
					.observableArrayList(FXCollections.observableArrayList(sd.getGames()));
			nickName.setCellValueFactory(new PropertyValueFactory<>("nickname"));
			time.setCellValueFactory(new PropertyValueFactory<>("date"));
			score.setCellValueFactory(new PropertyValueFactory<>("score"));
			tbData.setItems(gamesHistory);
			System.out.println(gamesHistory);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
