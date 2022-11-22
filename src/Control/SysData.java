package Control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.Jsoner;
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
	
	// Questions Zone // 
	
	/*
	 * This method do import JSON to Question array
	 */
	public List<Question> load_questions() throws IOException, ParseException
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
				/*Question question = new Question(); To be field after create question class */
				//String questionId = (String) currItem.get("question");
				int questionDifficulty = Integer.parseInt((String) currItem.get("level"));
				String questionContent = (String) currItem.get("question");
				List<String> answers =  (List<String>) currItem.get("answers");
				int correctAnswerId = Integer.parseInt((String) currItem.get("correct_ans"));
				
				Question q = new Question(questionDifficulty, questionContent, answers, correctAnswerId);
				//ystem.out.println(q);
				add_question(q);
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.questions;
	}
	
	/*
	 * This method add question to arrayList
	 */
	private boolean add_question(Question q)
	{
		return this.questions.add(q);
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

	
	
	// Score Zone // 

	
	
	
	


}
