import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import Control.SysData;
import Model.Question;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Knight's Move");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void example() {
		List<String> ans = new ArrayList<String>();
			ans.add("a plan or specification for the construction of an object or system");
			ans.add("no");
			ans.add("yes");
			ans.add("other");
		Question q = new Question(1, "What is design? ", ans, 1);
		List<Question> que = new ArrayList<Question>();
		que.add(q);
		System.out.println(SysData.getInstance().write_questions(que));
		
	}
	
	
	public static void main(String[] args) throws IOException, ParseException 
	{
		example();
		launch(args);
	}
}
