package Control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.Jsoner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.Game;
import Model.Question;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;


public class SysData implements Initializable
{
	private static SysData instance = null;
	private static List<Question> questions;
	private Game game;
	private static List<Game> games;
	//public static ObservableList<Question> observableQuestions;
	
	private String nickname;
	
	// static method to create instance of Singleton class
	public static SysData getInstance()
	{
		if (instance == null)
		{
			instance = new SysData();
			questions = new ArrayList<>();
			games = new ArrayList<>();
			//observableQuestions = FXCollections.observableArrayList();
		}
		return instance;
	}
	
	// getters & setters
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	// Questions Zone // 

	/*
	 * This method do import JSON to Question array
	 */
	public boolean load_questions() throws IOException, ParseException
	{
		//questions = new ArrayList<>();
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
				Long difficulty = (Long) currItem.get("level");
				int questionDifficulty = difficulty.intValue();
				String questionContent = (String) currItem.get("question");
				List<String> answers =  (List<String>) currItem.get("answers");
				Long correct = (Long) currItem.get("correct_ans");
				int correctAnswerId = correct.intValue();
				
				Question q = new Question(questionDifficulty, questionContent, answers, correctAnswerId);
				SysData.questions.add(q);
			}
			System.out.println(this.questions);
			return true;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	/*
	 * This method will write the questions to JSON file
	 */
	public boolean write_questions(List<Question> questionsToUpdate)
	{
		questions = new ArrayList<>();
		questions.addAll(questionsToUpdate);
		JSONArray questionsArr = new JSONArray();

		for (Question q : questions)
		{
			JSONArray answers = new JSONArray();
			for (String ans : q.getAnswers())
			{
				answers.add(ans);
			}
			JSONObject inner = new JSONObject();

			inner.put("question", q.getQuestionContent());
			inner.put("answers", answers);
			inner.put("correct_ans", q.getCorrectAnswerId());
			inner.put("level", q.getQuestionDifficulty());
			inner.put("team", "Hawk");
			questionsArr.add(inner);

		}
		JSONObject outer = new JSONObject();

		if (questions.size() > 0)
		{
			outer.put("questions", questionsArr);
		}

		FileWriter writer;
		try
		{
			writer = new FileWriter("Questions.json");
			writer.write(Jsoner.prettyPrint(outer.toJSONString()));
			writer.flush();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return false;

	}
	
	/*
	 * This method gets Question
	 * The method will remove the Question from the List 
	 */
	private boolean delete_question_from_list(Question q)
	{
		return this.questions.remove(q);
	}
	
	
	/*
	 * This method gets Question
	 * The method will remove the Question from the List and JSON
	 */
	public boolean delete_question(Question q)
	{
		if(delete_question_from_list(q))
		{
			return write_questions(questions);
		}
		return false;
	}

	/*
	 * This method get this question list 
	 */
	public List<Question> get_questions()
	{
		if(this.questions!=null)
			return this.questions;
		
		return new ArrayList<Question>();
	}
	
	
	// Score Zone // 
	
	//this method add game to list
	public boolean add_game_to_list(Game g)
	{
		return this.games.add(g);
	}
	
	/*
	 * This method will add the scores to JSON file
	 */
	public boolean add_score()
	{
		JSONArray gamesArr = new JSONArray();

		for (Game g : games)
		{
			JSONObject inner = new JSONObject();

			inner.put("nickname", g.getNickname());
			inner.put("score", g.getScore());
			inner.put("date", g.getDate().toGMTString());
			gamesArr.add(inner);
		}
		JSONObject outer = new JSONObject();

		if (games.size() > 0)
		{
			outer.put("games", gamesArr);
		}

		FileWriter writer;
		try
		{
			writer = new FileWriter("Scores.json");
			writer.write(Jsoner.prettyPrint(outer.toJSONString()));
			writer.flush();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * This method get the scores to the list from Scores.json
	 */
	public boolean import_scores() throws java.text.ParseException
	{
		String fileName = "Scores.json";
		FileReader reader;
		JSONObject jsO = new JSONObject();
		try
		{
			reader = new FileReader(fileName);
			JSONParser jsonParser = new JSONParser();
			jsO = (JSONObject) jsonParser.parse(reader);

			JSONArray jsAr = (JSONArray) jsO.get("games");
			for (int i = 0; i < jsAr.size(); i++)
			{
				JSONObject currItem = ((JSONObject) jsAr.get(i));
				String nick = (String) currItem.get("nickname");
				Game game = new Game(nick);
				Long s = (Long) currItem.get("score");
				int score = s.intValue();
				game.setScore(score);
				String gameDayOnString = (String) currItem.get("date");
				Date gameDay = new Date(gameDayOnString);
				game.setDate(gameDay);
				System.out.println(game);
			}	
			return true;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		if (questions == null)
		{
			questions = new ArrayList<>();
			try
			{
				this.load_questions();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	
	
	


}
