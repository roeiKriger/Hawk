package Control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import Model.Game;
import Model.Question;
import javafx.fxml.Initializable;


public class SysData implements Initializable
{
	private static SysData instance = null;
	private static List<Question> questions;
	private Game game;
	private static List<Game> games;
	
	// static method to create instance of Singleton class
	public static SysData getInstance()
	{
		if (instance == null)
		{
			instance = new SysData();
			questions = new ArrayList<>();
			games = new ArrayList<>();
		}
		return instance;
	}
	
	//TO-DO update JSON
	
	
	//TO-DO read JSON
	
	//TO-DO initialize 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		if (questions == null)
		{
			questions = new ArrayList<>();

		}

	}
	

	
	


}
