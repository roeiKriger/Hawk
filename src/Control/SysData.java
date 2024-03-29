package Control;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.Jsoner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Exceptions.QuestionEmptyException;
import Model.Game;
import Model.Question;
import Utils.Sound;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class SysData implements Initializable
{
	private static SysData instance = null;
	private static List<Question> questions;
	private List<Question> questionsLevel1;
	private List<Question> questionsLevel2;
	private List<Question> questionsLevel3;
	private Game game;
	private static List<Game> games;
	//public static ObservableList<Question> observableQuestions;
	
	private String nickname;
	private Question currentQuestion;
	
	// questions management
	private String addEditFlag;
	private Question editedQuestion;
	private boolean soundFlag = true;
	
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
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}	

	public String getAddEditFlag() {
		return addEditFlag;
	}

	public void setAddEditFlag(String addEditFlag) {
		this.addEditFlag = addEditFlag;
	}

	public Question getEditedQuestion() {
		return editedQuestion;
	}

	public void setEditedQuestion(Question editedQuestion) {
		this.editedQuestion = editedQuestion;
	}
	
	public boolean isSoundFlag() {
		return soundFlag;
	}

	public void setSoundFlag(boolean soundFlag) {
		this.soundFlag = soundFlag;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	// Questions Zone // 


	/*
	 * This method do import JSON to Question array
	 */
	public boolean loadQuestions() throws IOException, ParseException
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
				//checking answers is not empty and have 4 answers
				if(answers.isEmpty() || answers.get(0).isEmpty() || answers.get(1).isEmpty()|| answers.get(2).isEmpty()|| answers.get(3).isEmpty())
				{
					throw new QuestionEmptyException();
				}
				Long correct = (Long) currItem.get("correct_ans");
				int correctAnswerId = correct.intValue();
				//checking if values are correct to create Question object
				if(questionDifficulty > 0 && questionDifficulty < 5 &&questionContent.length()>0 && correctAnswerId >0 && correctAnswerId <5)
				{
					Question q = new Question(questionDifficulty, questionContent, answers, correctAnswerId);
					SysData.questions.add(q);
				}
				else
				{
					throw new QuestionEmptyException();
				}
									
			}
			filterQuestionsByLevels();
//			System.out.println(this.questions);
			return true;
		} catch (FileNotFoundException e)
		{
			getAlertForException(e);
		}
		catch(QuestionEmptyException e)
		{
			getAlertForException(e);
		}
		return false;
	}
	
	private void getAlertForException(Exception e)
	{
		Alert alert = new Alert(AlertType.ERROR,e.getMessage());
		alert.setHeaderText("Something Failed!");
		alert.setTitle("Something Failed!");
		alert.showAndWait();
	}
	
	/*
	 * create questions arrays by levels
	 */
	public void filterQuestionsByLevels() {
		// init arrays of questions
		this.questionsLevel1 = new ArrayList<>();
		this.questionsLevel2 = new ArrayList<>();
		this.questionsLevel3 = new ArrayList<>();
		
		
		for (Question question : this.getQuestions()) {
			if (question.getQuestionDifficulty() == 1) {
				this.questionsLevel1.add(question);
			} else if (question.getQuestionDifficulty() == 2) {
				this.questionsLevel2.add(question);
			} else { // level 3
				this.questionsLevel3.add(question);
			}
		}
	}
	
	
	/*
	 * This method will write the questions to JSON file
	 */
	public boolean writeQuestions(List<Question> questionsToUpdate)
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
	private boolean deleteQuestionFromList(Question q)
	{
		return this.questions.remove(q);
	}
	
	
	/*
	 * This method gets Question
	 * The method will remove the Question from the List and JSON
	 */
	public boolean deleteQuestion(Question q)
	{
		if(deleteQuestionFromList(q))
		{
			return writeQuestions(questions);
		}
		return false;
	}

	/*
	 * This method get this question list 
	 */
	public List<Question> getQuestions()
	{
		if(this.questions!=null)
			return this.questions;
		
		return new ArrayList<Question>();
	}
	
	public List<Question> getQuestionsLevel1() {
		return questionsLevel1;
	}

	public List<Question> getQuestionsLevel2() {
		return questionsLevel2;
	}

	public List<Question> getQuestionsLevel3() {
		return questionsLevel3;
	}
	

	
	// Score Zone // 

	//this method add game to list
	public boolean addGameToList(Game g)
	{
		return this.games.add(g);
	}
	
	/*
	 * This method will add the scores to JSON file
	 */
	public boolean addScore()
	{
		JSONArray gamesArr = new JSONArray();

		for (Game g : games)
		{
		//Game g = getGame();
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
	public boolean importScores() throws java.text.ParseException
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
				//Game game = new Game(nick);
				Long s = (Long) currItem.get("score");
				int score = s.intValue();
				//game.setScore(score);
				String gameDayOnString = (String) currItem.get("date");
				Date gameDay = new Date(gameDayOnString);
				//game.setDate(gameDay);
				//Game game = new Game
				Game game = new Game(nick, score, gameDay);
//				System.out.println(game);
				if(!games.contains(game)) // if game not exist in list
					this.games.add(game);
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
				this.loadQuestions();
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
	
	
    /*
     * Show alert generically by title and message
     */
    public static void alert(String title, String message, AlertType alertType) { 
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.show();
		return;
    }
    
    /*
     * alert with choice
     * return true if user pressed 'OK', else ruten false
     */
    public static boolean choiceAlert(String title, String message, AlertType alertType) { 
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(message);
		Optional<ButtonType> result = alert.showAndWait();
		if (!(result.get() == ButtonType.OK) || !result.isPresent()) {
			return false;
		}
		return true;
    }
    
    
    /*
     * Show game over alert generically by title and message
     */
    public static void gameOverAlert(String title, String message, AlertType alertType) { 
  		Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(message);
	    alert.show();
	    return;
      }
      
    
    

	/*
	 * This method add the end game to Scores.json
	 */
	public boolean addGameToHistory() {
		try {
			if (game == null) {
				return false;
			}
			if (!games.add(game)) {
				return false;
			}

			return this.addScore();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return true;
	}

	public List<Game> getGames() {
		return games;
	}

	public static void setGames(List<Game> games) {
		SysData.games = games;
	}
	
	
	// Bonuses
	
	// generic function that play sound by argument of Sound enum
	public synchronized void playSound(Sound sound)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
//					AudioInputStream inputStream = AudioSystem
//							.getAudioInputStream(getClass().getResourceAsStream("/sounds/" + sound.getValue()));
					InputStream audioSrc = getClass().getResourceAsStream("/sounds/" + sound.getValue());
					InputStream bufferedIn = new BufferedInputStream(audioSrc);
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

					clip.open(audioStream);
					clip.start();
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}


}
