package Control.ViewLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Control.SysData;
import Model.Game;
import Utils.Sound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatisticsScore {
	@FXML
	private AnchorPane mainPane;
	private SysData sd = SysData.getInstance();

	@FXML
	private PieChart myChart;

	@FXML
	public void initialize() {
		addingValues();
	}

	@FXML
	public void addingValues() {
		ObservableList<PieChart.Data> secdata = FXCollections.observableArrayList();
		for (int i = 0; i < sd.getGames().size(); i++) // will add each time the data
		{
			secdata.add(new PieChart.Data(sd.getGames().get(i).getNickname(), sd.getGames().get(i).getScore()));
		}
		myChart.setData(secdata);
		myChart.setVisible(true);

	}

	@FXML
	void returnBack(ActionEvent event) throws IOException {
		sd.playSound(Sound.Menu);

		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameHistory.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
	}

}
