package Control;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
	/*
	 * 
	 */
	public void loadQuestionsDetails() throws IOException, ParseException
	{
		questions = new ArrayList<>();
		String fileName = "Questions.json";
		FileReader reader;
		JSONObject jsO = new JSONObject();
		try
		{
			reader = new FileReader(fileName);
			JSONParser jsonParser = new JSONParser();
			jsO = (JSONObject) jsonParser.parse(reader);

			JSONArray jsAr = (JSONArray) jsO.get("questions");
			for (int i = 0; i < jsAr.size(); i++)
			{

				JSONObject currItem = ((JSONObject) jsAr.get(i));
				/*Question question = new Question(); To be field after create question class */
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	
	


}
