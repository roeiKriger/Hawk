package Control.ViewLogic;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;

import Control.SysData;
import Exceptions.JsonException;
import Model.Game;
import Utils.Sound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
	public TableColumn<Game, Integer> score;
	
	@FXML
	public TableColumn<Game, ImageView> win;

	private ObservableList<Game> gamesHistory;
	public static int counter = 0; //for not import scores more than one time
	
	@FXML
	private ImageView winPic;

	@FXML
	public void initialize() {
		try 
		{
			if(counter ==0) // if not imported yet
			{
				if (!sd.import_scores()) // doing import
					throw new JsonException();	//not successfully
			}
			counter ++;
			//sorting game History
			Collections.sort(sd.getGames(), new Comparator<Game>() {
			    public int compare(Game g1, Game g2) {
			        return g2.getScore() - g1.getScore();
			    }
			});
			//set background
			//winPic.setImage(new Image("/Assets/cup.png"));
			gamesHistory = FXCollections.observableArrayList(FXCollections.observableArrayList(sd.getGames()));
			nickName.setCellValueFactory(new PropertyValueFactory<>("nickname"));
			time.setCellValueFactory(new PropertyValueFactory<>("date"));
			score.setCellValueFactory(new PropertyValueFactory<>("score"));
			//win.setCellValueFactory(new PropertyValueFactory<Game, ImageView>("img"));
			//PropertyValueFactory winingList = new PropertyValueFactory<>("hasCup");
			/*if (winingList.getProperty().compareTo("True") == 0 ) //hasCup is true
			{
				Image img = new Image("Assets/cup.png");
				//win.setCellValueFactory(new PropertyValueFactory<Game, ImageView>(new Image("/Assets/.png")));
				win.setCellValueFactory(new PropertyValueFactory<Game, ImageView>(img));
				win.setPrefWidth(60);
			}*/
			//win.setCellValueFactory();
			tbData.setItems(gamesHistory);
			//System.out.println(score.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JsonException e) {
			SysData.alert(e.getMessage(), e.getMessage(), AlertType.ERROR);
		}
	}// ending initialize

	@FXML
	void returnToHomePage(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
		
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
	}
	
	@FXML
	void returnToStatisticsPage(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
		
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/StaticsScoreScreen.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
	}

}
